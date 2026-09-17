package autonomous_parking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import autonomous_parking.mocks.MockDataSensor;

public class TestMoveForward {
    private IDataSensor sensor1 = new MockDataSensor(new int[] {101, 100, 102, 104, 105});
    private IDataSensor sensor2 = new MockDataSensor(new int[] {103, 100, 101, 99, 98});

    @Test
    void MoveForwardRejectsInvalidPosition() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);
        car.currCarPosition = -10;
        Error error = assertThrows(Error.class, () -> car.MoveForward());
        assertEquals(error.getMessage(), "Invalid car position");

        car.currCarPosition = 501;
        error = assertThrows(Error.class, () -> car.MoveForward());
        assertEquals(error.getMessage(), "Invalid car position");
    }

    @Test
    void MoveForwardRejectsInvalidNumberOfFreeSpots() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);
        car.freeSpotsLength = -1;
        Error error = assertThrows(Error.class, () -> car.MoveForward());
        assertEquals(error.getMessage(), "Invalid free spots length");

        car.freeSpotsLength = 10;
        error = assertThrows(Error.class, () -> car.MoveForward());
        assertEquals(error.getMessage(), "Invalid free spots length");
    }

    @Test
    void MoveForwardIncrementsPositionByOneAndResetFreeSpotsLength() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);
        assertEquals(0, car.currCarPosition);

        // Assume we had already detected free 3m before this
        car.freeSpotsLength = 2;

        FreeSpots freeSpots = assertDoesNotThrow(() -> car.MoveForward());
        assertEquals(1, freeSpots.position); // Car position should be 1
        assertEquals(0, freeSpots.freeSpotsLength); // Detected free spots should be reset.
    }

    @Test
    void MoveForwardIncrementsPositionAndFreeSpotsByOne() {
        AutonomousParking car = new AutonomousParking(
            new MockDataSensor(new int [] {151, 152, 148, 160, 143}),
            new MockDataSensor(new int [] {155, 149, 147, 166, 161})
        );
        assertEquals(0, car.currCarPosition);

        // Assume we had already detected free 3m before this
        car.freeSpotsLength = 2;

        FreeSpots freeSpots = assertDoesNotThrow(() -> car.MoveForward());
        assertEquals(1, freeSpots.position); // Car position should be 1
        assertEquals(3, freeSpots.freeSpotsLength); // Detected free spots should be 3
    }
}
