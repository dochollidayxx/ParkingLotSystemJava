package com.parkinglot.exceptions;

public class InvalidTicketException extends ParkingException {
    public InvalidTicketException(String message) {
        super(message);
    }
}