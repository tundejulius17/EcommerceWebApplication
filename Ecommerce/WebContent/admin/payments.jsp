<jsp:include page="/includes/adminHeader.jsp" />
<jsp:include page="/includes/adminLeftColumn.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="indexRightColumn">
	<div id="ordersListColumn">
		<h1 class="hclass">Payments list</h1>

		<c:if test="${payments == null}">
			<p class="pclass">Empty list</p>
		</c:if>

		<c:if test="${payments != null}">
			<table class="nonFormTable" id="paymentsListTable">
				<tr class="tableHeader">
					<th>ID</th>
					<th>Amount</th>
					<th>Payment Date</th>
					<th>Payment Status</th>
					<th>Transaction ID</th>
					<th>Customer ID</th>
					<th>Order ID</th>
					
				</tr>

				<c:forEach var="payment" items="${payments }">

					<tr class="noImageTableData">
						<td><c:out value="${payment.id}" /></td>
						<td><fmt:formatNumber type="currency" maxFractionDigits="2"
								currencySymbol="&euro; " value="${payment.amount}" /></td>
						<td><c:out value="${payment.paymentDate}" /></td>
						<td><c:out value="${payment.paymentStatus}" /></td>
						<td><c:out value="${payment.transactionId}" /></td>
						<td><c:out value="${payment.customer.id}" /></td>
						<td><c:out value="${payment.customerOrder.id}" /></td>

						<%-- <td>
							<form action="<c:url value='/adminController/displayOrder'/>"
								method="post">
								<input type="hidden" name="id" value="${order.id}"> <input
									type="submit" value="view details">
							</form>

						</td> --%>
						
						<%-- <td>
							<form action="<c:url value='/adminController/updateCustomerOrder'/>"
								method="post">
								<input type="hidden" name="id" value="${order.id}"> <input
									type="submit" value="close order">
							</form>

						</td> --%>
					</tr>
				</c:forEach>
			</table>

		</c:if>

	</div>
</div>

<jsp:include page="/includes/footer.jsp" />