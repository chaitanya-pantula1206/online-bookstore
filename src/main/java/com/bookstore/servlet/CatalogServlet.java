package com.bookstore.servlet;

import com.bookstore.repository.BookRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Book catalog page with search and category filter. */
@WebServlet("/books")
public class CatalogServlet extends HttpServlet {

    private final BookRepository books = BookRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("q");
        String category = request.getParameter("category");

        request.setAttribute("books", books.search(query, category));
        request.setAttribute("categories", books.findCategories());
        request.setAttribute("q", query);
        request.setAttribute("selectedCategory", category);
        request.setAttribute("cart", SessionUtil.getCart(request));
        request.getRequestDispatcher("/WEB-INF/views/catalog.jsp").forward(request, response);
    }
}
