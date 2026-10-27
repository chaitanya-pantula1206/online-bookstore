<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isErrorPage="true" %>
<c:set var="pageTitle" value="Error"/>
<%@ include file="common/header.jspf" %>

<h1>Something went wrong</h1>
<p class="empty">
    <c:choose>
        <c:when test="${pageContext.errorData.statusCode == 404}">The page you were looking for was not found.</c:when>
        <c:otherwise>An unexpected error occurred. Please try again.</c:otherwise>
    </c:choose>
</p>
<p><a class="button" href="${pageContext.request.contextPath}/books">Back to the catalog</a></p>

<%@ include file="common/footer.jspf" %>
