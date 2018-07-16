<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/custLeftColumn.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="indexRightColumn">

	<div id="shoppingCartColumn">

		<h1 class="hclass">Your Shopping Cart</h1>
		<c:choose>
			<c:when test="${cart.count > 1}">
				<p class="pclass">You have ${cart.count} items in your cart.</p>
			</c:when>
			<c:when test="${cart.count == 1 }">
				<p class="pclass">You have 1 item in your cart.</p>
			</c:when>
			<c:otherwise>
				<p class="pclass">Your cart is empty.</p>
			</c:otherwise>
		</c:choose>
		<p class="pclass">
			<i style="color: red"><c:out value="${insuffientQuantity}" /></i>
		</p>

		<table class="nonFormTable" id="cartTable">
			<c:if test="${cart.count > 0 }">
				<tr class="tableHeader">
					<th>Description</th>
					<th>Image</th>
					<th>Quantity</th>
					<th>Amount</th>
					<th>Remove</th>
				</tr>
			</c:if>
			<c:forEach var="cartItem" items="${cart.cartItems}">
				<c:set var="product" value="${cartItem.product}" />
				<tr class="tableData">
					<td><c:out value="${product.description }" /></td>

					<td><img
						src="${pageContext.servletContext.contextPath }/imageDownload?id=${product.id}"
						id="productImage" alt="productImage" /></td>

					<td>
						<form action="<c:url value='/order/updateCartItem'/>"
							method="post">
							<input type="text" id="quantityTextField" name="quantity"
								value='<c:out value="${cartItem.quantity}"/>'> <input
								type="hidden" name="code"
								value='<c:out value="${product.code}"/>'><input
								type="submit" value="update">
						</form>

					</td>

					<td><fmt:formatNumber type="currency" maxFractionDigits="2"
							currencySymbol="&euro; " value="${cartItem.total}" /></td>

					<td>

						<form action="<c:url value='/order/removeCartItem'/>"
							method="post">
							<input type="hidden" name="code" value="${product.code}">
							<input type="submit" value="remove">

						</form>
					</td>
				</tr>
			</c:forEach>

		</table>

	</div>
	<c:if test="${cart.count > 0 }">
		<p class="pclass" id="orderTotal">
			Order Total:
			<fmt:formatNumber type="currency" maxFractionDigits="2"
				currencySymbol="&euro; " value="${cart.cartTotal}" />
		</p>

		<form action="<c:url value='/checkout'/>" method="post">
			<!-- <input type="hidden" name="LOGOIMG" value="/images/logo3.png"> -->
			<c:set var="count" value="0"></c:set>
			<c:forEach var="cartItem" items="${cart.cartItems }">

				<input type="hidden" name="L_PAYMENTREQUEST_0_NUMBER${count}"
					value="${cartItem.product.code }">

				<input type="hidden" name="L_PAYMENTREQUEST_0_DESC${count}"
					value="${cartItem.product.description }">

				<input type="hidden" name="L_PAYMENTREQUEST_0_QTY${count}"
					value="${cartItem.quantity }">

				<input type="hidden" name="L_PAYMENTREQUEST_0_AMT${count}"
					value="<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${cartItem.product.price }"/>">

				<%-- <input type="hidden" name="L_PAYMENTREQUEST_0_AMT${count}" value="${cartItem.product.price}" > --%>

				<c:set var="count" value="${count + 1 }"></c:set>
			</c:forEach>
			<input type="hidden" name="PAYMENTREQUEST_0_ITEMAMT"
				value="<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${cart.cartTotal }"/>">
			<input type="hidden" name="PAYMENTREQUEST_0_AMT"
				value="<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${cart.cartTotal }"/>">

			<%-- <input type="hidden" name="PAYMENTREQUEST_0_ITEMAMT" value="${cart.cartTotal }">			 
					
			<input type="hidden" name="PAYMENTREQUEST_0_AMT" value="${cart.cartTotal }"> --%>

			<input type="hidden" name="currencyCodeType" value="EUR"> <input
				type="hidden" name="paymentType" value="Sale"> <input
				type="image" class="clearCheckoutContinue"
				src="https://www.paypalobjects.com/webstatic/en_US/i/btn/png/gold-pill-paypalcheckout-26px.png"
				alt="PayPal Checkout">
		</form>
		<a href="<c:url value='/order/clearCart' />"
			class="clearCheckoutContinue"> clear cart</a>
	</c:if>

	<a href="<c:url value='/index.jsp' />" class="clearCheckoutContinue">continue
		shopping</a>

</div>

<jsp:include page="/includes/footer.jsp" />