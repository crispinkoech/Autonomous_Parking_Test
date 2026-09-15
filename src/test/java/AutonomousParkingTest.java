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
        int sum = 0;
        for (int i = 0; i < mockData.length; i++) {
            sum += mockData[i];
        }
        return (int)(sum/mockData.length);
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
        assertEquals(false, parkSts);
        assertEquals(500, Car.currCarPosition);
        assertEquals(ParkingStatus.UNPARKED, Car.currParkingStatus);
    }

    @Test
    void TestWhereIs() {
        AutonomousParking Car = new AutonomousParking(sensor1, sensor2);
        assertEquals(0, Car.currCarPosition);
        assertEquals(ParkingStatus.UNPARKED, Car.currParkingStatus);
    }
}