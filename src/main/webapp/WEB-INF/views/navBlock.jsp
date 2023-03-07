<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
            <%
			Cookie[] cookies = request.getCookies();
			int userID = 0;
			if(cookies != null) {
				for (Cookie cookie: cookies) {
					if(cookie.getName().equals("login"))
						userID = Integer.parseInt(cookie.getValue());
				}
			}%>

            <%if(userID != 0){ %>          	
				<button type="button" id="order" onclick="location.href='?page=order'"></button>
			
				<%if(userID != 1){ %>
            		<button type="button" id="cart" onclick="location.href='?page=cart'"></button>
            	<%} %>
            	
            	<%if(userID == 1){ %>
            		<button type="button" id="addGoodDB" onclick="location.href='?page=addGoodDB'"></button>
            	<%} %>
            
            	<button type="button" id="out" onclick="logoutUser()"></button>
			<%} %>
			
			<%if(userID == 0){ %>
				<button type="button" id="in" onclick="location.href='?page=auth'"></button>
			<%} %>