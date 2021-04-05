package org.example.exceptions;

/**
 * Represents custom exception which throws on operating not existing catalog category
 */
public class NoSuchCategoryException extends Exception {

    public NoSuchCategoryException(String message) {
        super(message);
    }
}
