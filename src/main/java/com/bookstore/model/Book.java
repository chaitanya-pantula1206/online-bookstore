package com.bookstore.model;

import java.math.BigDecimal;

/** A book available in the store catalog. */
public class Book {

    private final String id;
    private final String title;
    private final String author;
    private final String category;
    private final BigDecimal price;
    private final String description;

    public Book(String id, String title, String author, String category, BigDecimal price, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
