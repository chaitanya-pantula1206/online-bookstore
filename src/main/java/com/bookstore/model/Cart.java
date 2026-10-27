package com.bookstore.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Shopping cart kept in the user's HTTP session. */
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int MAX_QUANTITY = 99;

    private final Map<String, CartItem> items = new LinkedHashMap<>();

    public void add(Book book, int quantity) {
        if (quantity <= 0) {
            return;
        }
        CartItem item = items.get(book.getId());
        if (item == null) {
            items.put(book.getId(), new CartItem(book, Math.min(quantity, MAX_QUANTITY)));
        } else {
            item.setQuantity(Math.min(item.getQuantity() + quantity, MAX_QUANTITY));
        }
    }

    /** Sets the quantity of a book already in the cart; zero or less removes it. */
    public void update(String bookId, int quantity) {
        CartItem item = items.get(bookId);
        if (item == null) {
            return;
        }
        if (quantity <= 0) {
            items.remove(bookId);
        } else {
            item.setQuantity(Math.min(quantity, MAX_QUANTITY));
        }
    }

    public void remove(String bookId) {
        items.remove(bookId);
    }

    public void clear() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getItemCount() {
        return items.values().stream().mapToInt(CartItem::getQuantity).sum();
    }

    public BigDecimal getTotal() {
        return items.values().stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
