import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutonomousParkingTest {

    @Test
    void shouldAddTwoNumbers() {

        AutonomousParking calculator = new AutonomousParking();

        int result = calculator.add(2, 3);

        assertEquals(6, result);
    }
}