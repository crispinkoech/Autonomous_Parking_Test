package autonomous_parking;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestActuator {
    @Test // TC-ACU-2
    void ActuatorTestCaseACU2() {
        Actuator actuator = new Actuator();

        /* Test for the car's position being below the required range */
        actuator.currCarPosition = -1;
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> actuator.UpOneStep());
        assertEquals(exception.getMessage(), "Invalid car position");

        /* Test for the car's position being beyond the required range */
        actuator.currCarPosition = 500;
        exception = assertThrows(IllegalStateException.class, () -> actuator.UpOneStep());
        assertEquals(exception.getMessage(), "Invalid car position");
    }

    @Test // TC-ACU-1
    void ActuatorTestCaseACU1() {
        Actuator actuator = new Actuator();

        actuator.UpOneStep();

        /* Confirm position has been incremented */
        assertEquals(actuator.GetPosition(), 1);
    }

    @Test // TC-ACD-2
    void ActuatorTestCaseACD2() {
        Actuator actuator = new Actuator();

        /* Test for the car's position being below the required range */
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> actuator.DownOneStep());
        assertEquals(exception.getMessage(), "Invalid car position");

        /* Test for the car's position being beyond the required range */
        actuator.currCarPosition = 501;
        exception = assertThrows(IllegalStateException.class, () -> actuator.DownOneStep());
        assertEquals(exception.getMessage(), "Invalid car position");
    }

    @Test // TC-ACD-1
    void ActuatorTestCaseACD1() {
        Actuator actuator = new Actuator();

        actuator.currCarPosition = 50;
        actuator.DownOneStep();

        /* Confirm position has been incremented */
        assertEquals(actuator.GetPosition(), 49);
    }
}
