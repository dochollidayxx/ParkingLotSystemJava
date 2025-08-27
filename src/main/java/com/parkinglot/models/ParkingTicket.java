package com.parkinglot.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

public class ParkingTicket {
    private final String ticketId;
    private final String licensePlate;
    private final String spaceId;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public ParkingTicket(String ticketId, String licensePlate, String spaceId, LocalDateTime entryTime) {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be null or empty");
        }
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }
        if (spaceId == null || spaceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Space ID cannot be null or empty");
        }
        if (entryTime == null) {
            throw new IllegalArgumentException("Entry time cannot be null");
        }

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
        if (exitTime == null) {
            throw new IllegalArgumentException("Exit time cannot be null");
        }
        if (exitTime.isBefore(entryTime)) {
            throw new IllegalArgumentException("Exit time cannot be before entry time");
        }
        this.exitTime = exitTime;
    }

    public Duration getParkingDuration() {
        LocalDateTime endTime = (exitTime != null) ? exitTime : LocalDateTime.now();
        return Duration.between(entryTime, endTime);
    }

    public BigDecimal calculateFee(BigDecimal hourlyRate) {
        if (hourlyRate == null || hourlyRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Hourly rate must be positive");
        }

        Duration duration = getParkingDuration();
        long minutes = duration.toMinutes();
        
        // Round up to the next hour for partial hours
        long hours = (minutes + 59) / 60; // This effectively rounds up
        
        return hourlyRate.multiply(BigDecimal.valueOf(hours)).setScale(2, RoundingMode.HALF_UP);
    }
}