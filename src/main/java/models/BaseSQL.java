package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Данный класс взят из урока по ORM. Доработаны методы addQuery, updateQuery.
 * Методы add, update, delete использованы для работы с БД в учетке администратора (login: admin, password: admin).
 * Следующим шагом надо доработать возможность вставки и обновления товара при условии заполнения отдельных форм данных (сейчас сделано с учетом заполнения всех данных о товаре).
 */
public class BaseSQL {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement preparedStmt;
	private static BaseSQL instance;

	private BaseSQL() throws ClassNotFoundException, SQLException {
		conn = BaseModel.getConnection();
		stmt = conn.createStatement();
	}

	/**
	 * Возвращает объект класса, всегда один существующий.
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static BaseSQL getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null) {
			instance = new BaseSQL();
		}
		return instance;
	}

	/**
	 * Обертка для selectQuery
	 * 
	 * @param table
	 * @param fields
	 * @param where
	 * @return
	 */
	public ResultSet select(String table, String fields[], String where) {
		try {
			return getInstance().selectQuery(table, fields, where);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Выбирает данные из таблицы
	 * 
	 * @param table
	 * @param fields
	 * @param where
	 * @return
	 * @throws SQLException
	 */
	private ResultSet selectQuery(String table, String[] fields, String where) throws SQLException {
		if (conn != null && !conn.isClosed()) {
			String selectFields = "";
			int length = fields.length;
			if (length == 0) {
				selectFields = "*";
			} else {
				for (int i = 0; i < fields.length; i++) {
					selectFields += fields[i] + (i < length - 1 ? "," : "");
				}
			}
			return stmt.executeQuery("SELECT " + selectFields + " FROM " + table + " " + where);
		}
		return null;
	}

	/**
	 * Обертка для insertQuery
	 * 
	 * @param table
	 * @param columns
	 * @param values
	 * @return
	 */
	public boolean insert(String table, ArrayList<String> columns, HashMap<Integer, String[]> values) {
		try {
			return getInstance().insertQuery(table, columns, values);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Вставка данных в таблицу
	 * 
	 * формирует и выполняет запросы типа
	 * 
	 * INSERT INTO `goods`(`title`, `price`) VALUES ('Ока','500'), ('Москвич','600')
	 * 
	 * @param table  - таблица для вставки
	 * @param values - наборы данных для вставки
	 * @return
	 * @throws SQLException
	 */
	private boolean insertQuery(String table, ArrayList<String> columns, HashMap<Integer, String[]> values)
			throws SQLException {

		if (conn == null && conn.isClosed()) {
			return false;
		}

		String sqlQuery = "INSERT INTO `" + table + "` ", sqlValues = "", sqlColumns = "";
		sqlColumns = "(`" + String.join("`, `", columns) + "`)";

		int i = 0;
		int count = values.size();
		for (var valItem : values.entrySet()) {
			sqlValues += "('" + String.join("', '", valItem.getValue()) + "')" + (++i < count ? ", " : "");
		}

		sqlQuery += sqlColumns + " VALUES " + sqlValues;
		System.out.println("\nПолученный SQL-запрос:\n" + sqlQuery);
		return stmt.executeUpdate(sqlQuery) > 0;

	}

	/**
	 * Обертка над deleteQuery
	 * 
	 * @param table
	 * @param id
	 * @return
	 */
	public boolean delete(String table, String where) {
		try {
			return getInstance().deleteQuery(table, where);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Удаление данных из таблицы
	 * 
	 * @param table
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	private boolean deleteQuery(String table, String where) throws SQLException {
		if (conn != null && !conn.isClosed()) {
			preparedStmt = conn.prepareStatement("DELETE FROM " + table + " " + where);
			System.out.println(preparedStmt);
			return preparedStmt.executeUpdate() > 0;
		}
		return false;
	}

	/**
	 * обертка над updateQuery
	 * 
	 * @param table
	 * @param fields
	 * @param where
	 * @return
	 */
	public boolean update(String table, HashMap<String, Object> fields, String where) {
		try {
			return getInstance().updateQuery(table, fields, where);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	
	private boolean updateQuery(String table, HashMap<String, Object> fields, String where) throws SQLException {
		if (conn == null && conn.isClosed()) {
			return false;
		}

		String sqlQuery = "UPDATE `" + table + "` SET ";

		int i = 0;
		int count = fields.size();
		var vals = new ArrayList<Object>();
		for (String key : fields.keySet()) {
			sqlQuery += "`" + key + "`" + " = ?" + (++i < count ? ", " : "");
			vals.add(fields.get(key));
		}

		System.out.println(sqlQuery);
		preparedStmt = conn.prepareStatement(sqlQuery + where);

		i = 1;
		for (Object val : vals) {
			System.out.println(val);
			preparedStmt.setObject(i, val);
			i++;
		}

		System.out.println("Полученный SQL-запрос:\n" + preparedStmt);
		return preparedStmt.executeUpdate() > 0;

	}
	
	public boolean add(String table, ArrayList<String> columns, ArrayList<Object> values) {
		try {
			return getInstance().addQuery(table, columns, values);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean addQuery(String table, ArrayList<String> columns, ArrayList<Object> values)
			throws SQLException {

		if (conn == null && conn.isClosed()) {
			return false;
		}

		String sqlQuery = "INSERT INTO `" + table + "` ", sqlValues = "", sqlColumns = "";
		sqlColumns = "(`" + String.join("`, `", columns) + "`)";
		
		int i = 0;
		sqlValues = "(";
		for (Object object : values) {
			sqlValues += "'" + object + "'" + (++i < values.size() ? ", " : ")");
		}
				
		sqlQuery += sqlColumns + " VALUES " + sqlValues;
		System.out.println("\nПолученный SQL-запрос:\n" + sqlQuery);
		return stmt.executeUpdate(sqlQuery) > 0;

	}
}
