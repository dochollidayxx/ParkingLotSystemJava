package com.parkinglot.models;

import com.parkinglot.enums.SpaceSize;
import com.parkinglot.enums.VehicleType;
import java.util.Arrays;
import java.util.List;

public class Truck extends Vehicle {
    public Truck(String licensePlate) {
        super(licensePlate, VehicleType.TRUCK);
    }

    @Override
    public List<SpaceSize> getCompatibleSpaceSizes() {
        return Arrays.asList(SpaceSize.LARGE);
    }
}