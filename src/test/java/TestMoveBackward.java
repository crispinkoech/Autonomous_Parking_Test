import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestMoveBackward {
    private IDataSensor sensor1 = new MockDataSensor(new int[] {101, 100, 102, 104, 105});
    private IDataSensor sensor2 = new MockDataSensor(new int[] {103, 100, 101, 99, 98});

    @Test
    void MoveBackwardRejectsInvalidPosition() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);
        car.currCarPosition = 0;

        Error error = assertThrows(Error.class, () -> car.MoveBackward());
        assertEquals(error.getMessage(), "Invalid car position");

        car.currCarPosition = 501;
        error = assertThrows(Error.class, () -> car.MoveBackward());
        assertEquals(error.getMessage(), "Invalid car position");
    }

    @Test
    void MoveBackwardRejectsInvalidNumberOfFreeSpots() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);
        car.currCarPosition = 1;

        car.freeSpotsLength = -1;

        Error error = assertThrows(Error.class, () -> car.MoveBackward());
        assertEquals(error.getMessage(), "Invalid free spots length");

        car.freeSpotsLength = 10;
        error = assertThrows(Error.class, () -> car.MoveBackward());
        assertEquals(error.getMessage(), "Invalid free spots length");
    }

    @Test
    void MoveBackwardDecrementsPositionByOneAndResetFreeSpotsLength() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);
        car.currCarPosition = 1;

        // Assume we had already detected free 3m before this
        car.freeSpotsLength = 2;

        FreeSpots freeSpots = assertDoesNotThrow(() -> car.MoveBackward());
        assertEquals(0, freeSpots.position); // Car position should be 1
        assertEquals(0, freeSpots.freeSpotsLength); // Detected free spots should be reset.
    }

    @Test
    void MoveBackwardDecrementsPositionAndIncrementsFreeSpotsByOne() {
        AutonomousParking car = new AutonomousParking(
            new MockDataSensor(new int [] {151, 152, 148, 160, 143}),
            new MockDataSensor(new int [] {155, 149, 147, 166, 161})
        );
        car.currCarPosition = 1;

        // Assume we had already detected free 3m before this
        car.freeSpotsLength = 2;

        FreeSpots freeSpots = assertDoesNotThrow(() -> car.MoveBackward());
        assertEquals(0, freeSpots.position); // Car position should be 1
        assertEquals(3, freeSpots.freeSpotsLength); // Detected free spots should be 3
    }
}
