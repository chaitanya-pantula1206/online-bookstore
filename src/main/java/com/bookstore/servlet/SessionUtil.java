package com.bookstore.servlet;

import com.bookstore.model.Cart;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

/** Helpers for the per-user state stored in the HTTP session. */
final class SessionUtil {

    private static final String CART = "cart";
    private static final String ORDER_IDS = "orderIds";

    private SessionUtil() {
    }

    static Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART, cart);
        }
        return cart;
    }

    @SuppressWarnings("unchecked")
    static List<Long> getOrderIds(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Long> ids = (List<Long>) session.getAttribute(ORDER_IDS);
        if (ids == null) {
            ids = new ArrayList<>();
            session.setAttribute(ORDER_IDS, ids);
        }
        return ids;
    }

    static int parseInt(String value, int fallback) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NullPointerException | NumberFormatException e) {
            return fallback;
        }
    }
}
