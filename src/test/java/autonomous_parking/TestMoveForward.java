package autonomous_parking;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestMoveForward {
    private static IDataSensor sensor1 = mock(IDataSensor.class);
    private static IDataSensor sensor2 = mock(IDataSensor.class);

    @BeforeAll 
    static void beforeAll() {
        when(sensor1.GetDataSensor()).thenReturn(new int[] {101, 100, 102, 104, 105});
        when(sensor2.GetDataSensor()).thenReturn(new int[] {103, 100, 101, 99, 98});
    }

    @Test // TC-MF-6
    void MoveForwardTestCase6() {
        IActuator actuator = mock(IActuator.class);
        AutonomousParking car = new AutonomousParking(sensor1, sensor2, actuator);

        /* Test for the car's position being below the required range */
        when(actuator.GetPosition()).thenReturn(-10);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> car.MoveForward());
        assertEquals(exception.getMessage(), "Invalid car position");

        /* Test for the car's position being beyond the required range */
        when(actuator.GetPosition()).thenReturn(501);
        exception = assertThrows(IllegalStateException.class, () -> car.MoveForward());
        assertEquals(exception.getMessage(), "Invalid car position");
    }

    @Test // TC-MF-5
    void MoveForwardTestCase5() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2, mock(IActuator.class));

        /* Test for the car being in a unwanted parked state */
        car.currParkingStatus = ParkingStatus.PARKED;
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> car.MoveForward());
        assertEquals(exception.getMessage(), "Car is already parked");
    }

    @Test // TC-MF-4
    void MoveForwardTestCase4() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2, new Actuator());

        // Assume we had already detected free 2m before this
        car.freeSpotsLength = 2;

        /* Test that car position increases and detected free spots resets */
        FreeSpots freeSpots = assertDoesNotThrow(() -> car.MoveForward());
        assertEquals(1, freeSpots.position);
        assertEquals(0, freeSpots.freeSpotsLength);
    }

    @Test // TC-MF-3
    void MoveForwardTestCase3() {
        /* Test moving to the end of the street */
        IActuator actuator = mock(IActuator.class);
        when(actuator.GetPosition()).thenReturn(499, 500);

        AutonomousParking car = spy(new AutonomousParking(sensor1, sensor2, actuator));

        /* Test that car position increases and detected free spots resets*/
        FreeSpots freeSpots = assertDoesNotThrow(() -> car.MoveForward());
        assertEquals(500, freeSpots.position);
        assertEquals(0, freeSpots.freeSpotsLength);
        /* Test that we checked for efficient spots at the last position */
        verify(car).checkForMostEfficientFreeSpot(499);
    }

    @Test // TC-MF-2
    void MoveForwardTestCase2() {
        /* Use a mock sensor that will report the right side to be free */
        IDataSensor sensor = mock(IDataSensor.class);
        when(sensor.FilterNoise(any())).thenReturn(true);
        when(sensor.IsDataInRange(any())).thenReturn(true);
        when(sensor.CalculateData(any())).thenReturn(155);

        IActuator actuator = mock(IActuator.class);
        when(actuator.GetPosition()).thenReturn(0, 1);
        AutonomousParking car = new AutonomousParking(sensor, sensor, actuator);

        // Assume we had already detected free 2m before this
        car.freeSpotsLength = 2;

        /* Test that car position increases and detected free spots increases */
        FreeSpots freeSpots = assertDoesNotThrow(() -> car.MoveForward());
        assertEquals(1, freeSpots.position);
        assertEquals(3, freeSpots.freeSpotsLength);
    }

    @Test // TC-MF-1
    void MoveForwardTestCase1() {
        /* Use a mock sensor that will report the right side to be free */
        IDataSensor sensor = mock(IDataSensor.class);
        when(sensor.FilterNoise(any())).thenReturn(true);
        when(sensor.IsDataInRange(any())).thenReturn(true);
        when(sensor.CalculateData(any())).thenReturn(155);

        /* Test moving to the end of the street */
        IActuator actuator = mock(IActuator.class);
        when(actuator.GetPosition()).thenReturn(499, 500);

        AutonomousParking car = spy(new AutonomousParking(sensor, sensor, actuator));

        // Assume we had already detected free 2m before this
        car.freeSpotsLength = 2;

        /* Test that car position increases and detected free spots increases */
        FreeSpots freeSpots = assertDoesNotThrow(() -> car.MoveForward());
        assertEquals(500, freeSpots.position);
        assertEquals(3, freeSpots.freeSpotsLength);
        /* Test that we checked for efficient spots at the last position */
        verify(car).checkForMostEfficientFreeSpot(500);
    }
}
