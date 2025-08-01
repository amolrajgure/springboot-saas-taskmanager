package com.example.taskmanager.exception;

public final class DuplicateEntryException extends RuntimeException {

    public DuplicateEntryException(String message) {
        super(message);
    }
}
