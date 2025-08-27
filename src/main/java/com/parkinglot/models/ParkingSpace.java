package com.parkinglot.models;

import com.parkinglot.enums.SpaceSize;
import com.parkinglot.exceptions.NotImplementedException;
import com.parkinglot.exceptions.ParkingException;
import com.parkinglot.exceptions.SpaceOccupiedException;

public class ParkingSpace {
    private final String id;
    private final SpaceSize size;
    private boolean isOccupied;
    private Vehicle currentVehicle;

    public ParkingSpace(String id, SpaceSize size) {
        this.id = id;
        this.size = size;
        this.isOccupied = false;
        this.currentVehicle = null;
    }

    public String getId() {
        return id;
    }

    public SpaceSize getSize() {
        return size;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }

    public void parkVehicle(Vehicle vehicle) throws SpaceOccupiedException {
        // TODO: Implement parking logic
        // Should set isOccupied to true and store the vehicle
        // Should throw exception if space is already occupied
        if (isOccupied) {
            throw new SpaceOccupiedException("e");
        }

        isOccupied = true;
        currentVehicle = vehicle;
    }

    public Vehicle removeVehicle() throws ParkingException {
        // TODO: Implement unparking logic
        // Should return the vehicle and mark space as available
        // Should throw exception if space is not occupied
        if (!isOccupied) {
            throw new ParkingException("e");
        }

        isOccupied = false;
        Vehicle old = currentVehicle;
        currentVehicle = null;
        return old;
    }

    public boolean canFitVehicle(Vehicle vehicle) {
        // TODO: Check if this space can accommodate the given vehicle
        return vehicle.getCompatibleSpaceSizes().contains(size);
    }
}