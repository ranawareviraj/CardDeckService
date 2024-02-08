package com.acme.carddeckservice.error;

/**
 * The InvalidInputException class represents an exception that is thrown when an invalid input is provided.
 */
public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String message) {
        super(message);
    }
}
