package com.parkinglot.models;

import com.parkinglot.enums.SpaceSize;
import com.parkinglot.enums.VehicleType;
import com.parkinglot.exceptions.NotImplementedException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class Vehicle {
    private final String licensePlate;
    private final VehicleType type;
    private LocalDateTime entryTime;

    protected Vehicle(String licensePlate, VehicleType type) {
        // TODO: Implement constructor validation
        // Should validate that licensePlate is not null or empty
        // Should set the licensePlate and type fields
        throw new NotImplementedException();
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

    // TODO: Add method to determine which parking space sizes this vehicle can use
    public abstract List<SpaceSize> getCompatibleSpaceSizes();
}