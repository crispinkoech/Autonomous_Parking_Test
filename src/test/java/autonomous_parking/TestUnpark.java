package autonomous_parking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import autonomous_parking.mocks.MockDataSensor;

public class TestUnpark {
    private IDataSensor sensor1 = new MockDataSensor(new int[] {});
    private IDataSensor sensor2 = new MockDataSensor(new int[] {});

    @Test // TC-UP-3
    void UnparkRejectsInvalidPosition() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);
        car.currCarPosition = 0; // Outside the parking range

        /* Test for car being below the parking range */
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> car.UnPark());
        assertEquals(exception.getMessage(), "Invalid car position");

        /* Test for car being beyond the parking range */
        car.currCarPosition = 501;
        exception = assertThrows(IllegalStateException.class, () -> car.UnPark());
        assertEquals(exception.getMessage(), "Invalid car position");
    }

    @Test // TC-UP-2
    void UnparkDoesNothingWhenCarIsAlreadyUnparked() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);
        car.currCarPosition = 1; // Keep car within parking range

        /* Test the new car position and parking status */
        assertDoesNotThrow(() -> car.UnPark());
        assertEquals(1, car.currCarPosition); // Car position should be unchanged
        assertEquals(ParkingStatus.UNPARKED, car.currParkingStatus); // Car should still be unparked
    }

    @Test // TC-UP-1
    void UnparkChangesCarStateToUnparkedIfCarWasParked() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2);
        car.currCarPosition = 1; // Keep car within parking range
        car.currParkingStatus = ParkingStatus.PARKED; // Car is parked

        /* Test the new car position and parking status */
        assertDoesNotThrow(() -> car.UnPark());
        assertEquals(1, car.currCarPosition); // Car position should be unchanged
        assertEquals(ParkingStatus.UNPARKED, car.currParkingStatus); // Car should still be unparked
    }
}
