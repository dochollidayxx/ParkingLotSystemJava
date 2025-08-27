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
        // TODO: Motorcycles can park in any size space
        throw new NotImplementedException();
    }
}