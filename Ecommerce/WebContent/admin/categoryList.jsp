<jsp:include page="/includes/adminHeader.jsp" />
<jsp:include page="/includes/adminLeftColumn.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="indexRightColumn">
	<div id="categoryListColumn">

		<h1 class="hclass">Product Categories</h1>
		<p class="hclass">
			To add a category, click <a
				href="<c:url value='/admin/addCategory.jsp' />" class="redirectLink">here.</a>
		</p>

		<p class="pclass">
			<i style="font-size: 90%; color: red">${message}</i>
		</p>

		<c:if test="${categories != null}">

			<table class="nonFormTable" id="categoryListTable">
				<tr class="tableHeader">
					<th>ID</th>
					<th>Name</th>
					<th>Last Update</th>
					<th>Action</th>
				</tr>

				<c:forEach var="category" items="${categories }">
					<tr class="noImageTableData">
						<td>${category.id}</td>

						<td>${category.name}</td>

						<td>${category.lastUpdate}</td>

						<td><a
							href="<c:url value='/adminController/getProductsByCategoryId
				?id=${category.id}'/>"
							class="redirectLink">Display products |</a><a
							href="<c:url value='/adminController/getCategoryName
				?id=${category.id}'/>"
							class="redirectLink"> Edit |</a><a
							href="<c:url value='/adminController/deleteCategory
				?id=${category.id}'/>"
							class="redirectLink"> Delete </a></td>

					</tr>
				</c:forEach>

			</table>
		</c:if>

	</div>
</div>

<jsp:include page="/includes/footer.jsp" />