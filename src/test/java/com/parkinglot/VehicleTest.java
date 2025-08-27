package com.parkinglot;

import com.parkinglot.enums.SpaceSize;
import com.parkinglot.enums.VehicleType;
import com.parkinglot.models.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    @Test
    public void motorcycle_Constructor_ShouldSetPropertiesCorrectly() {
        // Arrange & Act
        Vehicle motorcycle = new Motorcycle("MC001");

        // Assert
        assertEquals("MC001", motorcycle.getLicensePlate());
        assertEquals(VehicleType.MOTORCYCLE, motorcycle.getType());
        assertNull(motorcycle.getEntryTime());
    }

    @Test
    public void motorcycle_GetCompatibleSpaceSizes_ShouldReturnAllSizes() {
        // Arrange
        Vehicle motorcycle = new Motorcycle("MC001");

        // Act
        List<SpaceSize> compatibleSizes = motorcycle.getCompatibleSpaceSizes();

        // Assert
        assertEquals(3, compatibleSizes.size());
        assertTrue(compatibleSizes.contains(SpaceSize.SMALL));
        assertTrue(compatibleSizes.contains(SpaceSize.MEDIUM));
        assertTrue(compatibleSizes.contains(SpaceSize.LARGE));
    }

    @Test
    public void car_Constructor_ShouldSetPropertiesCorrectly() {
        // Arrange & Act
        Vehicle car = new Car("CAR001");

        // Assert
        assertEquals("CAR001", car.getLicensePlate());
        assertEquals(VehicleType.CAR, car.getType());
        assertNull(car.getEntryTime());
    }

    @Test
    public void car_GetCompatibleSpaceSizes_ShouldReturnMediumAndLarge() {
        // Arrange
        Vehicle car = new Car("CAR001");

        // Act
        List<SpaceSize> compatibleSizes = car.getCompatibleSpaceSizes();

        // Assert
        assertEquals(2, compatibleSizes.size());
        assertFalse(compatibleSizes.contains(SpaceSize.SMALL));
        assertTrue(compatibleSizes.contains(SpaceSize.MEDIUM));
        assertTrue(compatibleSizes.contains(SpaceSize.LARGE));
    }

    @Test
    public void truck_Constructor_ShouldSetPropertiesCorrectly() {
        // Arrange & Act
        Vehicle truck = new Truck("TRUCK001");

        // Assert
        assertEquals("TRUCK001", truck.getLicensePlate());
        assertEquals(VehicleType.TRUCK, truck.getType());
        assertNull(truck.getEntryTime());
    }

    @Test
    public void truck_GetCompatibleSpaceSizes_ShouldReturnOnlyLarge() {
        // Arrange
        Vehicle truck = new Truck("TRUCK001");

        // Act
        List<SpaceSize> compatibleSizes = truck.getCompatibleSpaceSizes();

        // Assert
        assertEquals(1, compatibleSizes.size());
        assertFalse(compatibleSizes.contains(SpaceSize.SMALL));
        assertFalse(compatibleSizes.contains(SpaceSize.MEDIUM));
        assertTrue(compatibleSizes.contains(SpaceSize.LARGE));
    }

    @Test
    public void vehicle_Constructor_WithNullLicensePlate_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Motorcycle(null));
    }

    @Test
    public void vehicle_Constructor_WithEmptyLicensePlate_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Motorcycle(""));
        assertThrows(IllegalArgumentException.class, () -> new Motorcycle("   "));
    }
}