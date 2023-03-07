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

@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; character=UTF-8");	
		request.setCharacterEncoding("UTF-8");
		
		Cookie[] cookies = request.getCookies();
		
		var sum = 0;
		int idBasket = 0;
		
		if(cookies != null) {
			for (Cookie cookie: cookies) {
				if(cookie.getName().equals("idBasket"))
					idBasket=Integer.parseInt(cookie.getValue());
			}
		}
		
		var action = request.getParameter("action");

		switch(action) {
		
			case "confirm":
				
				try {
					if(BaseModel.createOrder(idBasket, 1, sum)) {
						if(cookies !=null) {
							for (Cookie cookie: cookies) {
								if(cookie.getName().equals("idBasket"))
									cookie.setMaxAge(0);
							}
						}
						String id_basket = Integer.toString(BaseModel.maxBasket());
						Cookie cookie_basket = new Cookie("idBasket", id_basket);
						response.addCookie(cookie_basket);
						System.out.println("Заказ сформирован");
					}
					
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;
				
			case "delete":
				
				var id = Integer.parseInt(request.getParameter("id"));
				
				try {
					if(BaseModel.editBasket(idBasket, id)) {
						System.out.println("Товар удален из корзины");
					}
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;
		}
	}
}
