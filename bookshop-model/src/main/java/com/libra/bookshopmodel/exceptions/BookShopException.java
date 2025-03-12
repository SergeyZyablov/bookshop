package com.libra.bookshopmodel.exceptions;

public class BookShopException extends RuntimeException {

    public BookShopException(String message) {
        super(message);
    }

    public BookShopException(String message, Throwable cause) {
        super(message, cause);
    }
}
