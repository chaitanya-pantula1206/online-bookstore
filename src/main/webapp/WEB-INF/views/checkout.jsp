<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Checkout"/>
<%@ include file="common/header.jspf" %>

<h1>Checkout</h1>

<c:if test="${not empty errors}">
    <div class="alert error">
        <ul><c:forEach var="e" items="${errors}"><li><c:out value="${e}"/></li></c:forEach></ul>
    </div>
</c:if>

<div class="two-col">
    <form class="form" method="post" action="${pageContext.request.contextPath}/checkout">
        <label>Full name
            <input type="text" name="name" required value="<c:out value='${param.name}'/>">
        </label>
        <label>Email
            <input type="email" name="email" required value="<c:out value='${param.email}'/>">
        </label>
        <label>Delivery address
            <textarea name="address" rows="4" required><c:out value="${param.address}"/></textarea>
        </label>
        <button type="submit">Place order</button>
    </form>

    <aside class="summary">
        <h2>Order summary</h2>
        <ul>
            <c:forEach var="item" items="${cart.items}">
                <li><span><c:out value="${item.book.title}"/> &times; ${item.quantity}</span>
                    <span>&#8377;<fmt:formatNumber value="${item.subtotal}" minFractionDigits="2" maxFractionDigits="2"/></span></li>
            </c:forEach>
        </ul>
        <p class="total"><span>Total</span>
            <strong>&#8377;<fmt:formatNumber value="${cart.total}" minFractionDigits="2" maxFractionDigits="2"/></strong></p>
    </aside>
</div>

<%@ include file="common/footer.jspf" %>
