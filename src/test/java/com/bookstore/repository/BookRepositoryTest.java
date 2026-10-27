package com.bookstore.repository;

import com.bookstore.model.Book;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookRepositoryTest {

    private final BookRepository repository = BookRepository.getInstance();

    @Test
    void catalogIsNotEmpty() {
        assertFalse(repository.findAll().isEmpty());
    }

    @Test
    void searchIsCaseInsensitiveOnTitleAndAuthor() {
        List<Book> byTitle = repository.search("clean code", null);
        assertEquals(1, byTitle.size());
        assertEquals("B001", byTitle.get(0).getId());

        assertFalse(repository.search("BLOCH", "").isEmpty());
    }

    @Test
    void filterByCategory() {
        List<Book> devops = repository.search("", "DevOps");
        assertFalse(devops.isEmpty());
        assertTrue(devops.stream().allMatch(b -> b.getCategory().equals("DevOps")));
    }

    @Test
    void unknownIdIsEmpty() {
        assertTrue(repository.findById("nope").isEmpty());
        assertTrue(repository.findById(null).isEmpty());
    }
}
