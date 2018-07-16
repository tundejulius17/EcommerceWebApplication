<jsp:include page="/includes/adminHeader.jsp" />
<jsp:include page="/includes/adminLeftColumn.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="indexRightColumn">
	<div id="singleColumn">

		<p class="pclass">Note! All fields marked with an asterisk (*) are
			mandatory.</p>
		
		<p class="pclass">Edit the category data.</p>
		<p class="pclass">
			<i style="font-size:90%; color:red">${message}</i>
		</p>
		<form action="<c:url value='/adminController/editCategory'/>"
			method="post">
			<table class="formTable">

				<tr>
					<td><label>Category name *</label></td>

					<td><input type="text" name="newName"
						value='<c:out value="${categoryName}"/>' required> <input
						type="hidden" name="oldName"
						value='<c:out value="${categoryName}"/>'><br></td>
					<%-- <td style="font-size:80%; color:red" ><i>${nameError}</i><br></td> --%>

				</tr>

				<tr>
					<td></td>

					<td><input type="submit" value="Edit category" id="submit">
					</td>
				</tr>


			</table>

		</form>

	</div>
</div>

<jsp:include page="/includes/footer.jsp" />