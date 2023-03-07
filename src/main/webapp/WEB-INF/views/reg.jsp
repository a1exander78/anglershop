<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
		<div id="auth" class="user" align="center">
			<br>
			<h3>РЕГИСТРАЦИЯ</h3>
			<br>
			<p>Введите логин</p>
			<input type="text" class="text_field" id="login">
			<p>Введите пароль</p>
			<input type="text" class="text_field" id="password">
			<p>Введите email</p>
			<input type="text" class="text_field" id="email">
			<p>Введите номет телефона</p>
			<input type="text" class="text_field" id="number">
			<p><button class="shine-button" onclick="regNewUser()">Зарегистрироваться</button>
			<p><a href="?page=auth">Авторизация</a></p>
			<p id="answerDB"></p>
		</div>