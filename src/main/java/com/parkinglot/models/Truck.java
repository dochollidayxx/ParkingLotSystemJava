package com.parkinglot.models;

import com.parkinglot.enums.SpaceSize;
import com.parkinglot.enums.VehicleType;
import com.parkinglot.exceptions.NotImplementedException;
import java.util.List;

public class Truck extends Vehicle {
    // TODO: Implement Truck class
    // Trucks can only park in Large spaces
    public Truck(String licensePlate) {
        super(licensePlate, VehicleType.TRUCK);
    }

    @Override
    public List<SpaceSize> getCompatibleSpaceSizes() {
        return List.of(SpaceSize.LARGE);
    }
}