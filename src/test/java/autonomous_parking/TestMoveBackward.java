package autonomous_parking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import autonomous_parking.mocks.MockDataSensor;

public class TestMoveBackward {
    private IDataSensor sensor1 = new MockDataSensor(new int[] {101, 100, 102, 104, 105});
    private IDataSensor sensor2 = new MockDataSensor(new int[] {103, 100, 101, 99, 98});

    @Test // TC-MB-4
    void MoveBackwardRejectsInvalidPosition() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);
        car.currCarPosition = 0;

        /* Test for the car's position being below the required range */
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> car.MoveBackward());
        assertEquals(exception.getMessage(), "Invalid car position");

        /* Test for the car's position being beyond the required range */
        car.currCarPosition = 501;
        exception = assertThrows(IllegalStateException.class, () -> car.MoveBackward());
        assertEquals(exception.getMessage(), "Invalid car position");
    }

    @Test // TC-MB-3
    void MoveBackwardRejectsInvalidParkingState() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);
        car.currCarPosition = 1;

        /* Test for the car being in a unwanted parked state */
        car.currParkingStatus = ParkingStatus.PARKED;
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> car.MoveBackward());
        assertEquals(exception.getMessage(), "Car is already parked");
    }

    @Test // TC-MB-2
    void MoveBackwardDecrementsPositionByOneAndResetFreeSpotsLength() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);
        car.currCarPosition = 1;

        // Assume we had already detected free 3m before this
        car.freeSpotsLength = 2;

        /* Test that car position decreases and detected free spots resets */
        FreeSpots freeSpots = assertDoesNotThrow(() -> car.MoveBackward());
        assertEquals(0, freeSpots.position); // Car position should be 1
        assertEquals(0, freeSpots.freeSpotsLength); // Detected free spots should be reset.
    }

    @Test // TC-MB-1
    void MoveBackwardDecrementsPositionAndIncrementsFreeSpotsByOne() {
        // Use a mock sensor that will report the right side to be free
        AutonomousParking car = new AutonomousParking(
            new MockDataSensor(new int [] {151, 152, 148, 160, 143}),
            new MockDataSensor(new int [] {155, 149, 147, 166, 161})
        );
        car.currCarPosition = 1;

        // Assume we had already detected free 3m before this
        car.freeSpotsLength = 2;

        /* Test that car position decreases and detected free spots increases */
        FreeSpots freeSpots = assertDoesNotThrow(() -> car.MoveBackward());
        assertEquals(0, freeSpots.position); // Car position should be 1
        assertEquals(3, freeSpots.freeSpotsLength); // Detected free spots should be 3
    }
}
