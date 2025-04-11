package com.payu.ui.service;

import com.payu.ui.request.BookRequestDTO;
import com.payu.ui.response.BookResponseDTO;

import java.util.List;

public interface BookService {

    List<BookResponseDTO> getAllBooks();

    BookResponseDTO createBook(BookRequestDTO bookRequestDTO);

    BookResponseDTO updateBook(Integer bookId, BookRequestDTO bookRequestDTO);

    BookResponseDTO getBookById(Integer bookId);

    BookResponseDTO deleteBook(Integer bookId);

}
