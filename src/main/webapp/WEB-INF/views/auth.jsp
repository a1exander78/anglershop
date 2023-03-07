<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
		<div id="auth" class="user" align="center">
			<br>
			<h3>АВТОРИЗАЦИЯ</h3>
			<br>
			<p>Ваш логин</p>
			<input type="text" class="text_field" id="login">
			<p>Ваш пароль</p>
			<input type="text" class="text_field" id="password">			
			<p><button class="shine-button" onclick="authUser()">Войти</button>
			<p><a href="?page=reg">Регистрация</a></p>
			<p id="answerDB"></p>
		</div>
