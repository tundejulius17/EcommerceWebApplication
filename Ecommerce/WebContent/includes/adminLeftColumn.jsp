<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="cusLeftColumn">
	<ul id="verticalNav">
		<li id="navHead">INVENTORY</li>
		<li><a
			href="<c:url value='/adminController/getCategories'>
				</c:url>"
			class="verticalAnchor">Manage Categories</a></li>

		<li><a
			href="<c:url value='/adminController/getProducts'>
				</c:url>"
			class="verticalAnchor">Manage Products</a></li>
	</ul>

	<ul id="verticalNav">
		<li id="navHead">ORDERS/PAYMENTS</li>
		<li><a
			href="<c:url value='/admin/orderParameters.jsp'>
				</c:url>"
			class="verticalAnchor">Display Orders</a></li>
		<li><a
			href="<c:url value='/admin/paymentParameters.jsp'>
				</c:url>"
			class="verticalAnchor">Display Payments</a></li>
		
		<li><a href="<c:url value='/admin/downloadParameters.jsp'>
				</c:url>"
			class="verticalAnchor">Download Report</a></li>
	</ul>
	<ul id="verticalNav">
		<li id="navHead">EMAIL LIST</li>
		<li><a
			href="<c:url value='/adminController/getEmailSubscribers'>
				</c:url>"
			class="verticalAnchor">Display Email Subscribers List</a></li>
		<li><a
			href="<c:url value='/adminController/getEmailSubscribersReport'>
				</c:url>"
			class="verticalAnchor">Download Email Subscribers List</a></li>

	</ul>
</div>