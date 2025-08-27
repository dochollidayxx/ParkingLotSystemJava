package com.parkinglot.services;

import com.parkinglot.enums.SpaceSize;
import com.parkinglot.enums.VehicleType;
import com.parkinglot.exceptions.ParkingException;
import com.parkinglot.models.ParkingSpace;
import com.parkinglot.models.ParkingTicket;
import com.parkinglot.models.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

        initializeParkingSpaces(smallSpaces, mediumSpaces, largeSpaces);
    }

    private void initializeParkingSpaces(int smallSpaces, int mediumSpaces, int largeSpaces) {
        spaces.put(SpaceSize.SMALL, new ArrayList<>());
        spaces.put(SpaceSize.MEDIUM, new ArrayList<>());
        spaces.put(SpaceSize.LARGE, new ArrayList<>());

        // Create small spaces: S001, S002, etc.
        for (int i = 1; i <= smallSpaces; i++) {
            spaces.get(SpaceSize.SMALL).add(new ParkingSpace(String.format("S%03d", i), SpaceSize.SMALL));
        }

        // Create medium spaces: M001, M002, etc.
        for (int i = 1; i <= mediumSpaces; i++) {
            spaces.get(SpaceSize.MEDIUM).add(new ParkingSpace(String.format("M%03d", i), SpaceSize.MEDIUM));
        }

        // Create large spaces: L001, L002, etc.
        for (int i = 1; i <= largeSpaces; i++) {
            spaces.get(SpaceSize.LARGE).add(new ParkingSpace(String.format("L%03d", i), SpaceSize.LARGE));
        }
    }

    @Override
    public ParkingTicket parkVehicle(Vehicle vehicle) throws ParkingException {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }

        // Check if vehicle is already parked
        if (findVehicleByLicensePlate(vehicle.getLicensePlate()) != null) {
            throw new ParkingException("Vehicle with license plate " + vehicle.getLicensePlate() + " is already parked");
        }

        ParkingSpace availableSpace = findAvailableSpace(vehicle);
        if (availableSpace == null) {
            throw new ParkingException("No available parking spaces for this vehicle type");
        }

        // Set entry time and park the vehicle
        vehicle.setEntryTime(LocalDateTime.now());
        availableSpace.parkVehicle(vehicle);

        // Create parking ticket
        String ticketId = generateTicketId();
        ParkingTicket ticket = new ParkingTicket(ticketId, vehicle.getLicensePlate(), 
                                                availableSpace.getId(), vehicle.getEntryTime());
        
        activeTickets.put(ticketId, ticket);
        return ticket;
    }

    @Override
    public Vehicle unparkVehicle(String ticketId) throws ParkingException {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be null or empty");
        }

        ParkingTicket ticket = activeTickets.get(ticketId);
        if (ticket == null) {
            throw new ParkingException("Invalid ticket ID: " + ticketId);
        }

        // Find the parking space and vehicle
        ParkingSpace space = findSpaceById(ticket.getSpaceId());
        if (space == null || !space.isOccupied()) {
            throw new ParkingException("No vehicle found for ticket ID: " + ticketId);
        }

        // Remove vehicle from space
        Vehicle vehicle = space.removeVehicle();
        
        // Mark ticket as completed
        ticket.markExit(LocalDateTime.now());
        
        // Remove ticket from active tickets
        activeTickets.remove(ticketId);
        
        return vehicle;
    }

    @Override
    public Map<SpaceSize, Integer> getAvailableSpaces() {
        Map<SpaceSize, Integer> availableSpaces = new HashMap<>();
        
        for (SpaceSize size : SpaceSize.values()) {
            List<ParkingSpace> spacesOfSize = spaces.get(size);
            long availableCount = spacesOfSize.stream()
                    .filter(space -> !space.isOccupied())
                    .count();
            availableSpaces.put(size, (int) availableCount);
        }
        
        return availableSpaces;
    }

    @Override
    public boolean isFull() {
        return spaces.values().stream()
                .flatMap(List::stream)
                .allMatch(ParkingSpace::isOccupied);
    }

    @Override
    public Vehicle findVehicleByLicensePlate(String licensePlate) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            return null;
        }

        return spaces.values().stream()
                .flatMap(List::stream)
                .filter(ParkingSpace::isOccupied)
                .map(ParkingSpace::getCurrentVehicle)
                .filter(vehicle -> licensePlate.equals(vehicle.getLicensePlate()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Vehicle> getAllVehiclesOfType(VehicleType type) {
        if (type == null) {
            return new ArrayList<>();
        }

        return spaces.values().stream()
                .flatMap(List::stream)
                .filter(ParkingSpace::isOccupied)
                .map(ParkingSpace::getCurrentVehicle)
                .filter(vehicle -> type.equals(vehicle.getType()))
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal calculateParkingFee(String ticketId) throws ParkingException {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be null or empty");
        }

        ParkingTicket ticket = activeTickets.get(ticketId);
        if (ticket == null) {
            throw new ParkingException("Invalid ticket ID: " + ticketId);
        }

        // Find the vehicle to get its type for the hourly rate
        ParkingSpace space = findSpaceById(ticket.getSpaceId());
        if (space == null || !space.isOccupied()) {
            throw new ParkingException("No vehicle found for ticket ID: " + ticketId);
        }

        Vehicle vehicle = space.getCurrentVehicle();
        BigDecimal hourlyRate = hourlyRates.get(vehicle.getType());
        
        return ticket.calculateFee(hourlyRate);
    }

    @Override
    public int getTotalCapacity() {
        return spaces.values().stream()
                .mapToInt(List::size)
                .sum();
    }

    @Override
    public int getOccupiedSpaces() {
        return (int) spaces.values().stream()
                .flatMap(List::stream)
                .filter(ParkingSpace::isOccupied)
                .count();
    }

    @Override
    public double getOccupancyRate() {
        int total = getTotalCapacity();
        if (total == 0) {
            return 0.0;
        }
        return (double) getOccupiedSpaces() / total * 100.0;
    }

    private ParkingSpace findAvailableSpace(Vehicle vehicle) {
        List<SpaceSize> compatibleSizes = vehicle.getCompatibleSpaceSizes();
        
        // Check spaces in order of preference (smallest suitable size first)
        for (SpaceSize size : compatibleSizes) {
            List<ParkingSpace> spacesOfSize = spaces.get(size);
            for (ParkingSpace space : spacesOfSize) {
                if (!space.isOccupied() && space.canFitVehicle(vehicle)) {
                    return space;
                }
            }
        }
        
        return null;
    }

    private ParkingSpace findSpaceById(String spaceId) {
        return spaces.values().stream()
                .flatMap(List::stream)
                .filter(space -> spaceId.equals(space.getId()))
                .findFirst()
                .orElse(null);
    }

    private String generateTicketId() {
        return String.format("T%06d", ++ticketCounter);
    }
}