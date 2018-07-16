<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/custLeftColumn.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="indexRightColumn">

	<h1 class="hclass">Thanks for subscribing to our email list</h1>
	<p class="pclass">An email confirmation has been sent to the
		provided email address.</p>

	<table class="formTable">

		<tr>
			<td><label>First name</label></td>


			<td style="font-size: 85%"><c:out
					value="${emailSubscriber.firstName}" /><br></td>

		</tr>

		<tr>
			<td><label>Last name</label></td>
			<td style="font-size: 85%"><c:out
					value="${emailSubscriber.lastName}" /><br></td>
		</tr>

		<tr>
			<td><label>Email</label></td>

			<td style="font-size: 85%"><c:out
					value="${emailSubscriber.email}" /><br></td>

		</tr>

	</table>
	
	<p class="pclass">
		Click <a href="<c:url value='/index.jsp' />" class="redirectLink">
			here </a> to return to Home Page
	</p>

</div>
<jsp:include page="/includes/footer.jsp" />