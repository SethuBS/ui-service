package com.payu.ui;

import com.payu.ui.controller.BookController;
import com.payu.ui.request.BookRequestDTO;
import com.payu.ui.response.BookResponseDTO;
import com.payu.ui.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private BookResponseDTO book1;
    private BookResponseDTO book2;

    @BeforeEach
    public void setUp() {
        book1 = new BookResponseDTO(15, "Book Title 15", 9780000225659L,
                LocalDate.of(2025, 4, 1), 254.0, "HARDCOPY");

        book2 = new BookResponseDTO(115, "Book Title 115", 9780000099333L,
                LocalDate.of(2025, 2, 7), 208.1, "SOFTCOPY");
    }

    @Test
    public void testGetBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        mockMvc.perform(get("/ui-service/api/v1/book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(15))
                .andExpect(jsonPath("$[0].name").value("Book Title 15"))
                .andExpect(jsonPath("$[0].ISBNNumber").value(9780000225659L))
                .andExpect(jsonPath("$[0].price").value(254.0))
                .andExpect(jsonPath("$[0].bookType").value("HARDCOPY"))
                .andExpect(jsonPath("$[1].id").value(115))
                .andExpect(jsonPath("$[1].name").value("Book Title 115"))
                .andExpect(jsonPath("$[1].ISBNNumber").value(9780000099333L))
                .andExpect(jsonPath("$[1].price").value(208.1))
                .andExpect(jsonPath("$[1].bookType").value("SOFTCOPY"));
    }

    @Test
    public void testCreateBook() throws Exception {
        when(bookService.createBook(Mockito.any(BookRequestDTO.class))).thenReturn(book1);

        mockMvc.perform(post("/ui-service/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Book Title 15\",\"ISBNNumber\":9780000225659,\"publishDate\":\"2025-04-01\",\"price\":254.0,\"bookType\":\"HARDCOPY\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book1.getId()))
                .andExpect(jsonPath("$.name").value(book1.getName()))
                .andExpect(jsonPath("$.ISBNNumber").value(book1.getISBNNumber()))
                .andExpect(jsonPath("$.price").value(book1.getPrice()));
    }

    @Test
    public void testUpdateBook() throws Exception {
        // Arrange: mock expected book response
        BookResponseDTO book1 = new BookResponseDTO();
        book1.setId(15);
        book1.setName("Updated Title");
        book1.setISBNNumber(9780000225659L);
        book1.setPublishDate(LocalDate.of(2025, 4, 1));
        book1.setPrice(300.0);
        book1.setBookType("HARDCOPY");

        when(bookService.updateBook(Mockito.eq(15), Mockito.any(BookRequestDTO.class))).thenReturn(book1);

        // Act & Assert: perform PUT and verify all fields
        mockMvc.perform(put("/ui-service/api/v1/book/{bookId}", 15)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Title\",\"ISBNNumber\":9780000225659,\"publishDate\":\"2025-04-01\",\"price\":300.0,\"bookType\":\"HARDCOPY\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(15))
                .andExpect(jsonPath("$.name").value("Updated Title"))
                .andExpect(jsonPath("$.ISBNNumber").value(9780000225659L))
                .andExpect(jsonPath("$.publishDate").value("2025-04-01"))
                .andExpect(jsonPath("$.price").value(300.0))
                .andExpect(jsonPath("$.bookType").value("HARDCOPY"));
    }

    @Test
    public void testGetBookById() throws Exception {
        // Mock the BookService's response
        when(bookService.getBookById(1)).thenReturn(book1);

        // Perform the GET request to the controller
        mockMvc.perform(get("/ui-service/api/v1/book/{bookId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book1.getId()))
                .andExpect(jsonPath("$.name").value(book1.getName()))
                .andExpect(jsonPath("$.ISBNNumber").value(book1.getISBNNumber()))
                .andExpect(jsonPath("$.publishDate").value(book1.getPublishDate().toString()))
                .andExpect(jsonPath("$.price").value(book1.getPrice()))
                .andExpect(jsonPath("$.bookType").value(book1.getBookType()));
    }

    @Test
    public void testDeleteBook() throws Exception {
        BookResponseDTO mockResponse = new BookResponseDTO(); // or build it with data
        Mockito.when(bookService.deleteBook(Mockito.eq(15))).thenReturn(mockResponse);

        mockMvc.perform(delete("/ui-service/api/v1/book/{bookId}", 15))
                .andExpect(status().isOk());
    }
}
