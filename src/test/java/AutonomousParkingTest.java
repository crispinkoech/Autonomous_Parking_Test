import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MockDataSensor implements IDataSensor {
    private int[] mockData;

    public MockDataSensor(int[] mockData) {
        this.mockData = mockData;
    }

    public void Read() {}

    public int[] GetDataSensor() {
        return mockData;
    }

    public boolean IsDataInRange() {
        return true;
    }

    public boolean FilterNoise() {
        return true;
    }

    public int CalculateData() {
        return Arrays.stream(mockData).sum();
    }
}

public class AutonomousParkingTest {
    private IDataSensor sensor1 = new MockDataSensor(
        new int[] {101, 100, 102, 104, 105}
    );
    private IDataSensor sensor2 = new MockDataSensor(
        new int[] {103, 100, 101, 99, 98}
    );

    @Test
    void TestIsEmpty() {
        AutonomousParking Car = new AutonomousParking(sensor1, sensor2);
        int result = Car.IsEmpty();
        assertEquals(100, result);
    }

    @Test
    void TestPark() {
        AutonomousParking Car = new AutonomousParking(sensor1, sensor2);
        boolean parkSts = Car.Park();
        CarState carSts = Car.WhereIs();
        assertEquals(false, parkSts);
        assertEquals(500, carSts.getCurrPosition());
        assertEquals(ParkingStatus.UNPARKED, carSts.getCurrParkingStatus());
    }

    @Test
    void TestWhereIs() {
        AutonomousParking Car = new AutonomousParking(sensor1, sensor2);
        CarState result = Car.WhereIs();
        assertEquals(0, result.getCurrPosition());
        assertEquals(ParkingStatus.UNPARKED, result.getCurrParkingStatus());
    }
}