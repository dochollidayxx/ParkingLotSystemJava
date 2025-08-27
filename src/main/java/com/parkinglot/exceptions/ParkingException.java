package com.parkinglot.exceptions;

public class ParkingException extends Exception {
    public ParkingException(String message) {
        super(message);
    }

    public ParkingException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class NoAvailableSpaceException extends ParkingException {
    public NoAvailableSpaceException() {
        super("No available parking spaces for this vehicle type");
    }

    public NoAvailableSpaceException(String vehicleType) {
        super("No available parking spaces for " + vehicleType);
    }
}

public class InvalidTicketException extends ParkingException {
    public InvalidTicketException(String message) {
        super(message);
    }
}

public class SpaceOccupiedException extends ParkingException {
    public SpaceOccupiedException(String message) {
        super(message);
    }
}