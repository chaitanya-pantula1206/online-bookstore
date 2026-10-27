package com.bookstore.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** A placed order, created from the cart at checkout. */
public class Order {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

    private final long id;
    private final String customerName;
    private final String email;
    private final String address;
    private final List<CartItem> items;
    private final BigDecimal total;
    private final LocalDateTime placedAt;

    public Order(long id, String customerName, String email, String address, List<CartItem> items) {
        this.id = id;
        this.customerName = customerName;
        this.email = email;
        this.address = address;
        this.items = List.copyOf(items);
        this.total = items.stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.placedAt = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getPlacedAt() {
        return placedAt.format(DATE_FORMAT);
    }
}
