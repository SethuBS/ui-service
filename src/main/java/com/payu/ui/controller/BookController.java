package com.payu.ui.controller;


import com.payu.ui.model.Book;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;


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
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/ui-service/api/v1/book")
public class BookController {



    private static String REST_URI = "http://localhost:9000/management-service/api/v1/book/";
    private Client client = ClientBuilder.newClient();
    private WebTarget webTarget = client.target(REST_URI);

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @GetMapping
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooks() throws ParseException {
        List<Book> books = new ArrayList<>();
        try {
           List<Book> bookList = webTarget.request(MediaType.APPLICATION_JSON).get(new GenericType<List<Book>>() {});
           for(Book book: bookList){
               books.add(book);
           }
           return books;
       }catch (Exception e){
           System.out.println(e.getMessage());
       } finally {
           return books;
       }
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
        Response response = webTarget.request().post(Entity.entity(book, MediaType.APPLICATION_JSON), Response.class);
        return response;
    }

    @PutMapping({"/{bookId}"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathVariable("bookId") Integer bookId, @RequestBody  Book book) {
        if(book.getPrice() == null){book.setPrice(0d);}
        if(book.getISBNNumber()==null){book.setISBNNumber(0L);}
        if(book.getPublishDate()==null){book.setPublishDate(new Date());}
        String date = dateFormat.format(book.getPublishDate());
        book.setPublishDate(new Date(date));
        Response response = webTarget.path(String.valueOf(bookId.toString())).request().put(Entity.entity(book, MediaType.APPLICATION_JSON), Response.class);
        return response;
    }

    @DeleteMapping({"/{bookId}"})
    public Response deleteBook(@PathVariable("bookId") Integer bookId){
        Response response = webTarget.path(bookId.toString()).request().delete();
        return response;
    }

}
