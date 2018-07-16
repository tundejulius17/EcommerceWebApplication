<%@page import="dbAdapter.DBCategory"%>
<%@page import="dataModel.Category"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
    
 
<%	
	List<Category> categories = DBCategory.getCustCategories();
	session.setAttribute("custCategories", categories);

%>

<div id="cusLeftColumn">

	<ul id="verticalNav">
		<li id="navHead">CATEGORIES</li>

		<c:forEach var="category" items="${custCategories}">

			<li><a
				href="<c:url value='/category/getProductsByCategory
				?id=${category.id}'/>"
				class="verticalAnchor"> ${category.name }</a></li>

		</c:forEach>

	</ul>
</div>