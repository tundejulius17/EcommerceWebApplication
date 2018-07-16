<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/custLeftColumn.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="indexRightColumn">

	<p class="pclass">You cancelled the order.</p>

	<p class="pclass">
		Click <a href="<c:url value='/index.jsp' />" class="redirectLink">
			here </a> to return to Home Page
	</p>

</div>


<jsp:include page="/includes/footer.jsp" />