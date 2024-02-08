package com.acme.carddeckservice.error;

/**
 * The InvalidInputException class represents an exception that is thrown when an invalid input is provided.
 *
 * @author Viraj Ranaware
 */
public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String message) {
        super(message);
    }
}
