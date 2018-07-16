<jsp:include page="/includes/adminHeader.jsp" />
<jsp:include page="/includes/adminLeftColumn.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="indexRightColumn">
	<div id="emailListColumn">
		<h1 class="hclass">Email subscribers list</h1>

		<c:if test="${emailSubscribers == null}">
			<p class="pclass">Empty list</p>
		</c:if>

		<c:if test="${emailSubscribers != null}">
			<table class="nonFormTable" id="emailListTable">
				<tr class="tableHeader">
					<th>ID</th>
					<th>First name</th>
					<th>Last name</th>
					<th>Email</th>

				</tr>
				<c:forEach var="emailSubscriber" items="${emailSubscribers }">
					<tr class="noImageTableData">
						<td><c:out value="${emailSubscriber.id}" /></td>
						<td><c:out value="${emailSubscriber.firstName}" /></td>

						<td><c:out value="${emailSubscriber.lastName}" /></td>

						<td><c:out value="${emailSubscriber.email}" /></td>

					</tr>
				</c:forEach>

			</table>
		</c:if>


	</div>
</div>
<jsp:include page="/includes/footer.jsp" />