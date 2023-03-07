<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<table class = "user">
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
			<%if(userID == 1){ %>
				<p>Выберите категорию товара</p>			
				<p>
				<select class="text_field" id="category">
					<option value = "undefined">Категории:</option>
					<option value = "1">Спиннинги</option>
					<option value = "2">Катушки</option>
					<option value = "3">Приманки</option>
					<option value = "4">Плетеные шнуры</option>
					<option value = "5">Ящики и сумки</option>
					<option value = "6">Инструменты</option>
					<option value = "7">Одежда и обувь</option>
					<option value = "8">Аксессуары</option>		
				</select>
				</p>
				
				<p>Выберите популярность товара</p>
				<p>
				<select class="text_field" id="popularity">
					<option value = "undefined">Популярность:</option>
					<option value = "y">Популярный</option>
					<option value = "n">Не популярный</option>
				</select>
				</p>
								
				<p>Введите новое наименование товара</p>
				<input type="text" class="text_field" id="title">
				
				<p>Введите новую стоимость товара</p>
				<input type="number" class="text_field" id="price">
				
				<p>Введите новое имя файла с изображением товара</p>
				<input type="text" class="text_field" id="img">
				
				<p>Введите новую информацию о товаре</p>
				<textarea class="text_field" id="info" style="min-width: 200px; max-width: 750px; min-height: 40px;"></textarea>
									
				<p><button class="shine-button" onclick="editGoodDB(${good.id})">Изменить</button>
			<%}%>
		</td>
		</tr>
	</table>