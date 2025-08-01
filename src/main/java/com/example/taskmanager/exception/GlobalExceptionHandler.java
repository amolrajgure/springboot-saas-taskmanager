package com.example.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler  {
    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<String> handleDuplicateEntryException(DuplicateEntryException ex) {
        // You can customize the response body and status code here.
        // For a duplicate entry, HttpStatus.CONFLICT (409) is a good choice.
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

}