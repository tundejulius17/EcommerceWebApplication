<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/custLeftColumn.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="indexRightColumn">

	<h1 class="hclass">Shipping Address</h1>

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
					value="${result['PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE']}" /></td>
		</tr>

		<tr>
			<td style="font-size: 85%"><c:out
					value="${result['SHIPTOCOUNTRYNAME']}" /></td>
		</tr>

		<tr></tr>

		<tr>
			<td><label>Total Amount</label></td>


			<td style="font-size: 85%"><c:out
					value="${result['PAYMENTREQUEST_0_AMT']}" /></td>

		</tr>

		<tr>
			<td><label>Currency</label></td>


			<td style="font-size: 85%"><c:out
					value="${result['CURRENCYCODE']}" /></td>

		</tr>

		<tr>
			<td><label>Payment Status</label></td>


			<td style="font-size: 85%"><c:out
					value="${result['PAYERSTATUS']}" /></td>

		</tr>


		<tr>

			<td><form action="<c:url value='/Return?page=return'/>"
					method="post">
					<input type="Submit" name="confirm" id="submit"
						style="margin-left: 0" alt="Check out with PayPal"
						value="Confirm order">
				</form></td>
		</tr>



	</table>

</div>

<jsp:include page="/includes/footer.jsp" />