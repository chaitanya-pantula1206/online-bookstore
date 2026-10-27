package com.bookstore.servlet;

import com.bookstore.model.Book;
import com.bookstore.model.Cart;
import com.bookstore.repository.BookRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

/** Shows the cart (GET) and handles add / update / remove actions (POST). */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private final BookRepository books = BookRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("cart", SessionUtil.getCart(request));
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cart cart = SessionUtil.getCart(request);
        String action = request.getParameter("action");
        String bookId = request.getParameter("bookId");
        int quantity = SessionUtil.parseInt(request.getParameter("quantity"), 1);

        if ("add".equals(action)) {
            Optional<Book> book = books.findById(bookId);
            if (book.isPresent()) {
                cart.add(book.get(), quantity);
                request.getSession().setAttribute("flash", "Added \"" + book.get().getTitle() + "\" to your cart.");
            }
            // Post/Redirect/Get: send the user back to the catalog page they came from
            String returnTo = request.getParameter("returnTo");
            response.sendRedirect(request.getContextPath() + ("catalog".equals(returnTo) ? "/books" : "/cart"));
            return;
        }
        if ("update".equals(action)) {
            cart.update(bookId, quantity);
        } else if ("remove".equals(action)) {
            cart.remove(bookId);
        } else if ("clear".equals(action)) {
            cart.clear();
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}
