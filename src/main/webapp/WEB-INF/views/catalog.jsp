<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Catalog"/>
<%@ include file="common/header.jspf" %>

<h1>Book Catalog</h1>

<form class="search" method="get" action="${pageContext.request.contextPath}/books">
    <input type="text" name="q" placeholder="Search by title or author" value="<c:out value='${q}'/>">
    <select name="category">
        <option value="">All categories</option>
        <c:forEach var="cat" items="${categories}">
            <option value="<c:out value='${cat}'/>" ${cat == selectedCategory ? 'selected' : ''}><c:out value="${cat}"/></option>
        </c:forEach>
    </select>
    <button type="submit">Search</button>
    <c:if test="${not empty q or not empty selectedCategory}">
        <a class="link" href="${pageContext.request.contextPath}/books">Clear</a>
    </c:if>
</form>

<c:choose>
    <c:when test="${empty books}">
        <p class="empty">No books match your search.</p>
    </c:when>
    <c:otherwise>
        <div class="grid">
            <c:forEach var="book" items="${books}">
                <article class="card">
                    <span class="tag"><c:out value="${book.category}"/></span>
                    <h2><c:out value="${book.title}"/></h2>
                    <p class="author">by <c:out value="${book.author}"/></p>
                    <p class="desc"><c:out value="${book.description}"/></p>
                    <div class="card-footer">
                        <span class="price">&#8377;<fmt:formatNumber value="${book.price}" minFractionDigits="2" maxFractionDigits="2"/></span>
                        <form method="post" action="${pageContext.request.contextPath}/cart">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="returnTo" value="catalog">
                            <input type="hidden" name="bookId" value="${book.id}">
                            <input type="number" name="quantity" value="1" min="1" max="99" aria-label="Quantity">
                            <button type="submit">Add to cart</button>
                        </form>
                    </div>
                </article>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="common/footer.jspf" %>
