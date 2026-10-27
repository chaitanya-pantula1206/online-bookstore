package com.bookstore.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CartTest {

    private final Book java = new Book("B1", "Java", "A", "Programming", new BigDecimal("500.00"), "");
    private final Book maven = new Book("B2", "Maven", "B", "DevOps", new BigDecimal("250.50"), "");
    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
    }

    @Test
    void newCartIsEmpty() {
        assertTrue(cart.isEmpty());
        assertEquals(0, cart.getItemCount());
        assertEquals(BigDecimal.ZERO, cart.getTotal());
    }

    @Test
    void addingSameBookTwiceIncreasesQuantity() {
        cart.add(java, 1);
        cart.add(java, 2);
        assertEquals(1, cart.getItems().size());
        assertEquals(3, cart.getItemCount());
    }

    @Test
    void totalIsSumOfSubtotals() {
        cart.add(java, 2);
        cart.add(maven, 1);
        assertEquals(new BigDecimal("1250.50"), cart.getTotal());
    }

    @Test
    void updateToZeroRemovesItem() {
        cart.add(java, 2);
        cart.update("B1", 0);
        assertTrue(cart.isEmpty());
    }

    @Test
    void quantityIsCappedAtMaximum() {
        cart.add(java, 500);
        assertEquals(Cart.MAX_QUANTITY, cart.getItemCount());
    }

    @Test
    void nonPositiveAddIsIgnored() {
        cart.add(java, 0);
        cart.add(java, -3);
        assertTrue(cart.isEmpty());
    }

    @Test
    void removeAndClear() {
        cart.add(java, 1);
        cart.add(maven, 1);
        cart.remove("B1");
        assertEquals(1, cart.getItems().size());
        cart.clear();
        assertTrue(cart.isEmpty());
    }
}
