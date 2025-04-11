package com.payu.ui.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class BookRequestDTO {
    private Integer id;

    private String name;

    @JsonProperty("ISBNNumber")
    private Long ISBNNumber;

    private LocalDate publishDate;

    private Double price;

    private String bookType;

    public BookRequestDTO() {

    }

    public BookRequestDTO(String name, Long ISBNNumber, LocalDate publishDate, Double price, String bookType) {
        this.name = name;
        this.ISBNNumber = ISBNNumber;
        this.publishDate = publishDate;
        this.price = price;
        this.bookType = bookType;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getISBNNumber() {
        return ISBNNumber;
    }

    public void setISBNNumber(Long ISBNNumber) {
        this.ISBNNumber = ISBNNumber;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", ISBNNumber=" + ISBNNumber +
                ", publishDate=" + publishDate +
                ", price=" + price +
                ", bookType=" + bookType +
                '}';
    }
}
