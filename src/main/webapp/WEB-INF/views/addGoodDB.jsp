<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
		<div class="user" align="center">
			<br>
			<h3>Добавить товар в базу данных</h3>
			<br>
			
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
							
			<p>Введите наименование товара</p>
			<input type="text" class="text_field" id="title">
			
			<p>Введите стоимость товара</p>
			<input type="number" class="text_field" id="price">
			
			<p>Введите имя файла с изображением товара</p>
			<input type="text" class="text_field" id="img">
			
			<p>Введите информацию о товаре</p>
			<textarea class="text_field" id="info" style="min-width: 200px; max-width: 750px; min-height: 40px;"></textarea>
								
			<p><button class="shine-button" onclick="addGoodDB()">Добавить</button>
			
			<p id="answerDB"></p>
		</div>		