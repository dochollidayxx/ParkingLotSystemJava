package com.parkinglot.exceptions;

/**
 * Exception thrown to indicate that a method or feature is not yet implemented.
 * This is used in interview coding challenges to mark methods that candidates need to implement.
 */
public class NotImplementedException extends RuntimeException {
    public NotImplementedException() {
        super("This method is not yet implemented. Please implement it to complete the challenge.");
    }

    public NotImplementedException(String message) {
        super(message);
    }
}