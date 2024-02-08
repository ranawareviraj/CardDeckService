package com.acme.carddeckservice.error;

public class UnknownServerException extends RuntimeException{
    public UnknownServerException(String message) {
        super(message);
    }
}
