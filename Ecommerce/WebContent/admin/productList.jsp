<jsp:include page="/includes/adminHeader.jsp" />
<jsp:include page="/includes/adminLeftColumn.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="indexRightColumn">
	<div id="productListColumn">

		<h1 class="hclass">Products</h1>
		<p class="hclass">
			To add a product, click <a
				href="<c:url value='/admin/addProduct.jsp' />" class="redirectLink">here</a>
		</p>
		<p class="pclass">
			<i style="font-size: 90%; color: red">${message}</i>
		</p>

		<c:if test="${products != null}">
			<table class="nonFormTable" id="productListTable">
				<tr class="tableHeader">
					<!-- <th>Id</th> -->
					<th>Code</th>
					<th>Description</th>
					<th>Price</th>
					<th>Image</th>
					<th>Quantity</th>
					<th>Last update</th>
					<th>Category</th>
					<th>Action</th>

				</tr>
				<c:forEach var="product" items="${products }">
					<tr class="tableData">
						<%-- <td>${product.id}</td> --%>

						<td>${product.code}</td>
						<td>${product.description}</td>
						<td><fmt:formatNumber type="currency" maxFractionDigits="2"
								currencySymbol="&euro; " value="${product.price}" /></td>
						<td><img
							src="${pageContext.servletContext.contextPath }/imageDownload?id=${product.id}"
							id="productImage" alt="productImage" /></td>
						<td>${product.quantity}</td>

						<td>${product.lastUpdate}</td>
						<td>${product.category.name}</td>
						<td><a
							href="<c:url value='/adminController/getProductData
				?id=${product.id}'/>"
							class="redirectLink">Edit |</a><a
							href="<c:url value='/adminController/deleteProduct
				?id=${product.id}'/>"
							class="redirectLink"> Delete</a></td>

					</tr>
				</c:forEach>

			</table>
		</c:if>

	</div>
</div>
<jsp:include page="/includes/footer.jsp" />