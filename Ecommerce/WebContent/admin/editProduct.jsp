<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="dbAdapter.DBCategory"%>
<%@page import="dataModel.Category"%>
<%@page import="java.util.List"%>

<jsp:include page="/includes/adminHeader.jsp" />
<jsp:include page="/includes/adminLeftColumn.jsp" />


 <%	
	List<Category> categories = DBCategory.getCustCategories();
 	session.setAttribute("adminCategories", categories);

%>

<div id="indexRightColumn">
	<div id="addProductColumn">
		<p class="pclass">Note! All fields marked with an asterisk (*) are
			mandatory.</p>
		
		<p class="pclass">Edit product data.</p>
		<p class="pclass">
			<i style="font-size: 90%; color: red">${message}</i>
		</p>
		
		<form action="<c:url value='/adminController/editProduct'/>"
			enctype="multipart/form-data" method="post">
			<table class="formTable">

				<tr>
					<td><label>Code </label></td>

					<td><input type="text"
						value="<c:out value="${editProduct.code }" />" name="code"
						readonly><br></td>

				</tr>

				<tr>
					<td><label>Description *</label></td>

					<td><input type="text"
						value="<c:out value="${editProduct.description }" />"
						name="description" required><br></td>

				</tr>

				<tr>
					<td><label>Price *</label></td>

					<td><input type="number" min="0.1" step="0.01"
						value="<c:out value="${editProduct.price }" />" name="price"
						required><br></td>

				</tr>

				<tr>
					<td><label>Image *</label></td>
					<td><img
						src="${pageContext.servletContext.contextPath }/imageDownload?id=${editProduct.id}"
						id="productImage" alt="productImage" />
				</tr>

				<tr>
					<td></td>
					<td><input type="file" name="image" required></td>
				</tr>

				<tr>
					<td><label>Quantity *</label></td>

					<td><input type="number" min="1"
						value="<c:out value="${editProduct.quantity }" />" name="quantity"
						required><br></td>

				</tr>

				<tr>
					<td><label>Category *</label></td>
					<td><select class="selectTag" name="categoryName" required>
							<c:forEach var="category" items="${adminCategories}">
								<option>${category.name}</option>
							</c:forEach>
					</select> <br></td>
				</tr>

				<tr>
					<td></td>

					<td><input type="submit" value="Edit product" id="submit"><input
						type="hidden" name="id" value='<c:out value="${editProduct.id}"/>'>
					</td>
				</tr>

			</table>

		</form>

	</div>
</div>
<jsp:include page="/includes/footer.jsp" />