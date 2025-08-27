package com.parkinglot;

import com.parkinglot.enums.SpaceSize;
import com.parkinglot.enums.VehicleType;
import com.parkinglot.exceptions.ParkingException;
import com.parkinglot.models.*;
import com.parkinglot.services.ParkingLotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotServiceTest {
    
    private ParkingLotService createParkingLot(int small, int medium, int large) {
        return new ParkingLotService(small, medium, large);
    }
    
    private ParkingLotService createParkingLot() {
        return createParkingLot(2, 2, 2);
    }

    @Test
    public void parkVehicle_Motorcycle_ShouldParkSuccessfully() throws ParkingException {
        // Arrange
        ParkingLotService parkingLot = createParkingLot();
        Vehicle motorcycle = new Motorcycle("MC001");

        // Act
        ParkingTicket ticket = parkingLot.parkVehicle(motorcycle);

        // Assert
        assertNotNull(ticket);
        assertEquals("MC001", ticket.getLicensePlate());
        assertNotNull(ticket.getTicketId());
        assertNotNull(ticket.getSpaceId());
    }

    @Test
    public void parkVehicle_Car_ShouldParkInMediumOrLargeSpace() throws ParkingException {
        // Arrange
        ParkingLotService parkingLot = createParkingLot();
        Vehicle car = new Car("CAR001");

        // Act
        ParkingTicket ticket = parkingLot.parkVehicle(car);
        Map<SpaceSize, Integer> availableSpaces = parkingLot.getAvailableSpaces();

        // Assert
        assertNotNull(ticket);
        assertEquals(2, availableSpaces.get(SpaceSize.SMALL).intValue()); // Small spaces should be untouched
    }

    @Test
    public void parkVehicle_Truck_ShouldOnlyParkInLargeSpace() throws ParkingException {
        // Arrange
        ParkingLotService parkingLot = createParkingLot();
        Vehicle truck = new Truck("TRUCK001");

        // Act
        ParkingTicket ticket = parkingLot.parkVehicle(truck);
        Map<SpaceSize, Integer> availableSpaces = parkingLot.getAvailableSpaces();

        // Assert
        assertNotNull(ticket);
        assertEquals(2, availableSpaces.get(SpaceSize.SMALL).intValue());
        assertEquals(2, availableSpaces.get(SpaceSize.MEDIUM).intValue());
        assertEquals(1, availableSpaces.get(SpaceSize.LARGE).intValue()); // One large space should be taken
    }

    @Test
    public void parkVehicle_WhenNoAvailableSpaces_ShouldThrowException() throws ParkingException {
        // Arrange
        ParkingLotService parkingLot = createParkingLot(0, 0, 1);
        Vehicle truck1 = new Truck("TRUCK001");
        Vehicle truck2 = new Truck("TRUCK002");

        // Act
        parkingLot.parkVehicle(truck1); // Fill the only large space

        // Assert
        assertThrows(ParkingException.class, () -> parkingLot.parkVehicle(truck2));
    }

    @Test
    public void unparkVehicle_ValidTicket_ShouldReturnVehicle() throws ParkingException {
        // Arrange
        ParkingLotService parkingLot = createParkingLot();
        Vehicle motorcycle = new Motorcycle("MC001");
        ParkingTicket ticket = parkingLot.parkVehicle(motorcycle);

        // Act
        Vehicle unparkedVehicle = parkingLot.unparkVehicle(ticket.getTicketId());

        // Assert
        assertNotNull(unparkedVehicle);
        assertEquals("MC001", unparkedVehicle.getLicensePlate());
        assertEquals(VehicleType.MOTORCYCLE, unparkedVehicle.getType());
    }

    @Test
    public void unparkVehicle_InvalidTicket_ShouldThrowException() {
        // Arrange
        ParkingLotService parkingLot = createParkingLot();

        // Act & Assert
        assertThrows(ParkingException.class, () -> parkingLot.unparkVehicle("INVALID"));
    }

    @Test
    public void getAvailableSpaces_InitialState_ShouldReturnCorrectCounts() {
        // Arrange
        ParkingLotService parkingLot = createParkingLot(3, 2, 1);

        // Act
        Map<SpaceSize, Integer> availableSpaces = parkingLot.getAvailableSpaces();

        // Assert
        assertEquals(3, availableSpaces.get(SpaceSize.SMALL).intValue());
        assertEquals(2, availableSpaces.get(SpaceSize.MEDIUM).intValue());
        assertEquals(1, availableSpaces.get(SpaceSize.LARGE).intValue());
    }

    @Test
    public void isFull_WhenNotFull_ShouldReturnFalse() {
        // Arrange
        ParkingLotService parkingLot = createParkingLot();

        // Act & Assert
        assertFalse(parkingLot.isFull());
    }

    @Test
    public void isFull_WhenFull_ShouldReturnTrue() throws ParkingException {
        // Arrange
        ParkingLotService parkingLot = createParkingLot(1, 1, 1);
        parkingLot.parkVehicle(new Motorcycle("MC001"));
        parkingLot.parkVehicle(new Car("CAR001"));
        parkingLot.parkVehicle(new Truck("TRUCK001"));

        // Act & Assert
        assertTrue(parkingLot.isFull());
    }

    @Test
    public void findVehicleByLicensePlate_ExistingVehicle_ShouldReturnVehicle() throws ParkingException {
        // Arrange
        ParkingLotService parkingLot = createParkingLot();
        Vehicle motorcycle = new Motorcycle("MC001");
        parkingLot.parkVehicle(motorcycle);

        // Act
        Vehicle foundVehicle = parkingLot.findVehicleByLicensePlate("MC001");

        // Assert
        assertNotNull(foundVehicle);
        assertEquals("MC001", foundVehicle.getLicensePlate());
        assertEquals(VehicleType.MOTORCYCLE, foundVehicle.getType());
    }

    @Test
    public void findVehicleByLicensePlate_NonExistingVehicle_ShouldReturnNull() {
        // Arrange
        ParkingLotService parkingLot = createParkingLot();

        // Act
        Vehicle foundVehicle = parkingLot.findVehicleByLicensePlate("NONEXISTENT");

        // Assert
        assertNull(foundVehicle);
    }

    @Test
    public void getAllVehiclesOfType_ShouldReturnCorrectVehicles() throws ParkingException {
        // Arrange
        ParkingLotService parkingLot = createParkingLot();
        parkingLot.parkVehicle(new Motorcycle("MC001"));
        parkingLot.parkVehicle(new Motorcycle("MC002"));
        parkingLot.parkVehicle(new Car("CAR001"));

        // Act
        List<Vehicle> motorcycles = parkingLot.getAllVehiclesOfType(VehicleType.MOTORCYCLE);
        List<Vehicle> cars = parkingLot.getAllVehiclesOfType(VehicleType.CAR);
        List<Vehicle> trucks = parkingLot.getAllVehiclesOfType(VehicleType.TRUCK);

        // Assert
        assertEquals(2, motorcycles.size());
        assertEquals(1, cars.size());
        assertEquals(0, trucks.size());
    }

    @Test
    public void calculateParkingFee_ShouldReturnCorrectFee() throws ParkingException, InterruptedException {
        // Arrange
        ParkingLotService parkingLot = createParkingLot();
        Vehicle motorcycle = new Motorcycle("MC001");
        ParkingTicket ticket = parkingLot.parkVehicle(motorcycle);

        // Wait a small amount to ensure some time passes
        Thread.sleep(100);

        // Act
        BigDecimal fee = parkingLot.calculateParkingFee(ticket.getTicketId());

        // Assert
        assertNotNull(fee);
        assertTrue(fee.compareTo(BigDecimal.ZERO) >= 0);
        // Fee should be either $0.00 (for very short duration) or $2.00+ (for at least 1 minute)
        assertTrue(fee.equals(new BigDecimal("0.00")) || fee.compareTo(new BigDecimal("2.00")) >= 0);
    }

    @Test
    public void getTotalCapacity_ShouldReturnCorrectTotal() {
        // Arrange
        ParkingLotService parkingLot = createParkingLot(3, 2, 1);

        // Act
        int totalCapacity = parkingLot.getTotalCapacity();

        // Assert
        assertEquals(6, totalCapacity);
    }

    @Test
    public void getOccupiedSpaces_ShouldReturnCorrectCount() throws ParkingException {
        // Arrange
        ParkingLotService parkingLot = createParkingLot();
        parkingLot.parkVehicle(new Motorcycle("MC001"));
        parkingLot.parkVehicle(new Car("CAR001"));

        // Act
        int occupiedSpaces = parkingLot.getOccupiedSpaces();

        // Assert
        assertEquals(2, occupiedSpaces);
    }

    @Test
    public void getOccupancyRate_ShouldReturnCorrectRate() throws ParkingException {
        // Arrange
        ParkingLotService parkingLot = createParkingLot(2, 2, 2); // Total 6 spaces
        parkingLot.parkVehicle(new Motorcycle("MC001"));
        parkingLot.parkVehicle(new Car("CAR001"));
        parkingLot.parkVehicle(new Truck("TRUCK001"));

        // Act
        double occupancyRate = parkingLot.getOccupancyRate();

        // Assert
        assertEquals(50.0, occupancyRate, 0.01); // 3 out of 6 spaces = 50%
    }

    @Test
    public void parkVehicle_DuplicateLicensePlate_ShouldThrowException() throws ParkingException {
        // Arrange
        ParkingLotService parkingLot = createParkingLot();
        Vehicle motorcycle1 = new Motorcycle("MC001");
        Vehicle motorcycle2 = new Motorcycle("MC001");
        parkingLot.parkVehicle(motorcycle1);

        // Act & Assert
        assertThrows(ParkingException.class, () -> parkingLot.parkVehicle(motorcycle2));
    }
}