package com.parkinglot.models;

import com.parkinglot.enums.SpaceSize;
import com.parkinglot.exceptions.ParkingException;

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

    public void parkVehicle(Vehicle vehicle) throws ParkingException {
        if (isOccupied) {
            throw new ParkingException("Space is already occupied");
        }
        if (!canFitVehicle(vehicle)) {
            throw new ParkingException("Vehicle cannot fit in this space");
        }
        
        this.currentVehicle = vehicle;
        this.isOccupied = true;
    }

    public Vehicle removeVehicle() throws ParkingException {
        if (!isOccupied) {
            throw new ParkingException("Space is not occupied");
        }
        
        Vehicle vehicle = this.currentVehicle;
        this.currentVehicle = null;
        this.isOccupied = false;
        return vehicle;
    }

    public boolean canFitVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            return false;
        }
        return vehicle.getCompatibleSpaceSizes().contains(this.size);
    }
}