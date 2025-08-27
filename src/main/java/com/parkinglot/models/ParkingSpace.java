package com.parkinglot.models;

import com.parkinglot.enums.SpaceSize;
import com.parkinglot.exceptions.NotImplementedException;

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

    public void parkVehicle(Vehicle vehicle) {
        // TODO: Implement parking logic
        // Should set isOccupied to true and store the vehicle
        // Should throw exception if space is already occupied
        throw new NotImplementedException();
    }

    public Vehicle removeVehicle() {
        // TODO: Implement unparking logic
        // Should return the vehicle and mark space as available
        // Should throw exception if space is not occupied
        throw new NotImplementedException();
    }

    public boolean canFitVehicle(Vehicle vehicle) {
        // TODO: Check if this space can accommodate the given vehicle
        throw new NotImplementedException();
    }
}