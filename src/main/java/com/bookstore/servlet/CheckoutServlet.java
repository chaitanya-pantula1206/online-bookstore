package com.bookstore.servlet;

import com.bookstore.model.Cart;
import com.bookstore.model.Order;
import com.bookstore.repository.OrderRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Checkout form (GET) and order placement (POST). */
@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private final OrderRepository orders = OrderRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cart cart = SessionUtil.getCart(request);
        if (cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cart cart = SessionUtil.getCart(request);
        if (cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        String name = trim(request.getParameter("name"));
        String email = trim(request.getParameter("email"));
        String address = trim(request.getParameter("address"));

        List<String> errors = new ArrayList<>();
        if (name.isEmpty()) {
            errors.add("Please enter your name.");
        }
        if (!email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
            errors.add("Please enter a valid email address.");
        }
        if (address.isEmpty()) {
            errors.add("Please enter a delivery address.");
        }
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("cart", cart);
            request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
            return;
        }

        Order order = orders.place(name, email, address, cart.getItems());
        SessionUtil.getOrderIds(request).add(order.getId());
        cart.clear();
        response.sendRedirect(request.getContextPath() + "/orders?id=" + order.getId());
    }

    private static String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
