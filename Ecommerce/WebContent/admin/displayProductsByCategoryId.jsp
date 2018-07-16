<jsp:include page="/includes/adminHeader.jsp" />
<jsp:include page="/includes/adminLeftColumn.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="indexRightColumn">
	<div id="productListColumn">

		<c:if test="${fn:length(category.products)==0}">
			<p class="pclass">
				No products for category:
				<c:out value="${category.name}"></c:out>
			</p>
		</c:if>

		<c:if test="${fn:length(category.products)>0}">
			<p class="pclass">
				Product list for category:
				<c:out value="${category.name}"></c:out>
			</p>

			<table class="nonFormTable" id="productListTable">
				<tr class="tableHeader">
					<!-- <th>Id</th> -->
					<th>Code</th>
					<th>Description</th>
					<th>Price</th>
					<th>Image</th>
					<th>Quantity</th>
					<th>Last updated</th>
					<th>Category</th>

				</tr>
				<c:forEach var="product" items="${category.products }">
					<tr class="tableData">
						<%-- <td><c:out value="${product.id }" /></td> --%>

						<td><c:out value="${product.code }" /></td>
						<td><c:out value="${product.description }" /></td>
						<td><fmt:formatNumber type="currency" maxFractionDigits="2"
								currencySymbol="&euro; " value="${product.price}" /></td>
						<td><img
							src="${pageContext.servletContext.contextPath }/imageDownload?id=${product.id}"
							id="productImage" alt="productImage" /></td>
						<td><c:out value="${product.quantity }" /></td>

						<td><c:out value="${product.lastUpdate }" /></td>
						<td><c:out value="${category.name }" /></td>

					</tr>
				</c:forEach>

			</table>
		</c:if>

	</div>
</div>
<jsp:include page="/includes/footer.jsp" />