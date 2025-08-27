package com.parkinglot.models;

import com.parkinglot.enums.SpaceSize;
import com.parkinglot.enums.VehicleType;
import com.parkinglot.exceptions.NotImplementedException;
import java.util.List;

public class Car extends Vehicle {
    public Car(String licensePlate) {
        super(licensePlate, VehicleType.CAR);
    }

    @Override
    public List<SpaceSize> getCompatibleSpaceSizes() {
        return List.of(SpaceSize.MEDIUM, SpaceSize.LARGE);
    }
}