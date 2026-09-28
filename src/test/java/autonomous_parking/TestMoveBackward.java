package autonomous_parking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;

public class TestMoveBackward {
    private static IDataSensor sensor1 = mock(IDataSensor.class);
    private static IDataSensor sensor2 = mock(IDataSensor.class);

    @BeforeAll 
    static void beforeAll() {
        when(sensor1.GetDataSensor()).thenReturn(new int[] {101, 100, 102, 104, 105});
        when(sensor2.GetDataSensor()).thenReturn(new int[] {103, 100, 101, 99, 98});
    }

    @Test // TC-MB-4
    void MoveBackwardRejectsInvalidPosition() {
        IActuator actuator = mock(IActuator.class);
        AutonomousParking car = new AutonomousParking(sensor1, sensor2, actuator);

        /* Test for the car's position being below the required range */
        when(actuator.GetPosition()).thenReturn(-1);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> car.MoveBackward());
        assertEquals(exception.getMessage(), "Invalid car position");

        /* Test for the car's position being beyond the required range */
        when(actuator.GetPosition()).thenReturn(501);
        exception = assertThrows(IllegalStateException.class, () -> car.MoveBackward());
        assertEquals(exception.getMessage(), "Invalid car position");
    }

    @Test // TC-MB-3
    void MoveBackwardRejectsInvalidParkingState() {
        IActuator actuator = mock(IActuator.class);
        AutonomousParking car = new AutonomousParking(sensor1, sensor2, actuator);
        when(actuator.GetPosition()).thenReturn(1);

        /* Test for the car being in a unwanted parked state */
        car.currParkingStatus = ParkingStatus.PARKED;
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> car.MoveBackward());
        assertEquals(exception.getMessage(), "Car is already parked");
    }

    @Test // TC-MB-2
    void MoveBackwardDecrementsPositionByOneAndResetFreeSpotsLength() {
        IActuator actuator = mock(IActuator.class);
        AutonomousParking car = new AutonomousParking(sensor1, sensor2, actuator);
        when(actuator.GetPosition()).thenReturn(1, 0);

        // Assume we had already detected free 3m before this
        car.freeSpotsLength = 2;

        /* Test that car position decreases and detected free spots resets */
        FreeSpots freeSpots = assertDoesNotThrow(() -> car.MoveBackward());
        assertEquals(0, freeSpots.position); // Car position should be 0
        assertEquals(0, freeSpots.freeSpotsLength); // Detected free spots should be reset.
    }

    @Test // TC-MB-1
    void MoveBackwardDecrementsPositionAndIncrementsFreeSpotsByOne() {
        IActuator actuator = mock(IActuator.class);
        when(actuator.GetPosition()).thenReturn(1, 0);
        // Use a mock sensor that will report the right side to be free
        IDataSensor sensor = mock(IDataSensor.class);
        when(sensor.IsDataInRange()).thenReturn(true);
        when(sensor.FilterNoise()).thenReturn(true);
        when(sensor.CalculateData()).thenReturn(151);

        AutonomousParking car = new AutonomousParking(sensor, sensor, actuator);

        // Assume we had already detected free 3m before this
        car.freeSpotsLength = 2;

        /* Test that car position decreases and detected free spots increases */
        FreeSpots freeSpots = assertDoesNotThrow(() -> car.MoveBackward());
        assertEquals(0, freeSpots.position); // Car position should be 0
        assertEquals(3, freeSpots.freeSpotsLength); // Detected free spots should be 3
    }
}
