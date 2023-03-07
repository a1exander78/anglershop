<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="style.css" rel="stylesheet" type="text/css">
<title>У Ручья &#8212; Рыболовный интернет-магазин</title>
<script src="jquery-3.6.0.min.js"></script>
<script src="scripts.js"></script>
<script> 
        display = "block";
        window.onload = function(){setInterval('vision()',3000);}   
</script>
</head>

<body class="simple-linear">
	
	<div id="containerBlock">
        <div id="headerBlock">
        <button type="button" id="name" onclick="location.href='?page=catalog'"></button>
            <h2>Рыболовный интернет-магазин</h2>  
        </div>
        <div id="promoBlock">
            <img id="promoFirst" src="pics/Sales.png" alt="pic" height="100%" width="100%">
        </div>     
        <div id="navBlock">
        <jsp:include page="navBlock.jsp" />
        </div>   	    
	       	<div id="contentBlock">
	       	<h3><a class="jump" href="?page=catalog">КАТАЛОГ ТОВАРОВ</a></h3>
	       	<div id="katalogBlock">                
                		<ul>
                    		<li><a class="goods" href="?page=rods">Спиннинги</a></li>
                    		<li><a class="goods" href="?page=reels">Катушки</a></li>
                    		<li><a class="goods" href="?page=lures">Приманки</a></li>
                    		<li><a class="goods" href="?page=pe">Плетеные шнуры</a></li>
                    		<li><a class="goods" href="?page=boxes">Ящики и сумки</a></li>
                    		<li><a class="goods" href="?page=tools">Инструменты</a></li>
                    		<li><a class="goods" href="?page=wear">Одежда и обувь</a></li>
                    		<li><a class="goods" href="?page=accessories">Аксессуары</a></li>
                		</ul>
            		</div>
				<jsp:include page="${content}" />
			</div>		
        <div class="clear"></div>         		       	
        <div id="footerBlock">
            <h3>
                <a class="info" href="?page=about">О компании</a><br>
                <a class="info" href="?page=contacts">Контакты</a><br>
                <a class="info" href="?page=delivery">Доставка и оплата</a>
            </h3>
        </div>      
    </div>
   
</body>
</html>