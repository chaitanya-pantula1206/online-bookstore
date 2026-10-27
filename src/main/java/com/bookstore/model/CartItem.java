package com.bookstore.model;

import java.math.BigDecimal;

/** One line in the shopping cart: a book and how many copies. */
public class CartItem {

    private final Book book;
    private int quantity;

    public CartItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return book.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
