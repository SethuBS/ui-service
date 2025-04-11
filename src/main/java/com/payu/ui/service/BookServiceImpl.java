package com.payu.ui.service;

import com.payu.ui.exception.BookServiceException;
import com.payu.ui.request.BookRequestDTO;
import com.payu.ui.response.BookResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Value("${service.url}")
    private String serviceUrl;

    private WebTarget webTarget;


    @PostConstruct
    private void initialize() {
        Client client = ClientBuilder.newClient();
        this.webTarget = client.target(serviceUrl);
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        Response response = webTarget
                .path("/book")
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<List<BookResponseDTO>>() {
            });
        } else {
            throw new BookServiceException(response.getStatus(), "Failed to fetch books");
        }
    }

    @Override
    public BookResponseDTO createBook(BookRequestDTO bookRequestDTO) {
        Response response = webTarget
                .path("/book")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(bookRequestDTO, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 201) {
            return response.readEntity(BookResponseDTO.class);
        } else {
            throw new BookServiceException(response.getStatus(), "Failed to create book");
        }
    }

    @Override
    public BookResponseDTO updateBook(Integer bookId, BookRequestDTO bookRequestDTO) {
        Response response = webTarget
                .path("/book/" + bookId)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(bookRequestDTO, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            return response.readEntity(BookResponseDTO.class);
        } else {
            throw new BookServiceException(response.getStatus(), "Failed to update book");
        }
    }

    @Override
    public BookResponseDTO getBookById(Integer bookId) {
        Response response = webTarget
                .path("/book/" + bookId)
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == 200) {
            return response.readEntity(BookResponseDTO.class);
        } else {
            throw new BookServiceException(response.getStatus(), "Failed to fetch book");
        }
    }

    @Override
    public BookResponseDTO deleteBook(Integer bookId) {
        Response response = webTarget
                .path("/book/" + bookId)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        if (response.getStatus() == 200) {
            return response.readEntity(BookResponseDTO.class);
        } else {
            throw new BookServiceException(response.getStatus(), "Failed to delete book");
        }
    }
}
