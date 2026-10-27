<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Cart"/>
<%@ include file="common/header.jspf" %>

<h1>Your Cart</h1>

<c:choose>
    <c:when test="${empty cart.items}">
        <p class="empty">Your cart is empty. <a href="${pageContext.request.contextPath}/books">Browse the catalog</a>.</p>
    </c:when>
    <c:otherwise>
        <table class="table">
            <thead>
            <tr><th>Book</th><th>Price</th><th>Quantity</th><th>Subtotal</th><th></th></tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${cart.items}">
                <tr>
                    <td><strong><c:out value="${item.book.title}"/></strong><br><small><c:out value="${item.book.author}"/></small></td>
                    <td>&#8377;<fmt:formatNumber value="${item.book.price}" minFractionDigits="2" maxFractionDigits="2"/></td>
                    <td>
                        <form class="inline" method="post" action="${pageContext.request.contextPath}/cart">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="bookId" value="${item.book.id}">
                            <input type="number" name="quantity" value="${item.quantity}" min="0" max="99" aria-label="Quantity">
                            <button type="submit" class="secondary">Update</button>
                        </form>
                    </td>
                    <td>&#8377;<fmt:formatNumber value="${item.subtotal}" minFractionDigits="2" maxFractionDigits="2"/></td>
                    <td>
                        <form class="inline" method="post" action="${pageContext.request.contextPath}/cart">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="bookId" value="${item.book.id}">
                            <button type="submit" class="danger">Remove</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr><td colspan="3">Total (<c:out value="${cart.itemCount}"/> items)</td>
                <td colspan="2"><strong>&#8377;<fmt:formatNumber value="${cart.total}" minFractionDigits="2" maxFractionDigits="2"/></strong></td></tr>
            </tfoot>
        </table>

        <div class="actions">
            <form method="post" action="${pageContext.request.contextPath}/cart">
                <input type="hidden" name="action" value="clear">
                <button type="submit" class="secondary">Clear cart</button>
            </form>
            <a class="link" href="${pageContext.request.contextPath}/books">Continue shopping</a>
            <a class="button" href="${pageContext.request.contextPath}/checkout">Proceed to checkout</a>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="common/footer.jspf" %>
