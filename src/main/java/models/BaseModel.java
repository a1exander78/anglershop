package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




public class BaseModel {
	static ArrayList<Good> goods = new ArrayList<Good>();
	static ArrayList<Cart> cart = new ArrayList<Cart>();
	static ArrayList<Order> order = new ArrayList<Order>();
	static Connection connection;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (connection == null) {
		Class.forName(Config.DRIVER);
		String url = "jdbc:mysql://localhost/" + Config.DB;
		connection = DriverManager.getConnection(url,Config.LOGIN,Config.PASS);
		}
		return connection;
	}
	
	/*
	 * Выборка 8 популярных товаров для главной страницы
	 */
	public static List<Good> getPopularGoods() throws ClassNotFoundException, SQLException {
		var c = getConnection();
		var ps = c.prepareStatement("SELECT * FROM goods WHERE popularity = 'y' LIMIT 8");
		ResultSet rs = ps.executeQuery();
		goods.clear();
		while(rs.next()) {
			var id = rs.getInt("id_good");
			var price = rs.getInt("price");
			var title = rs.getString("title");
			var info = rs.getString("info");
			var img = rs.getString("img");
			goods.add(new Good(id, price,title, info, img));
		}
		return goods;
	}

	/*
	 * Выборка всех товаров в категории
	 * Тоже поставил limit 8, надо разбираться, как динамически строить блоки (можно бы было применить другую верстку, но пока оставил эту)
	 */
	public static List<Good> getGoods(int category) throws ClassNotFoundException, SQLException {
		var c = getConnection();
		var ps = c.prepareStatement("SELECT * FROM goods WHERE id_category ='" + category + "' LIMIT 8");
		ResultSet rs = ps.executeQuery();
		goods.clear();
		while(rs.next()) {
			var id = rs.getInt("id_good");
			var price = rs.getInt("price");
			var title = rs.getString("title");
			var info = rs.getString("info");
			var img = rs.getString("img");
			goods.add(new Good(id, price,title, info, img));
		}
		return goods;
	}
	
	public static List<Cart> getCart(int id_basket) throws ClassNotFoundException, SQLException {
		var c = getConnection();
		var ps = c.prepareStatement("SELECT basket.id_basket, basket.id_good, basket.count, goods.title, goods.price FROM basket LEFT JOIN goods ON basket.id_good = goods.id_good WHERE basket.id_basket =" + id_basket);
		ResultSet rs = ps.executeQuery();
		cart.clear();
		while(rs.next()) {
			var id = rs.getInt("basket.id_good");
			var price = rs.getInt("goods.price");
			var title = rs.getString("goods.title");
			var iBasket = rs.getInt("basket.id_basket");
			var count = rs.getInt("basket.count");
			cart.add(new Cart(id, price,title, count,iBasket));
		}
		return cart;
	}
	
	public static List<Order> getOrder(int idUser) throws ClassNotFoundException, SQLException {
		var c = getConnection();
		var sqlSelect = "";
		if(idUser == 1) {
			sqlSelect="SELECT * FROM basket LEFT JOIN goods ON basket.id_good=goods.id_good LEFT JOIN orders ON basket.id_order=orders.id_order LEFT JOIN users ON basket.id_user = users.id_user WHERE basket.id_order IS NOT NULL";

		}else {
			sqlSelect="SELECT * FROM basket LEFT JOIN goods ON basket.id_good=goods.id_good LEFT JOIN orders ON basket.id_order=orders.id_order LEFT JOIN users ON basket.id_user = users.id_user WHERE basket.id_user =" + idUser + " AND basket.id_order IS NOT NULL";
		}
		var ps = c.prepareStatement(sqlSelect);
		ResultSet rs = ps.executeQuery();
		order.clear();
		while(rs.next()) {
			var id = rs.getInt("basket.id_order");
			var title = rs.getString("goods.title");
			var price = rs.getInt("goods.price");
			var status = rs.getInt("orders.status");
			var sum = rs.getInt("orders.sum");
			var count = rs.getInt("basket.count");
			var username=rs.getString("users.login");
			order.add(new Order(id, price,title, status, sum,count,username));
		}
		return order;
	}
	
	public static int maxBasket() throws ClassNotFoundException, SQLException {
		var c = getConnection();
		var ps0 = c.prepareStatement("SELECT COALESCE(MAX(id_basket), 0) AS 'id_basket' FROM basket");
		var rs0 = ps0.executeQuery();
		int id_basket = 0;
		if(rs0.next()) {
			id_basket = rs0.getInt("id_basket") + 1;
		}
		   return id_basket;
	}
	
	private static int getSumOrder(int idBasket)  throws ClassNotFoundException, SQLException {
		var c = getConnection();
		var selectSumBasket="SELECT SUM(basket.count * goods.price) AS SUMORDER FROM basket LEFT JOIN goods ON basket.id_good = goods.id_good WHERE basket.id_basket =" + idBasket;
		var selectSumBasketPs = c.prepareStatement(selectSumBasket);
		ResultSet selectSumBasketRs = selectSumBasketPs.executeQuery();
		int summaryOrder=0;
		while(selectSumBasketRs.next()) {
			if(selectSumBasketRs.getInt("SUMORDER") != 0){
				summaryOrder=selectSumBasketRs.getInt("SUMORDER");
				}
			}
	   return summaryOrder;
	}
	
	private static boolean addOrder(int idBasket,int orderStatus,int orderSum)  throws ClassNotFoundException, SQLException {
		var c = getConnection();
		var sqlInsert = "INSERT INTO orders(id_basket,status,sum) VALUES("+ idBasket + ","+ orderStatus + "," + orderSum +")";
		var insert = c.prepareStatement(sqlInsert);
		if(insert.executeUpdate() > 0) {
			return true;
		}
		return false;
	}
	
	private static boolean addOrderIdInBasketTable(int idBasket)  throws ClassNotFoundException, SQLException {
		var c = getConnection();
		var url="SELECT id_order FROM orders WHERE id_basket =" + idBasket;
		var ps = c.prepareStatement(url);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			if(rs.getString("id_order")!= null){
					var sqlUpdate = c.prepareStatement("UPDATE basket SET id_order =" + rs.getInt("id_order") + " WHERE id_basket =" + idBasket);
					if(sqlUpdate.executeUpdate() > 0) {
						return true;
					}
				}
			}
		return false;
	}
	
	
	public static boolean createOrder(int idBasket,int orderStatus,int orderSum) throws ClassNotFoundException, SQLException {
		int sumOrder = getSumOrder(idBasket);
		boolean result = addOrder(idBasket, orderStatus, sumOrder);
		boolean resultAdd = addOrderIdInBasketTable(idBasket);
		if (result && resultAdd) {
			return true;
		}
		return false;
	}
	
	/*
	 * Использован класс BaseSQL
	 */
	public static boolean editBasket(int idBasket,int idGood) throws ClassNotFoundException, SQLException {
		String where = "WHERE id_basket ='" + idBasket + "' AND id_good ='" + idGood + "'";
		if (BaseSQL.getInstance().delete("basket", where)) {
			return true;
		} else {
			System.out.println("Ошибка");
			return false;
		}		
	}
	
	public static boolean addGood(int idGood,int idBasket,int idUser) throws ClassNotFoundException, SQLException {
		var c = getConnection();
		var sql = "SELECT * FROM basket WHERE id_good =" + idGood + " AND id_basket ="+idBasket;
		var ps1 = c.prepareStatement(sql); 
		var rs1 = ps1.executeQuery();
		if(rs1.next()) {
			var update = c.prepareStatement("UPDATE basket SET count = count + 1 WHERE id_good =" + idGood + " AND id_basket =" + idBasket); 
			if(update.executeUpdate() > 0) {
				return true;
			}
		}else {
			var sqlInsert = "INSERT INTO basket(id_good, count, id_basket, id_user) VALUES("+ idGood +", 1, "+ idBasket +", "+ idUser +")";
			var insert = c.prepareStatement(sqlInsert); 		
			if(insert.executeUpdate() > 0) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean setOrderStatus(int idOrder) throws ClassNotFoundException, SQLException {
		var c = getConnection();
		var ps = c.prepareStatement("SELECT status FROM orders WHERE id_order =" + idOrder);
		ResultSet rs = ps.executeQuery();
		var status=0;
		while(rs.next()) {
			if (rs.getInt("status") < 4) {
				status = rs.getInt("status") + 1;
			
			}else {
				status = rs.getInt("status");
			}		
		}
		var sqlUpdate="UPDATE orders SET status =" + status + " WHERE id_order =" + idOrder;
		var update = c.prepareStatement(sqlUpdate); 			
		if(update.executeUpdate() > 0) {
			return true;
		}
		return false;
	}
	
	public static boolean checkUsername(String login) throws SQLException, ClassNotFoundException {
		var c = getConnection();
		var url = "SELECT login FROM users WHERE login ='" + login + "'";
		var ps = c.prepareStatement(url);
		var rs = ps.executeQuery();
		while(rs.next()) {
			if(rs.getString("login") != null){
				return false;
			}
		}
		return true;
	}
		
	
	public static boolean regNewUser(String login, String password, String email, String number) throws SQLException, ClassNotFoundException {
		var c = getConnection();
		var url = "INSERT INTO `users` (`login`, `password`, `email`, `phone`) VALUES ('" + login + "','" + password + "','" + email + "','" + number + "')";
		var ps = c.prepareStatement(url);
		if (ps.executeUpdate() > 0) {
			return true;
		}
		return false;
	}

	public static boolean authorization(String login, String password) throws SQLException, ClassNotFoundException {
		var c = getConnection();
		var ps = c.prepareStatement("SELECT login, password FROM users");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			if(login.equals(rs.getString("login"))) {
				if(password.equals(rs.getString("password"))) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static int getUserId(String login) throws SQLException, ClassNotFoundException {
		var c = getConnection();
		var url="SELECT id_user FROM users WHERE login ='" + login + "'";
		var ps = c.prepareStatement(url);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			if(rs.getString("id_user") != null){
					return rs.getInt("id_user");
				}
			}
		return 0;
	}
	
	public static int getCreateBasket(int userId) throws SQLException, ClassNotFoundException {
		var c = getConnection();
		var url="SELECT id_basket FROM basket WHERE id_user =" + userId + " AND id_order IS NULL LIMIT 1";
		var ps = c.prepareStatement(url);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			if(rs.getString("id_basket")!= null){
					return rs.getInt("id_basket");
				}
			}
		return 0;
	}
	
	public static Good getGood(int id) throws SQLException, ClassNotFoundException {
		var c = getConnection();
		var ps = c.prepareStatement("select * from goods where id_good='"+id+"'");
		ResultSet rs = ps.executeQuery();
		goods.clear();
		while(rs.next()) {

			var price = rs.getInt("price");
			var title = rs.getString("title");
			var info = rs.getString("info");
			var img = rs.getString("img");
			goods.add(new Good(id, price,title, info, img));
		}
		for (Good good : goods) {
			if(good.getId() == id) {
				return good;
			}
		}
		return null;
	}

/*
 * Использован класс BaseSQL
 */
public static boolean deleteGoodDB(int idGood) throws ClassNotFoundException, SQLException {
	String where = "WHERE id_good ='" + idGood + "'";
	if (BaseSQL.getInstance().delete("goods", where)) {
		return true;
	} else {
		System.out.println("Ошибка");
		return false;
	}		
}

/*
 * Использован класс BaseSQL
 */
public static boolean editGoodDB(int idGood, int category, String popularity, String title, int price, String info, String img) throws ClassNotFoundException, SQLException {
	String where = "WHERE id_good ='" + idGood + "'";
	
	HashMap<String, Object> fields = new HashMap<String, Object>();
	fields.put("id_category", category);
	fields.put("popularity", popularity);
	fields.put("title", title);
	fields.put("price", price);
	fields.put("info", info);
	fields.put("img", img);
	
	if (BaseSQL.getInstance().update("goods", fields, where)) {
		return true;
	} else {
		System.out.println("Ошибка");
		return false;
	}		
}

/*
 * Использован класс BaseSQL
 */
public static boolean addGoodDB(int category, String popularity, String title, int price, String info, String img) throws ClassNotFoundException, SQLException {
	
	var columns = new ArrayList<String>();
	columns.add("id_category");
	columns.add("popularity");
	columns.add("title");
	columns.add("price");
	columns.add("info");
	columns.add("img");
	
	var values = new ArrayList<Object>();
	values.add(category);
	values.add(popularity);
	values.add(title);
	values.add(price);
	values.add(info);
	values.add(img);
	
	if (BaseSQL.getInstance().add("goods", columns, values)) {
		return true;
	} else {
		System.out.println("Ошибка");
		return false;
	}		
}
}
