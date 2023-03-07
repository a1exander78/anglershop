package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Cookie;

import models.BaseModel;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cookie[] cookies = request.getCookies();
		
		int idBasket = 0;
		int userID = 0;
		
		if(cookies != null) {
			for (Cookie cookie: cookies) {
				if(cookie.getName().equals("idBasket"))
					idBasket=Integer.parseInt(cookie.getValue());
				if(cookie.getName().equals("login"))
					userID=Integer.parseInt(cookie.getValue());
			}
		}
		
		var include = "catalog";
		
		try {
			if(request.getParameter("page") != null) {
				include = request.getParameter("page");
			}
			
			switch (include) {
			
				case "order":
					request.setAttribute("goods", BaseModel.getOrder(userID));
					break;
					
				case "catalog":
					request.setAttribute("goods", BaseModel.getPopularGoods());
					break;
					
				case "cart":
					request.setAttribute("goods", BaseModel.getCart(idBasket));
					break;
					
				case "card":
					int id = Integer.parseInt(request.getParameter("id"));
					request.setAttribute("good", BaseModel.getGood(id));
					break;
					
				case "editGoodDB":
					int idGood = Integer.parseInt(request.getParameter("id"));
					request.setAttribute("good", BaseModel.getGood(idGood));
					break;
					
				case "rods":
					request.setAttribute("goods", BaseModel.getGoods(1));
					break;
					
				case "reels":
					request.setAttribute("goods", BaseModel.getGoods(2));
					break;
					
				case "lures":
					request.setAttribute("goods", BaseModel.getGoods(3));
					break;
					
				case "pe":
					request.setAttribute("goods", BaseModel.getGoods(4));
					break;
					
				case "boxes":
					request.setAttribute("goods", BaseModel.getGoods(5));
					break;
					
				case "tools":
					request.setAttribute("goods", BaseModel.getGoods(6));
					break;
					
				case "wear":
					request.setAttribute("goods", BaseModel.getGoods(7));
					break;
					
				case "accessories":
					request.setAttribute("goods", BaseModel.getGoods(8));
					break;
			}
			
			request.setAttribute("content", include + ".jsp");
			request.getRequestDispatcher("WEB-INF/views/main.jsp").forward(request, response);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
