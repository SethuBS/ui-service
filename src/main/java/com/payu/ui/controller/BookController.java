package com.payu.ui.controller;

import com.payu.ui.request.BookRequestDTO;
import com.payu.ui.response.BookResponseDTO;
import com.payu.ui.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ui-service/api/v1/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookResponseDTO> getBooks() {
        try {
            return bookService.getAllBooks();
        } catch (Exception e) {
            System.out.println("Error fetching books: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @PostMapping
    public BookResponseDTO createBook(@RequestBody BookRequestDTO bookRequestDTO) {
        if (bookRequestDTO.getPrice() == null) {
            bookRequestDTO.setPrice(0d);
        }
        if (bookRequestDTO.getISBNNumber() == null) {
            bookRequestDTO.setISBNNumber(0L);
        }
        if (bookRequestDTO.getPublishDate() == null) {
            bookRequestDTO.setPublishDate(LocalDate.now());
        }

        return bookService.createBook(bookRequestDTO);
    }

    @PutMapping("/{bookId}")
    public BookResponseDTO updateBook(@PathVariable("bookId") Integer bookId, @RequestBody BookRequestDTO bookRequestDTO) {
        // Validate and set defaults
        if (bookRequestDTO.getPrice() == null) {
            bookRequestDTO.setPrice(0d);
        }
        if (bookRequestDTO.getISBNNumber() == null) {
            bookRequestDTO.setISBNNumber(0L);
        }
        if (bookRequestDTO.getPublishDate() == null) {
            bookRequestDTO.setPublishDate(LocalDate.now());
        }

        return bookService.updateBook(bookId, bookRequestDTO);
    }

    @GetMapping("/{bookId}")
    public BookResponseDTO getBookById(@PathVariable("bookId") Integer bookId) {
        try {
            return bookService.getBookById(bookId);
        } catch (Exception e) {
            System.out.println("Error fetching book by ID: " + e.getMessage());
            return null;
        }
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable("bookId") Integer bookId) {
        bookService.deleteBook(bookId);
    }
}
