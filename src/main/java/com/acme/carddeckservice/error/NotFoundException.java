package com.acme.carddeckservice.error;

/**
 * The NotFoundException class represents an exception that is thrown when a requested resource (Card or Deck) is not found.
 *
 * @author Viraj Ranaware
 */
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
