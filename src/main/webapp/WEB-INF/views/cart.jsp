<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<table class="user" border="1" >
		<tr>
			<th>Номер товара</th>
			<th>Название товара</th>
			<th>Стоимость товара</th>
			<th>Количество</th>
			<th>Удалить</th>
		</tr>
		<c:forEach var="good" items="${goods}" varStatus="num">
			<tr>
				<td>${num.count}</td>
				<td>${good.title}</td>
				<td>${good.price} &#8381;</td>
				<td>${good.count}</td>			
				<td><button class="shine-button" onclick="editOrder(${good.id})">Удалить</button></td>
			</tr>
		</c:forEach >
			<%	Cookie[] cookies = request.getCookies();
				int idBasket = 0;
				
				if(cookies != null) {
					for (Cookie cookie: cookies) {
						
						if(cookie.getName().equals("idBasket"))
							idBasket = Integer.parseInt(cookie.getValue());						
							out.println("<tr><td colspan='5'><button class='shine-button' onclick='confirmOrder()'>Сформировать заказ</button></td></tr>");
						break;
					}
				}
			%>
	</table>