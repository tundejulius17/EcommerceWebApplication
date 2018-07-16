<jsp:include page="/includes/adminHeader.jsp" />
<jsp:include page="/includes/adminLeftColumn.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="indexRightColumn">
	<div id="ordersListColumn">
		<h1 class="hclass">Order details</h1>
		<table class="orderDetailsTable">
		<tr>
				<td><b>Order ID</b></td>
				<td>${order.id}</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td><b>Date</b></td>
				<td>${order.orderDate}</td>
				<td></td>
				<td></td>
			</tr>
			
			<tr>
				<td><b>Order Status</b></td>
				<td>${order.status}</td>
				<td></td>
				<td></td>
			</tr>
			
			<c:set var="customer" value="${order.customer }" />
			<tr>
				<td><b>Ship To</b></td>
				<td>${customer.firstName}${customer.lastName}<br>
					${customer.street }<br> ${customer.postalCode }<br>
					${customer.city }
				</td>
				<td></td>
			</tr>

			<tr>
				<td colspan="4"><hr></td>
			</tr>
			<tr>
				<td><b>Code</b></td>
				<td><b>Description</b></td>
				<td><b>Qty</b></td>
				<td><b>Price</b></td>
			</tr>
			<c:forEach var="item" items="${order.lineItems}">
				<tr>
					<td>${item.product.code}</td>
					<td>${item.product.description}</td>
					<td>${item.quantity}</td>
					<td><fmt:formatNumber type="currency" maxFractionDigits="2"
							currencySymbol="&euro; "
							value="${(item.product.price)*(item.quantity)}" /></td>
				</tr>
			</c:forEach>

			<tr>
				<td colspan="4"><hr></td>
			</tr>
			<tr class="noImageTableData">
				<td><b>Total</b></td>
				<td></td>
				<td></td>
				<td><p>
						<fmt:formatNumber type="currency" maxFractionDigits="2"
							currencySymbol="&euro; " value="${order.totalPrice}" /></td>
			</tr>

			<tr>
				<td><b>Transaction ID</b></td>
				<td></td>
				<td></td>
				<td>${payment.transactionId }</td>
			</tr>
			<tr>
				<td><b>Transaction Status</b></td>
				<td></td>
				<td></td>
				<td>${payment.paymentStatus }</td>
			</tr>

			<tr>
				<td><b>Email Address</b></td>
				<td></td>
				<td></td>
				<td>${customer.email }</td>
			</tr>

			<tr>
				<td><b>Phone</b></td>
				<td></td>
				<td></td>
				<td>${customer.phone }</td>
			</tr>
			<tr>
			<td>
			<form action="<c:url value='/adminController/updateCustomerOrder'/>"
								method="post">
								<input type="hidden" name="id" value="${order.id}"> <input
									type="submit" value="close order">
							</form>
			</td>
			</tr>

		</table>

	</div>

</div>

<jsp:include page="/includes/footer.jsp" />