<jsp:include page="/includes/adminHeader.jsp" />
<jsp:include page="/includes/adminLeftColumn.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="indexRightColumn">
	<h1 class="hclass">Payments Report Form</h1>
	<p class="pclass">Please enter dates using this format: yyyy-mm-dd.</p>
	<p class="pclass">
		<i style="font-size: 90%; color: red">${message}</i>
	</p>

	<form action="<c:url value='/adminController/getPayments'/>"
		method="post">

		<table class="formTable">

			<tr>
				<td><label>Start date</label></td>
				<td><input type="text" name="startDate"
					value="${currentYear}-01-01" required><br></td>

			</tr>

			<tr>
				<td><label>End date</label></td>
				<td><input type="text" name="endDate"
					value="${currentYear}-12-31" required><br></td>
			</tr>

			<tr>
				<td></td>
				<td><input type="submit" value="Continue" id="submit">
				</td>
			</tr>

		</table>

	</form>

</div>

<jsp:include page="/includes/footer.jsp" />