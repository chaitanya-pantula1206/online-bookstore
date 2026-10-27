package com.bookstore.repository;

import com.bookstore.model.Book;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

/** In-memory book catalog (no database needed for the demo). */
public final class BookRepository {

    private static final BookRepository INSTANCE = new BookRepository();

    private final Map<String, Book> books = new LinkedHashMap<>();

    private BookRepository() {
        add("B001", "Clean Code", "Robert C. Martin", "Programming", "549.00",
                "A handbook of agile software craftsmanship.");
        add("B002", "Effective Java", "Joshua Bloch", "Programming", "699.00",
                "Best practices for the Java platform.");
        add("B003", "Head First Java", "Kathy Sierra & Bert Bates", "Programming", "625.00",
                "A brain-friendly guide to learning Java.");
        add("B004", "The Pragmatic Programmer", "Andrew Hunt & David Thomas", "Programming", "599.00",
                "Your journey to mastery.");
        add("B005", "Maven: The Definitive Guide", "Sonatype", "DevOps", "450.00",
                "Everything about building Java projects with Apache Maven.");
        add("B006", "Continuous Delivery", "Jez Humble & David Farley", "DevOps", "799.00",
                "Reliable software releases through build, test and deployment automation.");
        add("B007", "Database System Concepts", "Silberschatz, Korth & Sudarshan", "Databases", "875.00",
                "The classic textbook on database systems.");
        add("B008", "Computer Networks", "Andrew S. Tanenbaum", "Networking", "720.00",
                "A top-down tour of how computer networks work.");
        add("B009", "Operating System Concepts", "Silberschatz, Galvin & Gagne", "Operating Systems", "810.00",
                "The 'dinosaur book' on operating systems.");
        add("B010", "Introduction to Algorithms", "Cormen, Leiserson, Rivest & Stein", "Algorithms", "995.00",
                "Comprehensive coverage of modern algorithms.");
    }

    public static BookRepository getInstance() {
        return INSTANCE;
    }

    private void add(String id, String title, String author, String category, String price, String description) {
        books.put(id, new Book(id, title, author, category, new BigDecimal(price), description));
    }

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public Optional<Book> findById(String id) {
        return Optional.ofNullable(id == null ? null : books.get(id));
    }

    /** Filters by a free-text query (title/author) and/or a category; blank values are ignored. */
    public List<Book> search(String query, String category) {
        String q = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        String c = category == null ? "" : category.trim();
        return books.values().stream()
                .filter(b -> q.isEmpty()
                        || b.getTitle().toLowerCase(Locale.ROOT).contains(q)
                        || b.getAuthor().toLowerCase(Locale.ROOT).contains(q))
                .filter(b -> c.isEmpty() || b.getCategory().equals(c))
                .collect(Collectors.toList());
    }

    public List<String> findCategories() {
        return new ArrayList<>(books.values().stream()
                .map(Book::getCategory)
                .collect(Collectors.toCollection(TreeSet::new)));
    }
}
