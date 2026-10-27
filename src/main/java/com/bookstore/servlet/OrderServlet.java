package com.bookstore.servlet;

import com.bookstore.repository.OrderRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/** Order confirmation (?id=...) and the list of orders placed in this session. */
@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    private final OrderRepository orders = OrderRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Long> myOrderIds = SessionUtil.getOrderIds(request);
        request.setAttribute("cart", SessionUtil.getCart(request));

        String id = request.getParameter("id");
        if (id != null) {
            long orderId = SessionUtil.parseInt(id, -1);
            // Only show orders that were placed from this session
            if (myOrderIds.contains(orderId)) {
                orders.findById(orderId).ifPresent(order -> request.setAttribute("order", order));
            }
            if (request.getAttribute("order") == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
                return;
            }
            request.getRequestDispatcher("/WEB-INF/views/order-confirmation.jsp").forward(request, response);
            return;
        }

        request.setAttribute("orders", orders.findByIds(myOrderIds));
        request.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(request, response);
    }
}
