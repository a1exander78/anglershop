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


@WebServlet("/Catalog")
public class Catalog extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html; character=UTF-8");	
		request.setCharacterEncoding("UTF-8");
		
		Cookie[] cookies = request.getCookies();
		
		int idBasket = 0;
		int idUser = 0;
		
		if(cookies != null) {
			for (Cookie cookie: cookies) {
				if(cookie.getName().equals("idBasket")){
					idBasket=Integer.parseInt(cookie.getValue());}
				if(cookie.getName().equals("login")){
					idUser=Integer.parseInt(cookie.getValue());
				}		
			}
		}
		
		var id = Integer.parseInt(request.getParameter("id"));
		
		if(id != 0) {
			
			try {
				if(BaseModel.addGood(id, idBasket, idUser)) {
					System.out.println("Товар добавлен в корзину");
				}else {
					System.out.println("Ошибка добавления товара в корзину");
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
