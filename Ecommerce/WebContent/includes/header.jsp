<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>E-commerce</title>

<link rel="stylesheet" href="<c:url value='/styles/main.css'/> ">

</head>

<body>
	<div id="main">
		<div id="header">


			<ul id="horizontalNav">

				<li class="horizontalList"><a
					href="<c:url value='/cart/cart.jsp' />">view cart</a></li>

				<li class="horizontalList"><a><img
						src="<c:url value='/images/cart.gif'/>" alt="cart icon"></a></li>
				<c:choose>
					<c:when test="${cart.count > 1}">
						<li class="horizontalList"><a>${cart.count} items</a></li>
					</c:when>
					<c:when test="${cart.count == 1 }">
						<li class="horizontalList"><a>${cart.count} item</a></li>
					</c:when>
					<c:otherwise>
						<li class="horizontalList"><a>0 items</a></li>
					</c:otherwise>
				</c:choose>
				<li class="horizontalList"><a
					href="<c:url value='/emailSubscription/index.jsp'>
				</c:url>">
						subscribe to email list</a></li>

			</ul>

			<img src="<c:url value='/images/logo3.png'/>" id="logo"
				alt="T&T logo">
			<h1 id="compName">T&T Oy</h1>
			<h2 id="motto">Your number one retail store!</h2>




		</div>