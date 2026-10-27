<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="My Orders"/>
<%@ include file="common/header.jspf" %>

<h1>My Orders</h1>

<c:choose>
    <c:when test="${empty orders}">
        <p class="empty">You have not placed any orders yet. <a href="${pageContext.request.contextPath}/books">Start shopping</a>.</p>
    </c:when>
    <c:otherwise>
        <table class="table">
            <thead><tr><th>Order</th><th>Placed on</th><th>Items</th><th>Total</th></tr></thead>
            <tbody>
            <c:forEach var="o" items="${orders}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/orders?id=${o.id}">#${o.id}</a></td>
                    <td>${o.placedAt}</td>
                    <td>${o.items.size()}</td>
                    <td>&#8377;<fmt:formatNumber value="${o.total}" minFractionDigits="2" maxFractionDigits="2"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>

<%@ include file="common/footer.jspf" %>
