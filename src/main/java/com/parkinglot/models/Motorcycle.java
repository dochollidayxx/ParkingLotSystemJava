package com.parkinglot.models;

import com.parkinglot.enums.SpaceSize;
import com.parkinglot.enums.VehicleType;
import com.parkinglot.exceptions.NotImplementedException;
import java.util.List;

public class Motorcycle extends Vehicle {
    public Motorcycle(String licensePlate) {
        super(licensePlate, VehicleType.MOTORCYCLE);
    }

    @Override
    public List<SpaceSize> getCompatibleSpaceSizes() {
        return List.of(SpaceSize.SMALL, SpaceSize.MEDIUM, SpaceSize.LARGE);
    }
}