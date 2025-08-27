# Parking Lot System - Coding Challenge

## Overview
Design and implement a parking management system for a multi-level parking lot. This system should handle vehicle parking, tracking, and provide various management capabilities.

## Problem Statement

You are tasked with implementing a parking lot management system with the following requirements:

### Core Requirements

1. **Parking Space Management**
   - The parking lot has **3 types of parking spaces**: `Large`, `Medium`, and `Small`
   - Each type has a fixed number of available slots
   - Vehicles must be parked in appropriately sized spaces (or larger if their size is unavailable)

2. **Vehicle Types**
   - Support 3 vehicle types: `Motorcycle`, `Car`, and `Truck`
   - Motorcycles can park in any size space
   - Cars can park in Medium or Large spaces
   - Trucks can only park in Large spaces

3. **Core Operations**
   - `parkVehicle`: Park a vehicle and return a parking ticket
   - `unparkVehicle`: Remove a vehicle using its ticket ID
   - `getAvailableSpaces`: Get count of available spaces by type
   - `isFull`: Check if the parking lot is completely full

4. **Parking Ticket System**
   - Generate unique ticket IDs
   - Track parking start time
   - Calculate parking duration
   - Store vehicle and space information

### Additional Requirements (Implement at least 2)

5. **Fee Calculation**
   - Implement hourly rate-based fee calculation
   - Different rates for different vehicle types
   - Support for partial hour billing

6. **Search Capabilities**
   - Find a vehicle by license plate
   - Get all vehicles of a specific type
   - Get parking history for a time period

7. **Statistics & Reporting**
   - Track peak usage times
   - Calculate occupancy rate
   - Generate daily summary reports

## Project Structure

```
parking-lot-system/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── parkinglot/
│   │               ├── enums/
│   │               │   ├── VehicleType.java
│   │               │   └── SpaceSize.java
│   │               ├── exceptions/
│   │               │   └── ParkingException.java
│   │               ├── models/
│   │               │   ├── Vehicle.java
│   │               │   ├── ParkingSpace.java
│   │               │   └── ParkingTicket.java
│   │               └── services/
│   │                   ├── IParkingLotService.java
│   │                   └── ParkingLotService.java
│   └── test/
│       └── java/
│           └── com/
│               └── parkinglot/
│                   ├── ParkingLotServiceTest.java
│                   ├── VehicleTest.java
│                   ├── ParkingSpaceTest.java
│                   └── FeeCalculationTest.java
├── pom.xml
└── README.md
```

## Your Task

1. **Complete the implementation** of the classes in the `src/main/java/com/parkinglot` folder
2. **Fix all failing unit tests** in the `src/test/java/com/parkinglot` folder
3. **Add at least 2 additional features** from the Additional Requirements section
4. **Write tests** for any new features you implement

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Building the Project
```bash
mvn clean compile
```

### Running Tests
```bash
mvn test
```

### Running with Coverage Report
```bash
mvn clean test jacoco:report
```

## Expected Usage Example

Once you complete the implementation, your code should work like this:

```java
// Create a parking lot with 2 small, 3 medium, and 2 large spaces
ParkingLotService parkingLot = new ParkingLotService(2, 3, 2);

// Park vehicles
Vehicle motorcycle = new Motorcycle("MC001");
Vehicle car = new Car("CAR001");
Vehicle truck = new Truck("TRUCK001");

ParkingTicket motorcycleTicket = parkingLot.parkVehicle(motorcycle);
ParkingTicket carTicket = parkingLot.parkVehicle(car);
ParkingTicket truckTicket = parkingLot.parkVehicle(truck);

// Check available spaces
Map<SpaceSize, Integer> availableSpaces = parkingLot.getAvailableSpaces();
System.out.println("Available spaces: " + availableSpaces);

// Calculate parking fee
BigDecimal fee = parkingLot.calculateParkingFee(motorcycleTicket.getTicketId());
System.out.println("Parking fee: $" + fee);

// Unpark vehicle
Vehicle unparkedVehicle = parkingLot.unparkVehicle(motorcycleTicket.getTicketId());
System.out.println("Unparked: " + unparkedVehicle.getLicensePlate());
```

## Implementation Requirements

- **Thread-safe operations**: Use appropriate data structures for concurrent access
- **Comprehensive error handling**: Proper exception handling and validation
- **Flexible pricing**: Different hourly rates for different vehicle types
- **Smart space allocation**: Vehicles should be assigned to the smallest suitable space
- **Complete test coverage**: All unit tests must pass

## Hourly Rates
- Motorcycle: $2.00/hour
- Car: $4.00/hour  
- Truck: $6.50/hour

## Time Limit
This project was designed to be completed within **90 minutes** for interview purposes.

## Evaluation Criteria

Your solution will be evaluated based on:

1. **Correctness**: All unit tests pass
2. **Code Quality**: Clean, readable, and maintainable code
3. **Design**: Good use of OOP principles and design patterns
4. **Error Handling**: Appropriate exception handling and validation
5. **Testing**: Comprehensive test coverage for new features
6. **Performance**: Efficient algorithms and data structures
7. **Documentation**: Clear code comments and documentation

## Submission

1. Ensure all tests pass
2. Commit your changes with meaningful commit messages
3. Push to your fork or create a pull request

Good luck!