package com.payu.ui.controller;


import com.payu.ui.model.Book;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/ui-service/api/v1/book")
public class BookController {

    @Value("${service.url}")
    private String serviceUrl;
    private WebTarget webTarget;

    @PostConstruct
    private void initialize() {
        Client client = ClientBuilder.newClient();
        this.webTarget = client.target(serviceUrl);
    }

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @GetMapping
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooks() throws ParseException {
        List<Book> books = new ArrayList<>();
        try {
           List<Book> bookList = webTarget.request(MediaType.APPLICATION_JSON).get(new GenericType<List<Book>>() {});
            books.addAll(bookList);
           return books;
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
        return books;
    }

    @PostMapping
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(@RequestBody Book book) {
        if(book.getPrice() == null){book.setPrice(0d);}
        if(book.getISBNNumber()==null){book.setISBNNumber(0L);}
        if(book.getPublishDate()==null){book.setPublishDate(new Date());}
        String date = dateFormat.format(book.getPublishDate());
        book.setPublishDate(new Date(date));
        return webTarget.request().post(Entity.entity(book, MediaType.APPLICATION_JSON), Response.class);
    }

    @PutMapping({"/{bookId}"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathVariable("bookId") Integer bookId, @RequestBody  Book book) {
        if(book.getPrice() == null){book.setPrice(0d);}
        if(book.getISBNNumber()==null){book.setISBNNumber(0L);}
        if(book.getPublishDate()==null){book.setPublishDate(new Date());}
        String date = dateFormat.format(book.getPublishDate());
        book.setPublishDate(new Date(date));
        return webTarget.path(String.valueOf(bookId.toString())).request().put(Entity.entity(book, MediaType.APPLICATION_JSON), Response.class);
    }

    @DeleteMapping({"/{bookId}"})
    public Response deleteBook(@PathVariable("bookId") Integer bookId){
        Response response;
        response = webTarget.path(bookId.toString()).request().delete();
        return response;
    }

}
