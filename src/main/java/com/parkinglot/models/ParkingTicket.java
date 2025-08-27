package com.parkinglot.models;

import com.parkinglot.exceptions.NotImplementedException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

public class ParkingTicket {
    private final String ticketId;
    private final String licensePlate;
    private final String spaceId;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public ParkingTicket(String ticketId, String licensePlate, String spaceId, LocalDateTime entryTime) {
        // TODO: Implement constructor
        this.ticketId = ticketId;
        this.licensePlate = licensePlate;
        this.spaceId = spaceId;
        this.entryTime = entryTime;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void markExit(LocalDateTime exitTime) {
        // TODO: Set the exit time
        this.exitTime = exitTime;
    }

    public Duration getParkingDuration() {
        // TODO: Calculate parking duration
        // If exitTime is null, use current time
        LocalDateTime exit = exitTime == null ? LocalDateTime.now() : exitTime;
        return Duration.ofSeconds(exit.getSecond() - entryTime.getSecond());
    }

    public BigDecimal calculateFee(BigDecimal hourlyRate) {
        // TODO: Calculate parking fee based on duration and hourly rate
        // Round up to the next hour for partial hours
        return hourlyRate.multiply(BigDecimal.valueOf((getParkingDuration().toHours())));
    }
}