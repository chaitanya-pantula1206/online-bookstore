package com.bookstore.repository;

import com.bookstore.model.CartItem;
import com.bookstore.model.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/** In-memory order store; orders are lost when Tomcat restarts. */
public final class OrderRepository {

    private static final OrderRepository INSTANCE = new OrderRepository();

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1001);

    private OrderRepository() {
    }

    public static OrderRepository getInstance() {
        return INSTANCE;
    }

    public Order place(String customerName, String email, String address, List<CartItem> items) {
        Order order = new Order(nextId.getAndIncrement(), customerName, email, address, items);
        orders.put(order.getId(), order);
        return order;
    }

    public Optional<Order> findById(long id) {
        return Optional.ofNullable(orders.get(id));
    }

    /** Returns the given orders, newest first; ids that no longer exist are skipped. */
    public List<Order> findByIds(List<Long> ids) {
        List<Order> result = new ArrayList<>();
        for (Long id : ids) {
            Order order = orders.get(id);
            if (order != null) {
                result.add(order);
            }
        }
        Collections.reverse(result);
        return result;
    }
}
