package autonomous_parking;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestCheckForMostEfficientFreeSpot {
  private static IDataSensor sensor1 = mock(IDataSensor.class);
  private static IDataSensor sensor2 = mock(IDataSensor.class);

  @Test // TC-CF-4
  void CheckForMostEfficientFreeSpot_TestCase4() {
    AutonomousParking car = new AutonomousParking(sensor1, sensor2, mock(IActuator.class));
    assertEquals(0, car.currMostEfficientFreeSpot.freeSpotsLength);

    // Checking for free spot does nothing
    car.CheckForMostEfficientFreeSpot(0);
    assertEquals(0, car.currMostEfficientFreeSpot.freeSpotsLength);
  }

  @Test // TC-CF-3
  void CheckForMostEfficientFreeSpot_TestCase3() {
    AutonomousParking car = new AutonomousParking(sensor1, sensor2, mock(IActuator.class));
    assertEquals(0, car.currMostEfficientFreeSpot.freeSpotsLength);

    // Suppose we have now tracked 5m free backwards, from pos 10
    car.freeSpotsLength = 5;
    car.CheckForMostEfficientFreeSpot(10);

    // Assert that most effiction spot has been updated.
    assertEquals(10, car.currMostEfficientFreeSpot.position);
    assertEquals(5, car.currMostEfficientFreeSpot.freeSpotsLength);
  }

  @Test // TC-CF-2
  void CheckForMostEfficientFreeSpot_TestCase2() {
    AutonomousParking car = new AutonomousParking(sensor1, sensor2, mock(IActuator.class));
    car.currMostEfficientFreeSpot.position = 7;
    car.currMostEfficientFreeSpot.freeSpotsLength = 7;

    // Suppose we have now tracked 10m free backwards from pos 20
    car.freeSpotsLength = 10;
    car.CheckForMostEfficientFreeSpot(20);

    // Assert that most efficient spot has not changed.
    assertEquals(7, car.currMostEfficientFreeSpot.position);
    assertEquals(7, car.currMostEfficientFreeSpot.freeSpotsLength);
  }

  @Test // TC-CF-1
  void CheckForMostEfficientFreeSpot_TestCase1() {
    AutonomousParking car = new AutonomousParking(sensor1, sensor2, mock(IActuator.class));
    car.currMostEfficientFreeSpot.position = 7;
    car.currMostEfficientFreeSpot.freeSpotsLength = 7;

    // Suppose we have now tracked 5m free backwards from pos 20
    car.freeSpotsLength = 5;
    car.CheckForMostEfficientFreeSpot(20);

    // Assert that most efficient spot has not changed.
    assertEquals(20, car.currMostEfficientFreeSpot.position);
    assertEquals(5, car.currMostEfficientFreeSpot.freeSpotsLength);
  }
}