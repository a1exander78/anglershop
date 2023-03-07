<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        
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
			
            <c:forEach var="good" items="${goods}" varStatus="num">
            
            <div id="goodBlock${num.count}">
                <img class="good_pic" src="pics/${good.img}">
                <span><a class="good_name" href="?page=card&id=${good.id}">${good.title}</a></span><br>
                <span class="new_price">${good.price} &#8381;</span>
                
                <%if(userID != 0 && userID != 1){ %>
					<button type="button" class="shine-button" onclick="add(${good.id})">Добавить в корзину</button>
				<%}%>
			
				<%if(userID == 1){ %>
					<button type="button" class="shine-button" onclick="location.href='?page=editGoodDB&id=${good.id}'">Редактировать товар</button>
				<%}%>
            </div>
	
			</c:forEach >