package autonomous_parking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import autonomous_parking.mocks.MockDataSensor;

public class TestUnpark {
    private IDataSensor sensor1 = new MockDataSensor(new int[] {});
    private IDataSensor sensor2 = new MockDataSensor(new int[] {});

    @Test
    void UnparkRejectsInvalidPosition() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);
        car.currCarPosition = 0; // Outside the parking range

        Error error = assertThrows(Error.class, () -> car.UnPark());
        assertEquals(error.getMessage(), "Invalid car position");

        car.currCarPosition = 501; // Outside the parking range
        error = assertThrows(Error.class, () -> car.UnPark());
        assertEquals(error.getMessage(), "Invalid car position");
    }

    @Test
    void UnparkDoesNothingWhenCarIsAlreadyUnparked() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);
        car.currCarPosition = 1; // Keep car within parking range

        assertDoesNotThrow(() -> car.UnPark());
        assertEquals(1, car.currCarPosition); // Car position should be unchanged
        assertEquals(ParkingStatus.UNPARKED, car.currParkingStatus); // Car should still be unparked
    }

    @Test
    void UnparkChangesCarStateToUnparkedIfCarWasParked() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);
        car.currCarPosition = 1; // Keep car within parking range
        car.currParkingStatus = ParkingStatus.PARKED; // Car is parked

        assertDoesNotThrow(() -> car.UnPark());
        assertEquals(1, car.currCarPosition); // Car position should be unchanged
        assertEquals(ParkingStatus.UNPARKED, car.currParkingStatus); // Car should still be unparked
    }
}
