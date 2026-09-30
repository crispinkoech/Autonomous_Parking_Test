# Autonomous Parking Test

A Java/Maven project for developing and testing an autonomous parking controller. The project focuses on test-driven development, unit testing, integration testing, mocking, and structural/code coverage for an autonomous parking scenario.

## Project Overview

The system models a car driving along a 500 m road while two sensors monitor the right-hand side for parking space.

The parking controller:

* Moves the car forward or backward in 1 m steps.
* Reads data from two distance sensors.
* Filters invalid/noisy sensor readings.
* Uses the closest valid sensor result to determine whether the space is free.
* Detects continuous free stretches and considers stretches of at least 5 m as parking candidates.
* Selects the most efficient valid parking stretch.
* Moves the car backward to the selected position and changes the parking state to `PARKED`.
* Leaves the car `UNPARKED` at 500 m when no valid parking stretch is found.
* Supports unparking by changing the parking state back to `UNPARKED`.

## Main Components

The production code is located in `src/main/java/autonomous_parking`.

| Component                    | Responsibility                                                      |
| ---------------------------- | ------------------------------------------------------------------- |
| `AutonomousParking`          | Main parking controller and parking logic                           |
| `AutonomousParkingInterface` | Public controller interface                                         |
| `DataSensor`                 | Sensor data storage, range checking, noise filtering, and averaging |
| `IDataSensor`                | Sensor abstraction used by the controller                           |
| `Actuator`                   | Simulated car movement and position                                 |
| `IActuator`                  | Actuator abstraction used by the controller                         |
| `CarState`                   | Current car position and parking status                             |
| `ParkingStatus`              | `PARKED` / `UNPARKED` state                                         |
| `FreeSpots`                  | Position and length of a detected free stretch                      |

### Important System Rules

* Road position: `0 .. 500` m
* Minimum parking stretch: `5` m
* A sensor reading must be within `0 .. 200` cm.
* A sensor data set is rejected when its maximum-to-minimum deviation is greater than `80` cm.
* When both sensors are valid, the minimum calculated distance is used.
* When only one sensor is valid, that sensor's calculated distance is used.
* When both sensors are invalid, `IsEmpty()` returns `-1`.
* A measured distance of at least `150` cm is treated as free space by the parking controller.

## Project Structure

```text
Autonomous_Parking_Test/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── autonomous_parking/
│   │           ├── Actuator.java
│   │           ├── AutonomousParking.java
│   │           ├── AutonomousParkingInterface.java
│   │           ├── CarState.java
│   │           ├── DataSensor.java
│   │           ├── FreeSpots.java
│   │           ├── IActuator.java
│   │           ├── IDataSensor.java
│   │           └── ParkingStatus.java
│   │
│   └── test/
│       └── java/
│           └── autonomous_parking/
│               ├── Integration/
│               │   ├── TestScenario_1.java
│               │   └── TestScenario_2.java
│               │
│               ├── mocks/
│               │   └── MockDataSensor.java
│               │
│               ├── TestCheckForMostEfficientFreeSpot.java
│               ├── TestIsEmpty.java
│               ├── TestMoveBackward.java
│               ├── TestMoveForward.java
│               ├── TestPark.java
│               ├── TestUnpark.java
│               └── TestWhereIs.java
│
├── Documents/
│   ├── Coverage/
│   ├── Design/
│   ├── Personal/
│   ├── Project_Details/
│   ├── Requirements/
│   ├── Git_Basic_Workflow.pptx
│   └── Test_Specification_Template.xlsm
│
├── pom.xml
└── README.md
```

## Testing Strategy

The project contains both **unit tests** and **integration tests**.

### Unit Testing

JUnit 5 tests cover the main behaviors of the parking controller:

* `IsEmpty()`
* `MoveForward()`
* `MoveBackward()`
* `Park()`
* `UnPark()`
* `WhereIs()`
* `CheckForMostEfficientFreeSpot()`

The sensor and actuator dependencies are abstracted behind `IDataSensor` and `IActuator`.

Mockito is used when controlled dependency behavior is required, while dedicated mock sensor implementations are used for scenarios involving ordered sensor data.

### Integration Testing

Integration scenarios are located under:

```text
src/test/java/autonomous_parking/Integration/
```

The integration tests exercise the parking controller through complete sequences of sensor readings and vehicle movements rather than testing an isolated method.

Current integration scenarios include:

* `TestScenario_1`
* `TestScenario_2`

## Test Design Techniques

The project applies several software testing techniques:

* **Equivalence Class Testing**
* **Boundary Value Analysis**
* **Decision Tables**
* **Structural / White-box Testing**
* **Unit Testing**
* **Integration Testing**
* **Mock-based Testing**

Test cases are identified in the source code using identifiers such as:

* `TC_IE` — `IsEmpty()`
* `TC_MF` — `MoveForward()`
* `TC_MB` — `MoveBackward()`
* `TC_P` — `Park()`
* `TC_UP` — `UnPark()`
* `TC_WI` — `WhereIs()`
* `TC_CF` — `CheckForMostEfficientFreeSpot()`

## Sensor Processing

Each sensor provides a set of five distance measurements.

The `DataSensor` class performs three main operations:

1. **Range validation**

   Sensor readings must be within:

   ```text
   0 <= reading <= 200 cm
   ```

2. **Noise filtering**

   A sensor data set is considered invalid when:

   ```text
   maximum reading - minimum reading > 80 cm
   ```

3. **Data calculation**

   Valid sensor data is converted into a single value using the average of the five readings.

The `IsEmpty()` method combines the results of the two sensors:

```text
Sensor 1 valid + Sensor 2 valid
        ↓
    minimum value

Sensor 1 valid + Sensor 2 invalid
        ↓
       Sensor 1

Sensor 1 invalid + Sensor 2 valid
        ↓
       Sensor 2

Sensor 1 invalid + Sensor 2 invalid
        ↓
        -1
```

The parking controller considers a distance of **150 cm or greater** to represent free space.

## Parking Algorithm

The `Park()` method searches the road from the starting position toward 500 m.

The general process is:

```text
Start
  │
  ▼
Move forward by 1 m
  │
  ▼
Check sensor data
  │
  ├── Free ──────────────► Continue detecting free stretch
  │
  └── Blocked ───────────► Evaluate completed free stretch
                              │
                              ▼
                       Valid stretch ≥ 5 m?
                              │
                              ▼
                       Store candidate
  │
  ▼
Reach 500 m
  │
  ├── No valid parking spot ──► Remain UNPARKED
  │
  └── Valid parking spot ─────► Move backward to selected spot
                                      │
                                      ▼
                                   PARKED
```

When multiple valid parking stretches are detected, the controller keeps track of the most efficient candidate according to the current parking-selection logic.

## Requirements, Design, and Reports

Supporting project material is stored in the `Documents` directory.

### Requirements

The requirements and test specifications are stored under:

```text
Documents/Requirements/
```

### Design

The UML/design documentation is stored under:

```text
Documents/Design/
```

### Project Documentation

Project instructions and phase-related documentation are available under:

```text
Documents/Project_Details/
```

### Coverage

Coverage-related reports and resources are available under:

```text
Documents/Coverage/
```

## Technologies

* **Java 21**
* **Maven**
* **JUnit 5**
* **Mockito**
* **JaCoCo**
* **Eclipse / EclEmma**

The Maven configuration and project dependencies are defined in `pom.xml`.

## How to Build and Run Tests

### Prerequisites

Install:

* JDK 21
* Maven 3.x

Verify the installation:

```bash
java -version
mvn -version
```

### Run All Tests

From the project root:

```bash
mvn test
```

### Generate Coverage Report

Run:

```bash
mvn verify
```

JaCoCo generates the coverage report during the Maven `verify` phase.

The generated report is normally available under:

```text
target/site/jacoco/
```

## Development Workflow

A typical development and testing workflow is:

```text
Requirements
     │
     ▼
Test Specification
     │
     ▼
Implementation
     │
     ▼
Unit Tests
     │
     ▼
Integration Tests
     │
     ▼
Coverage Analysis
     │
     ▼
Review & Refinement
```

Production code should be placed under:

```text
src/main/java/
```

Unit and integration tests should be placed under:

```text
src/test/java/
```

## Notes

This repository is a **testing-focused simulation** of an autonomous parking system.

The `Actuator` class represents vehicle movement in the model, while `DataSensor` represents distance-sensor data processing. The project is intended for software testing and does not directly control physical vehicle hardware.

## Repository

[GitHub Repository](https://github.com/tienbk1995/Autonomous_Parking_Test)
