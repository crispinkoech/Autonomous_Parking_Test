package autonomous_parking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import autonomous_parking.mocks.MockDataSensor;

public class TestMoveForward {
    private IDataSensor sensor1 = new MockDataSensor(new int[] {101, 100, 102, 104, 105});
    private IDataSensor sensor2 = new MockDataSensor(new int[] {103, 100, 101, 99, 98});

    @Test // TC-MF-4
    void MoveForwardRejectsInvalidPosition() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);

        /* Test for the car's position being below the required range */
        car.currCarPosition = -10;
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> car.MoveForward());
        assertEquals(exception.getMessage(), "Invalid car position");

        /* Test for the car's position being beyond the required range */
        car.currCarPosition = 501;
        exception = assertThrows(IllegalStateException.class, () -> car.MoveForward());
        assertEquals(exception.getMessage(), "Invalid car position");
    }

    @Test // TC-MF-3
    void MoveForwardRejectsInvalidParkingState() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);

        /* Test for the car being in a unwanted parked state */
        car.currParkingStatus = ParkingStatus.PARKED;
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> car.MoveForward());
        assertEquals(exception.getMessage(), "Car is already parked");
    }

    @Test // TC-MF-2
    void MoveForwardIncrementsPositionByOneAndResetFreeSpotsLength() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);
        assertEquals(0, car.currCarPosition);

        // Assume we had already detected free 3m before this
        car.freeSpotsLength = 2;

        /* Test that car position increases and detected free spots resets */
        FreeSpots freeSpots = assertDoesNotThrow(() -> car.MoveForward());
        assertEquals(1, freeSpots.position);
        assertEquals(0, freeSpots.freeSpotsLength);
    }

    @Test // TC-MF-1
    void MoveForwardIncrementsPositionAndFreeSpotsByOne() {
        // Use a mock sensor that will report the right side to be free
        AutonomousParking car = new AutonomousParking(
            new MockDataSensor(new int [] {151, 152, 148, 160, 143}),
            new MockDataSensor(new int [] {155, 149, 147, 166, 161})
        );
        assertEquals(0, car.currCarPosition);

        // Assume we had already detected free 3m before this
        car.freeSpotsLength = 2;

        /* Test that car position increases and detected free spots increases */
        FreeSpots freeSpots = assertDoesNotThrow(() -> car.MoveForward());
        assertEquals(1, freeSpots.position);
        assertEquals(3, freeSpots.freeSpotsLength);
    }
}
