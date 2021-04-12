package org.example.exceptions;

/**
 * Represents custom exception which throws on operating not existing item (product)
 */
public class NoSuchItemException extends Exception {

    public NoSuchItemException(String message) {
        super(message);
    }
}
