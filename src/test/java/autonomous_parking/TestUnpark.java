package autonomous_parking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestUnpark {
    private static IDataSensor sensor1 = mock(IDataSensor.class);
    private static IDataSensor sensor2 = mock(IDataSensor.class);

    @Test // TC-UP-3
    void UnparkRejectsInvalidPosition() {
        IActuator actuator = mock(IActuator.class);
        AutonomousParking car = new AutonomousParking(sensor1, sensor2, actuator);
        when(actuator.GetPosition()).thenReturn(0); // Outside the parking range

        /* Test for car being below the parking range */
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> car.UnPark());
        assertEquals(exception.getMessage(), "Invalid car position");
    }

    @Test // TC-UP-2
    void UnparkDoesNothingWhenCarIsAlreadyUnparked() {
        IActuator actuator = mock(IActuator.class);
        AutonomousParking car = new AutonomousParking(sensor1, sensor2, actuator);
        when(actuator.GetPosition()).thenReturn(1); // Keep car within parking range

        /* Test the new car position and parking status */
        assertDoesNotThrow(() -> car.UnPark());
        assertEquals(1, car.WhereIs().position); // Car position should be unchanged
        assertEquals(ParkingStatus.UNPARKED, car.currParkingStatus); // Car should still be unparked
    }

    @Test // TC-UP-1
    void UnparkChangesCarStateToUnparkedIfCarWasParked() {
        IActuator actuator = mock(IActuator.class);
        AutonomousParking car = new AutonomousParking(sensor1, sensor2, actuator);
        when(actuator.GetPosition()).thenReturn(1); // Keep car within parking range
        car.currParkingStatus = ParkingStatus.PARKED; // Car is parked

        /* Test the new car position and parking status */
        assertDoesNotThrow(() -> car.UnPark());
        assertEquals(1, car.WhereIs().position); // Car position should be unchanged
        assertEquals(ParkingStatus.UNPARKED, car.currParkingStatus); // Car should still be unparked
    }
}
