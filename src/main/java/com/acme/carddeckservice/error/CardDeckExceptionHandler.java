package com.acme.carddeckservice.error;

import com.acme.carddeckservice.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The CardDeckExceptionHandler class is a global exception handler for the CardDeckService application.
 * It handles exceptions thrown by the CardDeckController.
 *
 * @author Viraj Ranaware
 */
@ControllerAdvice
public class CardDeckExceptionHandler {

    /**
     * Handle NotFoundException
     * @param e NotFoundException
     * @return ErrorResponse
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.NOT_FOUND.toString());
        errorResponse.setType(Constants.RESOURCE_NOT_FOUND);
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles InvalidInputException and returns an ErrorResponse containing the error details.
     * @param e InvalidInputException
     * @return ErrorResponse
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setType(Constants.INVALID_REQUEST);
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
