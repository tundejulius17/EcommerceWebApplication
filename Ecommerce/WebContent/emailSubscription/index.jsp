<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/custLeftColumn.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="indexRightColumn">
	<p class="pclass">Note! All fields marked with an asterisk (*) are
		mandatory.</p>
	<br>
	<h1 class="hclass">Subscribe to our email list</h1>
	<p class="pclass">If you do, we'll send you news about our new
		products and special offers.</p>
	<p class="pclass">
		<i style="font-size: 90%; color: red">${message}</i>
	</p>
	<p class="pclass">
		<i style="font-size: 90%; color: red">${emailMessage}</i>
	</p>
	<form action="<c:url value='/email/subscribeToEmail'/>" method="post">
		<table class="formTable">

			<tr>
				<td><label>First name*</label></td>


				<td><input type="text" name="firstName"
					value='<c:out value="${emailSubscriber.firstName}"/>' required><br></td>

			</tr>

			<tr>
				<td><label>Last name *</label></td>
				<td><input type="text" name="lastName"
					value='<c:out value="${emailSubscriber.lastName}"/>' required><br></td>
			</tr>

			<tr>
				<td><label>Email *</label></td>

				<td><input type="email" name="email"
					value='<c:out value="${emailSubscriber.email}"/>' required></td>
				<td style="font-size: 80%; color: red"><i>${emailError}</i><br></td>

			</tr>
			<tr>
				<td></td>

				<td><input type="submit" value="Subscribe Now" id="submit">
				</td>
			</tr>


		</table>

	</form>
	
	<p class="pclass">
		Click <a href="<c:url value='/index.jsp' />" class="redirectLink">
			here </a> to return to Home Page
	</p>


</div>
<jsp:include page="/includes/footer.jsp" />