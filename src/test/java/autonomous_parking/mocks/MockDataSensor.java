package autonomous_parking.mocks;

import autonomous_parking.DataSensor;


public class MockDataSensor extends DataSensor {
    public MockDataSensor(int[] mockData) {
        this.sensorData = mockData;
    }

    public void Read() {}
}