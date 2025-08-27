package com.parkinglot.services;

import com.parkinglot.enums.SpaceSize;
import com.parkinglot.enums.VehicleType;
import com.parkinglot.exceptions.NotImplementedException;
import com.parkinglot.exceptions.ParkingException;
import com.parkinglot.models.ParkingSpace;
import com.parkinglot.models.ParkingTicket;
import com.parkinglot.models.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class ParkingLotService implements IParkingLotService {
    private final Map<SpaceSize, List<ParkingSpace>> spaces;
    private final Map<String, ParkingTicket> activeTickets;
    private final Map<VehicleType, BigDecimal> hourlyRates;
    private int ticketCounter;

    public ParkingLotService(int smallSpaces, int mediumSpaces, int largeSpaces) {
        this.spaces = new HashMap<>();
        this.activeTickets = new HashMap<>();
        this.ticketCounter = 1000;
        
        // Initialize hourly rates
        this.hourlyRates = new HashMap<>();
        hourlyRates.put(VehicleType.MOTORCYCLE, new BigDecimal("2.00"));
        hourlyRates.put(VehicleType.CAR, new BigDecimal("4.00"));
        hourlyRates.put(VehicleType.TRUCK, new BigDecimal("6.50"));

        // TODO: Initialize parking spaces based on the provided counts
        initializeParkingSpaces(smallSpaces, mediumSpaces, largeSpaces);
    }

    private void initializeParkingSpaces(int smallSpaces, int mediumSpaces, int largeSpaces) {
        // TODO: Create parking spaces for each size category
        // Small spaces: S001, S002, etc.
        // Medium spaces: M001, M002, etc.
        // Large spaces: L001, L002, etc.

        List<ParkingSpace> l1 = new ArrayList<>();
        for (int i = 0; i < smallSpaces; i++) {
            l1.add(new ParkingSpace(String.format("S%03d", i+1), SpaceSize.SMALL));
        }
        spaces.put(SpaceSize.SMALL, l1);

        List<ParkingSpace> l2 = new ArrayList<>();
        for (int i = 0; i < mediumSpaces; i++) {
            l2.add(new ParkingSpace(String.format("M%03d", i+1), SpaceSize.MEDIUM));
        }
        spaces.put(SpaceSize.MEDIUM, l1);

        List<ParkingSpace> l3 = new ArrayList<>();
        for (int i = 0; i < largeSpaces; i++) {
            l3.add(new ParkingSpace(String.format("L%03d", i+1), SpaceSize.LARGE));
        }
        spaces.put(SpaceSize.LARGE, l3);
    }

    @Override
    public ParkingTicket parkVehicle(Vehicle vehicle) throws ParkingException {
        // TODO: Implement parking logic
        // 1. Validate input
        // 2. Find an available space that can fit the vehicle
        // 3. Park the vehicle in the space
        // 4. Create and return a parking ticket
        // 5. Store the ticket in _activeTickets
        
        ParkingSpace space = findAvailableSpace(vehicle);
        space.parkVehicle(vehicle);
        
        ParkingTicket ticket = new ParkingTicket(generateTicketId(), vehicle.getLicensePlate(), space.getId(), LocalDateTime.now());
        activeTickets.put(ticket.getTicketId(), ticket);
        return ticket;
    }

    @Override
    public Vehicle unparkVehicle(String ticketId) throws ParkingException {
        // TODO: Implement unparking logic
        // 1. Validate ticket exists
        // 2. Find the parked vehicle and space
        // 3. Remove vehicle from space
        // 4. Mark ticket as completed (set exit time)
        // 5. Remove ticket from _activeTickets
        // 6. Return the vehicle
        throw new NotImplementedException();
    }

    @Override
    public Map<SpaceSize, Integer> getAvailableSpaces() {
        // TODO: Return count of available spaces for each size
        throw new NotImplementedException();
    }

    @Override
    public boolean isFull() {
        // TODO: Check if all spaces are occupied
        throw new NotImplementedException();
    }

    @Override
    public Vehicle findVehicleByLicensePlate(String licensePlate) {
        // TODO: Search for a vehicle by license plate
        throw new NotImplementedException();
    }

    @Override
    public List<Vehicle> getAllVehiclesOfType(VehicleType type) {
        // TODO: Return all currently parked vehicles of the specified type
        throw new NotImplementedException();
    }

    @Override
    public BigDecimal calculateParkingFee(String ticketId) throws ParkingException {
        // TODO: Calculate the parking fee for a given ticket
        // Use the hourly rates defined in _hourlyRates
        throw new NotImplementedException();
    }

    @Override
    public int getTotalCapacity() {
        // TODO: Return total number of parking spaces
        throw new NotImplementedException();
    }

    @Override
    public int getOccupiedSpaces() {
        // TODO: Return number of occupied spaces
        throw new NotImplementedException();
    }

    @Override
    public double getOccupancyRate() {
        // TODO: Calculate and return occupancy rate as a percentage
        throw new NotImplementedException();
    }

    private ParkingSpace findAvailableSpace(Vehicle vehicle) {
        // TODO: Find the first available space that can fit the vehicle
        // Should check spaces in order of preference (smallest suitable size first)
        throw new NotImplementedException();
    }

    // Helper method - this one is implemented for you
    private ParkingSpace findSpaceById(String spaceId) {
        return spaces.values().stream()
                .flatMap(List::stream)
                .filter(space -> spaceId.equals(space.getId()))
                .findFirst()
                .orElse(null);
    }

    // Helper method - this one is implemented for you
    private String generateTicketId() {
        return String.format("T%06d", ++ticketCounter);
    }
}