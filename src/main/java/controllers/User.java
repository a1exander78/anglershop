package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.BaseModel;


@WebServlet("/User")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void addCookies(String login,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		try {
			int userId=BaseModel.getUserId(login);
			int idBasket = BaseModel.getCreateBasket(userId);
			if(idBasket == 0) {
				idBasket = BaseModel.maxBasket();
			}
			Cookie cookieBasket = new Cookie("idBasket", Integer.toString(idBasket));
			Cookie cookieLogin = new Cookie("login", Integer.toString(userId));
			response.addCookie(cookieBasket);
			response.addCookie(cookieLogin);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		var action = request.getParameter("action");
		var login = request.getParameter("login");
		var password = request.getParameter("password");
		
		int id, category, price;
		String popularity, info, img, title;

		switch(action) {
			
			case "auth":
				
				try {
					if(BaseModel.authorization(login, password)) {
						if (request.getCookies() != null) {
							Cookie arr_cookie[] = request.getCookies();
							boolean checkCookie = false;
							for (var i = 0; i < arr_cookie.length; i++) {
								if (arr_cookie[i].getName().equals("idBasket")) {
									System.out.println("Вы уже авторизованы");
									checkCookie=true;
								}
							}
							if (!checkCookie) {
								addCookies(login, request, response);
								System.out.println("Вы успешно авторизованы");
							}
							
						}
						else {
						addCookies(login, request, response);
						}
					}else {						
						System.out.println("Ошибка входа");
					}
				} catch (ClassNotFoundException | SQLException | IOException e) {
					e.printStackTrace();
				}
				
				break;
			
			case "reg":
				
				var email = request.getParameter("email");
				var number = request.getParameter("number");				
				
				try {
					if(BaseModel.checkUsername(login)) {
						if(BaseModel.regNewUser(login, password, email, number)) {
							System.out.println("Вы зарегистрированы");
						}
					}
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				
				break;
			
			
			case "logout":
				
				Cookie[] cookies = request.getCookies();
				if(cookies !=null) {
					for (Cookie cookie: cookies) {
							cookie.setMaxAge(0);
							response.addCookie(cookie);
					}
					System.out.println("Вы вышли из учетной записи");
				}
				
				break;
				
			case "deleteGoodDB":
				
				id = Integer.parseInt(request.getParameter("id"));
				
				try {
					BaseModel.deleteGoodDB(id);
					System.out.println("Товар удален из БД");
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				
				break;
				
			case "addGoodDB":			
			
				category = Integer.parseInt(request.getParameter("category"));
				popularity = request.getParameter("popularity");
				title = request.getParameter("title");
				price = Integer.parseInt(request.getParameter("price"));
				info = request.getParameter("info");
				img = request.getParameter("img");
					
				System.out.println(category + popularity + title + price + info + img);
				
				try {
					BaseModel.addGoodDB(category, popularity, title, price, info, img);
					System.out.println("Товар добавлен в БД");
				} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
				}
	
				break;
				
			case "editGoodDB":			
			
				id = Integer.parseInt(request.getParameter("id"));
				category = Integer.parseInt(request.getParameter("category"));
				popularity = request.getParameter("popularity");
				title = request.getParameter("title");
				price = Integer.parseInt(request.getParameter("price"));
				info = request.getParameter("info");
				img = request.getParameter("img");
					
				System.out.println(category + popularity + title + price + info + img);
					
				try {
					BaseModel.editGoodDB(id, category, popularity, title, price, info, img);
					System.out.println("Информация о товаре обновлена в БД");
				} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
				}
				
				break;
			
		}
	}
}
