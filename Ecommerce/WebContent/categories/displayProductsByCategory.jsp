<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/custLeftColumn.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="productsRightColumn">

	<c:if test="${fn:length(selectedCategory.products)==0}">
		<p class="pclass">
			No available
			<c:out value="${selectedCategory.name}"></c:out>
			products
		</p>
	</c:if>

	<c:if test="${fn:length(selectedCategory.products)>0}">
		<p class="pclass">
			Available
			<c:out value="${selectedCategory.name}"></c:out>
			products
		</p>

		<c:forEach var="product" items="${selectedCategory.products }">
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

	</c:if>
	<p class="pclass">
		Click <a href="<c:url value='/index.jsp' />" class="redirectLink">
			here </a> to return to Home Page
	</p>

</div>