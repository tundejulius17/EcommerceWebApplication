<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/includes/adminHeader.jsp" />
<jsp:include page="/includes/adminLeftColumn.jsp" />

<div id="indexRightColumn">
	<div id="addProductColumn">

		<p class="pclass">Enter the old product code to load and edit
			product data.</p>
		<p class="pclass">
			<i>${message}</i>
		</p>
		<form action="<c:url value='/adminController/getProductByCode'/>"
			method="get">
			<table class="formTable">

				<tr>
					<td><label>Code *</label></td>

					<td><input type="text" name="code"
						value='<c:out value="${code}"/>' required><br></td>

				</tr>

				<tr>
					<td></td>

					<td><input type="submit" value="Load product" id="submit">
					</td>
				</tr>


			</table>

		</form>

	</div>
</div>
<jsp:include page="/includes/footer.jsp" />