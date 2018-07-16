<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/custLeftColumn.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="indexRightColumn">
	<h1 class="hclass">Order Confirmation</h1>

	<p class="pclass">
		Thanks for your order! 
	</p>
	<p class="pclass">
		Check your email for the order confirmation.
	</p>

	<table class="formTable">

		<tr>
			<td style="font-size: 85%"><c:out
					value="${result['PAYMENTREQUEST_0_SHIPTONAME']}" /></td>
		</tr>

		<tr>
			<td style="font-size: 85%"><c:out
					value="${result['PAYMENTREQUEST_0_SHIPTOSTREET']}" /></td>
		</tr>

		<tr>
			<td style="font-size: 85%"><c:out
					value="${result['PAYMENTREQUEST_0_SHIPTOZIP']}" /></td>
		</tr>

		<tr>
			<td style="font-size: 85%"><c:out
					value="${result['PAYMENTREQUEST_0_SHIPTOCITY']}" /></td>
		</tr>

		<tr>
			<td style="font-size: 85%"><c:out
					value="${result['PAYMENTREQUEST_0_SHIPTOSTATE']}" /></td>
		</tr>

		<tr>
			<td style="font-size: 85%"><c:out
					value="${result['PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE']}" /></td>
		</tr>

		<tr></tr>

		<tr>
			<td><label>Transaction ID</label></td>
			<td style="font-size: 85%"><c:out
					value="${result['PAYMENTINFO_0_TRANSACTIONID']}" /></td>
		</tr>

		<tr>
			<td><label>Transaction Type</label></td>
			<td style="font-size: 85%"><c:out
					value="${result['PAYMENTINFO_0_TRANSACTIONTYPE']}" /></td>

		</tr>

		<tr>
			<td><label>Payment Total Amount</label></td>
			<td style="font-size: 85%"><c:out
					value="${result['PAYMENTINFO_0_AMT']}" /></td>
		</tr>

		<tr>
			<td><label>Currency</label></td>
			<td style="font-size: 85%"><c:out
					value="${result['PAYMENTINFO_0_CURRENCYCODE']}" /></td>
		</tr>

		<tr>
			<td><label>Payment Status</label></td>
			<td style="font-size: 85%"><c:out
					value="${result['PAYMENTINFO_0_PAYMENTSTATUS']}" /></td>
		</tr>

		<tr>
			<td><label>Payment Type</label></td>
			<td style="font-size: 85%"><c:out
					value="${result['PAYMENTINFO_0_PAYMENTTYPE']}" /></td>
		</tr>

	</table>

	<p class="pclass">
		Click <a href="<c:url value='/index.jsp' />" class="redirectLink">
			here </a> to return to Home Page
	</p>

</div>

<jsp:include page="/includes/footer.jsp" />