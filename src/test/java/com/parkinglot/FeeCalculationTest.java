package com.parkinglot;

import com.parkinglot.models.ParkingTicket;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class FeeCalculationTest {

    @Test
    public void calculateFee_OneHourParking_ShouldReturnExactHourlyRate() {
        // Arrange
        LocalDateTime entryTime = LocalDateTime.now().minusHours(1);
        ParkingTicket ticket = new ParkingTicket("T001", "MC001", "S001", entryTime);
        ticket.markExit(entryTime.plusHours(1));
        BigDecimal hourlyRate = new BigDecimal("5.00");

        // Act
        BigDecimal fee = ticket.calculateFee(hourlyRate);

        // Assert
        assertEquals(new BigDecimal("5.00"), fee);
    }

    @Test
    public void calculateFee_PartialHour_ShouldRoundUpToNextHour() {
        // Arrange
        LocalDateTime entryTime = LocalDateTime.now().minusMinutes(30);
        ParkingTicket ticket = new ParkingTicket("T001", "MC001", "S001", entryTime);
        ticket.markExit(entryTime.plusMinutes(30));
        BigDecimal hourlyRate = new BigDecimal("4.00");

        // Act
        BigDecimal fee = ticket.calculateFee(hourlyRate);

        // Assert
        assertEquals(new BigDecimal("4.00"), fee); // 30 minutes rounds up to 1 hour
    }

    @Test
    public void calculateFee_TwoAndHalfHours_ShouldRoundUpToThreeHours() {
        // Arrange
        LocalDateTime entryTime = LocalDateTime.now().minusHours(2).minusMinutes(30);
        ParkingTicket ticket = new ParkingTicket("T001", "CAR001", "M001", entryTime);
        ticket.markExit(entryTime.plusHours(2).plusMinutes(30));
        BigDecimal hourlyRate = new BigDecimal("6.00");

        // Act
        BigDecimal fee = ticket.calculateFee(hourlyRate);

        // Assert
        assertEquals(new BigDecimal("18.00"), fee); // 2.5 hours rounds up to 3 hours
    }

    @Test
    public void calculateFee_ZeroMinutes_ShouldChargeFreeHour() {
        // Arrange
        LocalDateTime entryTime = LocalDateTime.now();
        ParkingTicket ticket = new ParkingTicket("T001", "MC001", "S001", entryTime);
        ticket.markExit(entryTime);
        BigDecimal hourlyRate = new BigDecimal("3.00");

        // Act
        BigDecimal fee = ticket.calculateFee(hourlyRate);

        // Assert
        assertEquals(new BigDecimal("0.00"), fee); // 0 minutes = 0 hours
    }

    @Test
    public void calculateFee_OneMinute_ShouldRoundUpToOneHour() {
        // Arrange
        LocalDateTime entryTime = LocalDateTime.now().minusMinutes(1);
        ParkingTicket ticket = new ParkingTicket("T001", "MC001", "S001", entryTime);
        ticket.markExit(entryTime.plusMinutes(1));
        BigDecimal hourlyRate = new BigDecimal("2.50");

        // Act
        BigDecimal fee = ticket.calculateFee(hourlyRate);

        // Assert
        assertEquals(new BigDecimal("2.50"), fee); // 1 minute rounds up to 1 hour
    }

    @Test
    public void calculateFee_ExactlyTwoHours_ShouldChargeTwoHours() {
        // Arrange
        LocalDateTime entryTime = LocalDateTime.now().minusHours(2);
        ParkingTicket ticket = new ParkingTicket("T001", "TRUCK001", "L001", entryTime);
        ticket.markExit(entryTime.plusHours(2));
        BigDecimal hourlyRate = new BigDecimal("8.50");

        // Act
        BigDecimal fee = ticket.calculateFee(hourlyRate);

        // Assert
        assertEquals(new BigDecimal("17.00"), fee); // Exactly 2 hours = 2 * 8.50
    }

    @Test
    public void calculateFee_NegativeRate_ShouldThrowException() {
        // Arrange
        LocalDateTime entryTime = LocalDateTime.now().minusHours(1);
        ParkingTicket ticket = new ParkingTicket("T001", "MC001", "S001", entryTime);
        ticket.markExit(entryTime.plusHours(1));
        BigDecimal negativeRate = new BigDecimal("-5.00");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> ticket.calculateFee(negativeRate));
    }

    @Test
    public void calculateFee_NullRate_ShouldThrowException() {
        // Arrange
        LocalDateTime entryTime = LocalDateTime.now().minusHours(1);
        ParkingTicket ticket = new ParkingTicket("T001", "MC001", "S001", entryTime);
        ticket.markExit(entryTime.plusHours(1));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> ticket.calculateFee(null));
    }

    @Test
    public void getParkingDuration_WithExitTime_ShouldReturnCorrectDuration() {
        // Arrange
        LocalDateTime entryTime = LocalDateTime.now().minusHours(2).minusMinutes(30);
        ParkingTicket ticket = new ParkingTicket("T001", "MC001", "S001", entryTime);
        ticket.markExit(entryTime.plusHours(2).plusMinutes(30));

        // Act
        Duration duration = ticket.getParkingDuration();

        // Assert
        assertEquals(150, duration.toMinutes()); // 2.5 hours = 150 minutes
    }

    @Test
    public void getParkingDuration_WithoutExitTime_ShouldUseCurrentTime() {
        // Arrange
        LocalDateTime entryTime = LocalDateTime.now().minusMinutes(30);
        ParkingTicket ticket = new ParkingTicket("T001", "MC001", "S001", entryTime);

        // Act
        Duration duration = ticket.getParkingDuration();

        // Assert
        assertTrue(duration.toMinutes() >= 29 && duration.toMinutes() <= 31); // Approximately 30 minutes
    }
}