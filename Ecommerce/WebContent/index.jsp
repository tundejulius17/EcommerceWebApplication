<%@page import="dbAdapter.DBProduct"%>
<%@page import="dataModel.Product"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/custLeftColumn.jsp" />
    
<%	
	List<Product> products = DBProduct.getProducts(); 
	session.setAttribute("custProducts", products);

%>

<div id="productsRightColumn">

	<p class="pclass">Welcome to the home page of T&T retail store.</p>
	<p class="pclass">We wish you a memorable online experience!</p>

	<c:forEach var="product" items="${custProducts}">

		<c:if test="${product.quantity>0 }">

			<div class="floating-box">
				<table>
					<tr>
						<td><img
							src="${pageContext.servletContext.contextPath }/imageDownload?id=${product.id}"
							id="productImage" alt="productImage" /></td>

					</tr>

					<tr>
						<td><c:out value="${product.code }" /></td>
					</tr>

					<tr>
						<td><c:out value="${product.description }" /></td>
					</tr>

					<tr>
						<td><fmt:formatNumber type="currency" maxFractionDigits="2"
								currencySymbol="&euro; " value="${product.price }" /></td>
					</tr>

					<tr>
						<td><form action="<c:url value='/order/addCartItem'/>"
								method="post">
								<input type="hidden" name="code" value="${product.code}">
								<input type="submit" value="add to cart">

							</form></td>
					</tr>
				</table>
			</div>
		</c:if>
	</c:forEach>

</div>

<jsp:include page="/includes/footer.jsp" />