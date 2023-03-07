<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<table id = "card">
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
		%>
		
		<tr>
			<tr><td colspan="2"><h3 style="text-align: center;">${good.title}</h3></td></tr>
		<tr>
			<td><img width="400" src="pics/${good.img}"></td>
			<td><p style="text-align: left; margin: 20px;">Информация о товаре:<br><br>${good.info}<p></td>
		</tr>
		<tr><td><h3  style="text-align: center;">Стоимость ${good.price} &#8381;</h3></td>
		<td>
			<%if(userID != 0 && userID != 1){ %>
				<button type="button" class="shine-button" onclick="add(${good.id})">Добавить в корзину</button>
			<%}%>
			
			<%if(userID == 1){ %>
				<button type="button" class="shine-button" onclick="location.href='?page=editGoodDB&id=${good.id}'">Редактировать товар</button>
				<button type="button" class="shine-button" onclick="deleteGoodDB(${good.id})">Удалить товар</button>
			<%}%>
		</td>
		</tr>
	</table>