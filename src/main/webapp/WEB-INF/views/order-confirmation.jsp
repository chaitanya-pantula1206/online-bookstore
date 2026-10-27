<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Order #${order.id}"/>
<%@ include file="common/header.jspf" %>

<div class="alert success">Thank you, <c:out value="${order.customerName}"/>! Your order has been placed.</div>

<h1>Order #${order.id}</h1>
<p class="muted">Placed on ${order.placedAt} &middot; confirmation sent to <c:out value="${order.email}"/></p>
<p><strong>Deliver to:</strong> <c:out value="${order.address}"/></p>

<table class="table">
    <thead><tr><th>Book</th><th>Price</th><th>Qty</th><th>Subtotal</th></tr></thead>
    <tbody>
    <c:forEach var="item" items="${order.items}">
        <tr>
            <td><c:out value="${item.book.title}"/></td>
            <td>&#8377;<fmt:formatNumber value="${item.book.price}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <td>${item.quantity}</td>
            <td>&#8377;<fmt:formatNumber value="${item.subtotal}" minFractionDigits="2" maxFractionDigits="2"/></td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr><td colspan="3">Total</td>
        <td><strong>&#8377;<fmt:formatNumber value="${order.total}" minFractionDigits="2" maxFractionDigits="2"/></strong></td></tr>
    </tfoot>
</table>

<div class="actions">
    <a class="link" href="${pageContext.request.contextPath}/orders">View all my orders</a>
    <a class="button" href="${pageContext.request.contextPath}/books">Continue shopping</a>
</div>

<%@ include file="common/footer.jspf" %>
