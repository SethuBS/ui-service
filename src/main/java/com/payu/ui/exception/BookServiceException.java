package com.payu.ui.exception;

public class BookServiceException extends RuntimeException {

    private int statusCode;
    private String message;

    public BookServiceException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
