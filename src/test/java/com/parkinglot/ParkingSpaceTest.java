package com.parkinglot;

import com.parkinglot.enums.SpaceSize;
import com.parkinglot.exceptions.ParkingException;
import com.parkinglot.models.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingSpaceTest {

    @Test
    public void parkingSpace_Constructor_ShouldSetPropertiesCorrectly() {
        // Arrange & Act
        ParkingSpace space = new ParkingSpace("S001", SpaceSize.SMALL);

        // Assert
        assertEquals("S001", space.getId());
        assertEquals(SpaceSize.SMALL, space.getSize());
        assertFalse(space.isOccupied());
        assertNull(space.getCurrentVehicle());
    }

    @Test
    public void parkVehicle_ValidVehicle_ShouldParkSuccessfully() throws ParkingException {
        // Arrange
        ParkingSpace space = new ParkingSpace("S001", SpaceSize.SMALL);
        Vehicle motorcycle = new Motorcycle("MC001");

        // Act
        space.parkVehicle(motorcycle);

        // Assert
        assertTrue(space.isOccupied());
        assertEquals(motorcycle, space.getCurrentVehicle());
    }

    @Test
    public void parkVehicle_WhenSpaceOccupied_ShouldThrowException() throws ParkingException {
        // Arrange
        ParkingSpace space = new ParkingSpace("S001", SpaceSize.SMALL);
        Vehicle motorcycle1 = new Motorcycle("MC001");
        Vehicle motorcycle2 = new Motorcycle("MC002");
        space.parkVehicle(motorcycle1);

        // Act & Assert
        assertThrows(ParkingException.class, () -> space.parkVehicle(motorcycle2));
    }

    @Test
    public void parkVehicle_IncompatibleVehicle_ShouldThrowException() {
        // Arrange
        ParkingSpace smallSpace = new ParkingSpace("S001", SpaceSize.SMALL);
        Vehicle truck = new Truck("TRUCK001");

        // Act & Assert
        assertThrows(ParkingException.class, () -> smallSpace.parkVehicle(truck));
    }

    @Test
    public void removeVehicle_WhenOccupied_ShouldReturnVehicle() throws ParkingException {
        // Arrange
        ParkingSpace space = new ParkingSpace("S001", SpaceSize.SMALL);
        Vehicle motorcycle = new Motorcycle("MC001");
        space.parkVehicle(motorcycle);

        // Act
        Vehicle removedVehicle = space.removeVehicle();

        // Assert
        assertEquals(motorcycle, removedVehicle);
        assertFalse(space.isOccupied());
        assertNull(space.getCurrentVehicle());
    }

    @Test
    public void removeVehicle_WhenNotOccupied_ShouldThrowException() {
        // Arrange
        ParkingSpace space = new ParkingSpace("S001", SpaceSize.SMALL);

        // Act & Assert
        assertThrows(ParkingException.class, space::removeVehicle);
    }

    @Test
    public void canFitVehicle_MotorcycleInSmallSpace_ShouldReturnTrue() {
        // Arrange
        ParkingSpace smallSpace = new ParkingSpace("S001", SpaceSize.SMALL);
        Vehicle motorcycle = new Motorcycle("MC001");

        // Act
        boolean canFit = smallSpace.canFitVehicle(motorcycle);

        // Assert
        assertTrue(canFit);
    }

    @Test
    public void canFitVehicle_CarInSmallSpace_ShouldReturnFalse() {
        // Arrange
        ParkingSpace smallSpace = new ParkingSpace("S001", SpaceSize.SMALL);
        Vehicle car = new Car("CAR001");

        // Act
        boolean canFit = smallSpace.canFitVehicle(car);

        // Assert
        assertFalse(canFit);
    }

    @Test
    public void canFitVehicle_CarInMediumSpace_ShouldReturnTrue() {
        // Arrange
        ParkingSpace mediumSpace = new ParkingSpace("M001", SpaceSize.MEDIUM);
        Vehicle car = new Car("CAR001");

        // Act
        boolean canFit = mediumSpace.canFitVehicle(car);

        // Assert
        assertTrue(canFit);
    }

    @Test
    public void canFitVehicle_TruckInLargeSpace_ShouldReturnTrue() {
        // Arrange
        ParkingSpace largeSpace = new ParkingSpace("L001", SpaceSize.LARGE);
        Vehicle truck = new Truck("TRUCK001");

        // Act
        boolean canFit = largeSpace.canFitVehicle(truck);

        // Assert
        assertTrue(canFit);
    }

    @Test
    public void canFitVehicle_TruckInMediumSpace_ShouldReturnFalse() {
        // Arrange
        ParkingSpace mediumSpace = new ParkingSpace("M001", SpaceSize.MEDIUM);
        Vehicle truck = new Truck("TRUCK001");

        // Act
        boolean canFit = mediumSpace.canFitVehicle(truck);

        // Assert
        assertFalse(canFit);
    }

    @Test
    public void canFitVehicle_NullVehicle_ShouldReturnFalse() {
        // Arrange
        ParkingSpace space = new ParkingSpace("S001", SpaceSize.SMALL);

        // Act
        boolean canFit = space.canFitVehicle(null);

        // Assert
        assertFalse(canFit);
    }
}