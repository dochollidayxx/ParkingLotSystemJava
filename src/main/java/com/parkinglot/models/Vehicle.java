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