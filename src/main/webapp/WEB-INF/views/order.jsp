<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<table class="user" border="1">
	<tr>
			<th>Номер позиции</th>
			<th>Номер заказа</th>
			<th>Название</th>
			<th>Количество</th>
			<th>Статус</th>
			<th>Стоимость</th>
			
			<%Cookie[] cookies = request.getCookies();
			int userID = 0;
			if(cookies != null) {
				for (Cookie cookie: cookies) {
					if(cookie.getName().equals("login")){
						userID = Integer.parseInt(cookie.getValue());
						break;
					}				
				}
			}
         	
			if (userID == 1){ %> 
         		<th>Клиент</th>
				<th>Действие</th>
			<%}%>
			
		</tr>
		<c:forEach var="good" items="${goods}" varStatus="num">
			<tr>
				<td>${num.count}</td>
				<td>${good.idOrder}</td>
				<td>${good.title}</td>
				<td>${good.count}</td>
				<td>${good.orderStatus}</td>
				<td>${good.price} &#8381;</td>
			
			<c:set var="sum">${good.sum}</c:set>				
			<c:set var="ord">${good.idOrder}</c:set>
				
			<%if (userID == 1){ %> 
	         	<td>${good.username}</td>
				<td><button class="shine-button" onclick="changeStatus(${good.idOrder})">Обработать</button></td>
			<%}%>
			</tr>
		</c:forEach >
	</table> 