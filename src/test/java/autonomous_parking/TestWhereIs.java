package autonomous_parking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestWhereIs {
    private static IDataSensor sensor1 = mock(IDataSensor.class);
    private static IDataSensor sensor2 = mock(IDataSensor.class);

    @Test //TC-WI-01
    void WhereIsRejectsInvalidPosition() {
        IActuator actuator = mock(IActuator.class);
        AutonomousParking car = new AutonomousParking(sensor1, sensor2, actuator);
        when(actuator.GetPosition()).thenReturn(-1);
        IllegalStateException error = assertThrows( IllegalStateException.class, () -> car.WhereIs() );
        assertEquals("Invalid car position", error.getMessage());

        when(actuator.GetPosition()).thenReturn(501);
        error = assertThrows( IllegalStateException.class, () -> car.WhereIs() );
        assertEquals("Invalid car position", error.getMessage());
        System.out.println(error.getMessage());
    }

    @Test //TC-WI-02
    void WhereIsReturnsParkedState(){
        IActuator actuator = mock(IActuator.class);
        AutonomousParking car = new AutonomousParking(sensor1, sensor2, actuator);

        when(actuator.GetPosition()).thenReturn(100);
        car.currParkingStatus = ParkingStatus.PARKED;

        CarState state = car.WhereIs();

        assertEquals(100, state.position);
        assertEquals(ParkingStatus.PARKED, state.CurrParkingStatus);
    }

    @Test //TC-WI-03
    void WhereIsReturnsUnparkedState(){
        IActuator actuator = mock(IActuator.class);
        AutonomousParking car = new AutonomousParking(sensor1, sensor2, actuator);

        when(actuator.GetPosition()).thenReturn(100);
        car.currParkingStatus = ParkingStatus.UNPARKED;

        CarState state = car.WhereIs();

        assertEquals(100, state.position);
        assertEquals(ParkingStatus.UNPARKED, state.CurrParkingStatus);
    }
}