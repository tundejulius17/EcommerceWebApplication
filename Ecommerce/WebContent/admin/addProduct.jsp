<%@page import="dbAdapter.DBCategory"%>
<%@page import="dataModel.Category"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
		
		
		<p class="pclass">Enter product data.</p>
		<p class="pclass">
			<i style="font-size: 90%; color: red">${message}</i>
		</p>

		<form action="<c:url value='/adminController/addProduct'/>"
			enctype="multipart/form-data" method="post">
			<table class="formTable">

				<tr>
					<td><label>Code *</label></td>

					<td><input type="text" name="code"
						value='<c:out value="${product.code}"/>' required><br></td>

				</tr>

				<tr>
					<td><label>Description *</label></td>

					<td><input type="text" name="description"
						value='<c:out value="${product.description}"/>' required><br></td>

				</tr>

				<tr>
					<td><label>Price *</label></td>

					<td><input type="number" min="0.1" step="0.01" name="price"
						value='<c:out value="${product.price}"/>' required><br></td>

				</tr>

				<tr>
					<td><label>Image *</label></td>

					<td><input type="file" name="image" required><br></td>

				</tr>

				<tr>
					<td><label>Quantity *</label></td>

					<td><input type="number" min="1" name="quantity"
						value='<c:out value="${product.quantity}"/>' required><br></td>

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

					<td><input type="submit" value="Add product" id="submit">
					</td>
				</tr>

			</table>

		</form>

	</div>
</div>
<jsp:include page="/includes/footer.jsp" />