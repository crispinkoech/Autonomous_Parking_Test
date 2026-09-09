import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutonomousParkingTest {

    @Test
    void TestWhereIs() {

        AutonomousParking Car = new AutonomousParking();
        CarState currPos = Car.WhereIs();
        assertEquals(0, currPos.getPosition());
        assertEquals(ParkingStatus.UNPARKED, currPos.getCurrParkingStatus());
    }

    @Test
    void TestIsEmpty() {
        AutonomousParking SensorData = new AutonomousParking();
        int result = SensorData.IsEmpty();
        assertEquals(200, result);
    }
}