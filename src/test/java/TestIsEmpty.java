import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
class MockDataSensorIsEmpty extends DataSensor {

    int testCount = 0;
    int[][] sensorDataSets;

    public MockDataSensorIsEmpty(int[][] sensorDataSets)
    {
        this.sensorData = sensorDataSets[0];
        this.sensorDataSets = sensorDataSets;
    }

    @Override public void Read() {
        sensorData = new int[] {-1, -1, -1, -1, -1};
        if (testCount < sensorDataSets.length)
        {
            sensorData = sensorDataSets[testCount];
            testCount++;
        }
    }

}
public class TestIsEmpty {
    int[][] sensorData1Sets = {
                            {180, 180, 180, 180, 180},
                            {180, 180, 180, 180, 180},
                            {180, 180, 180, 180, 180},
                            {180, 180, 180, 180, 180},
                            {180, 180, 180, 180, 180},                           
                            };
    int[][] sensorData2Sets = {
                            {180, 180, 180, 180, 180},
                            {180, 180, 180, 180, 180},
                            {180, 180, 180, 180, 180},
                            {1, 1, 1, 1, 1},
                            {180, 180, 180, 180, 180},
                            };


    private IDataSensor sensor1 = new MockDataSensorIsEmpty(sensorData1Sets);
    private IDataSensor sensor2 = new MockDataSensorIsEmpty(sensorData2Sets);

    @Test
    void TestPark() {
        AutonomousParking Car = new AutonomousParking(sensor1, sensor2);
        Car.currCarPosition = 10;
        boolean doPark = Car.Park();
        assertEquals(0, Car.freeSpotsLength);

        assertEquals(-1, sensor1.CalculateData());
        assertEquals(false, doPark);
        assertEquals(500, Car.currCarPosition);
        assertEquals(ParkingStatus.UNPARKED, Car.currParkingStatus);
        assertEquals(5, ((MockDataSensorIsEmpty)sensor1).testCount);

        int[] expected = {180, 180, 180, 180, 180};
        assertArrayEquals(expected, ((MockDataSensorIsEmpty)sensor1).sensorData);
    }
}