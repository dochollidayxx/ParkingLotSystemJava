package com.parkinglot.services;

import com.parkinglot.enums.SpaceSize;
import com.parkinglot.enums.VehicleType;
import com.parkinglot.exceptions.ParkingException;
import com.parkinglot.models.ParkingTicket;
import com.parkinglot.models.Vehicle;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IParkingLotService {
    // Core operations
    ParkingTicket parkVehicle(Vehicle vehicle) throws ParkingException;
    Vehicle unparkVehicle(String ticketId) throws ParkingException;
    Map<SpaceSize, Integer> getAvailableSpaces();
    boolean isFull();
    
    // Additional operations
    Vehicle findVehicleByLicensePlate(String licensePlate);
    List<Vehicle> getAllVehiclesOfType(VehicleType type);
    BigDecimal calculateParkingFee(String ticketId) throws ParkingException;
    
    // Statistics
    int getTotalCapacity();
    int getOccupiedSpaces();
    double getOccupancyRate();
}