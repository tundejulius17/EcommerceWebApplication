<jsp:include page="/includes/adminHeader.jsp" />
<jsp:include page="/includes/adminLeftColumn.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="indexRightColumn">
	<div id="ordersListColumn">
		<h1 class="hclass">Orders list</h1>

		<c:if test="${orders == null}">
			<p class="pclass">Empty list</p>
		</c:if>

		<c:if test="${orders != null}">
			<table class="nonFormTable" id="ordersListTable">
				<tr class="tableHeader">
					<th>ID</th>
					<th>Order Date</th>
					<th>Amount</th>
					<th>Customer ID</th>
					<th>Order Status</th>
					<th>Details</th>

				</tr>

				<c:forEach var="order" items="${orders }">

					<tr class="noImageTableData">
						<td><c:out value="${order.id}" /></td>
						<td><c:out value="${order.orderDate}" /></td>
						<td><fmt:formatNumber type="currency" maxFractionDigits="2"
								currencySymbol="&euro; " value="${order.totalPrice}" /></td>
						<td><c:out value="${order.customer.id}" /></td>
						<td><c:out value="${order.status}" /></td>

						<td>
							<form action="<c:url value='/adminController/displayOrder'/>"
								method="post">
								<input type="hidden" name="id" value="${order.id}"> <input
									type="submit" value="view details">
							</form>

						</td>

					</tr>
				</c:forEach>
			</table>

		</c:if>

	</div>
</div>

<jsp:include page="/includes/footer.jsp" />