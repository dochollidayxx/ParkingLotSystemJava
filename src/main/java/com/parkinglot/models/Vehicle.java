package com.parkinglot.models;

import com.parkinglot.enums.SpaceSize;
import com.parkinglot.enums.VehicleType;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public abstract class Vehicle {
    private final String licensePlate;
    private final VehicleType type;
    private LocalDateTime entryTime;

    protected Vehicle(String licensePlate, VehicleType type) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return type;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public abstract List<SpaceSize> getCompatibleSpaceSizes();
}

public class Motorcycle extends Vehicle {
    public Motorcycle(String licensePlate) {
        super(licensePlate, VehicleType.MOTORCYCLE);
    }

    @Override
    public List<SpaceSize> getCompatibleSpaceSizes() {
        return Arrays.asList(SpaceSize.SMALL, SpaceSize.MEDIUM, SpaceSize.LARGE);
    }
}

public class Car extends Vehicle {
    public Car(String licensePlate) {
        super(licensePlate, VehicleType.CAR);
    }

    @Override
    public List<SpaceSize> getCompatibleSpaceSizes() {
        return Arrays.asList(SpaceSize.MEDIUM, SpaceSize.LARGE);
    }
}

public class Truck extends Vehicle {
    public Truck(String licensePlate) {
        super(licensePlate, VehicleType.TRUCK);
    }

    @Override
    public List<SpaceSize> getCompatibleSpaceSizes() {
        return Arrays.asList(SpaceSize.LARGE);
    }
}