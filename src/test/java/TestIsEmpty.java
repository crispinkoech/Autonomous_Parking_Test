import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIsEmpty {
    @Test
    void TestIsEmpty1() {
        AutonomousParking Car = new AutonomousParking();
        int result = Car.IsEmpty();
        assertEquals(100, result);
    }

    @Test
    void TestPark() {
        AutonomousParking Car = new AutonomousParking();
        boolean parkSts = Car.Park();
        CarState carSts = Car.WhereIs();
        assertEquals(false, parkSts);
        assertEquals(500, carSts.getCurrPosition());
        assertEquals(ParkingStatus.UNPARKED, carSts.getCurrParkingStatus());
    }

    @Test
    void TestWhereIs() {
        AutonomousParking Car = new AutonomousParking();
        CarState result = Car.WhereIs();
        assertEquals(0, result.getCurrPosition());
        assertEquals(ParkingStatus.UNPARKED, result.getCurrParkingStatus());
    }
}