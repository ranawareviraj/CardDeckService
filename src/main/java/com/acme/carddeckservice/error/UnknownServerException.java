package com.acme.carddeckservice.error;

/**
 * The UnknownServerException class represents an exception that is thrown when an unknown server error occurs.
 *
 * @author Viraj Ranaware
 */
public class UnknownServerException extends RuntimeException{
    public UnknownServerException(String message) {
        super(message);
    }
}
