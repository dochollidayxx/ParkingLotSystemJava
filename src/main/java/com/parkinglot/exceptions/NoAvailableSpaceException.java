package com.parkinglot.exceptions;

public class NoAvailableSpaceException extends ParkingException {
    public NoAvailableSpaceException() {
        super("No available parking spaces for this vehicle type");
    }

    public NoAvailableSpaceException(String vehicleType) {
        super("No available parking spaces for " + vehicleType);
    }
}