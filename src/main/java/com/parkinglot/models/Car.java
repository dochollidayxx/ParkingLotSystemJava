package com.parkinglot.models;

import com.parkinglot.enums.SpaceSize;
import com.parkinglot.enums.VehicleType;
import java.util.Arrays;
import java.util.List;

public class Car extends Vehicle {
    public Car(String licensePlate) {
        super(licensePlate, VehicleType.CAR);
    }

    @Override
    public List<SpaceSize> getCompatibleSpaceSizes() {
        return Arrays.asList(SpaceSize.MEDIUM, SpaceSize.LARGE);
    }
}