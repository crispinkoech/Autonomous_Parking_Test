package autonomous_parking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestDataSensor {

    @Test // TC_SSG_1
    void TestSensorRead() {
        int[] sensorData1 = {101, 100, 102, 104, 105};
        int[] sensorData2 = {10, 20, 30, 40, 50};

        IDataSensor sensor1 = new DataSensor();
        IDataSensor sensor2 = new DataSensor();

        sensor1.Read(sensorData1);
        sensor2.Read(sensorData2);
        int [] data1 = sensor1.GetDataSensor();
        int [] data2 = sensor2.GetDataSensor();

        assertArrayEquals(new int [] {101, 100, 102, 104, 105}, data1);
        assertArrayEquals(new int []{10, 20, 30, 40, 50}, data2);
    }

    @Test // TC_SSIDR_1
    void TestDataInRange1() {
        int[] sensorData1 = {101, 100, 102, 104, 105};
        int[] sensorData2 = {10, 20, 30, 40, 50};

        IDataSensor sensor1 = new DataSensor();
        IDataSensor sensor2 = new DataSensor();

        sensor1.Read(sensorData1);
        sensor2.Read(sensorData2);

        assertEquals(true,sensor1.IsDataInRange());
        assertEquals(true,sensor2.IsDataInRange());
    }

    @Test // TC_SSIDR_2
    void TestDataInRange2() {
        int[] sensorData1 = {-1, -2, -3, -5, -6};
        int[] sensorData2 = {-1, -2, -3, -5, -6};

        IDataSensor sensor1 = new DataSensor();
        IDataSensor sensor2 = new DataSensor();

        sensor1.Read(sensorData1);
        sensor2.Read(sensorData2);

        assertEquals(false,sensor1.IsDataInRange());
        assertEquals(false,sensor2.IsDataInRange());
    }

    @Test // TC_SSIDR_3
    void TestDataInRange3() {
        int[] sensorData1 = {250, 250, 251, 300, 400};
        int[] sensorData2 = {450, 254, 234, 500, 501};

        IDataSensor sensor1 = new DataSensor();
        IDataSensor sensor2 = new DataSensor();

        sensor1.Read(sensorData1);
        sensor2.Read(sensorData2);

        assertEquals(false,sensor1.IsDataInRange());
        assertEquals(false,sensor2.IsDataInRange());
    }

    @Test // TC_SSFN_1
    void TestFilterNoise1() {
        int[] sensorData1 = {180, 100, 102, 104, 100};
        int[] sensorData2 = {81, 20, 30, 40, 1};

        IDataSensor sensor1 = new DataSensor();
        IDataSensor sensor2 = new DataSensor();

        sensor1.Read(sensorData1);
        sensor2.Read(sensorData2);

        assertEquals(true,sensor1.FilterNoise());
        assertEquals(true,sensor2.FilterNoise());
    }

    @Test // TC_SSFN_2
    void TestFilterNoise2() {
        int[] sensorData1 = {180, 100, 102, 104, 80};
        int[] sensorData2 = {111, 20, 30, 40, 1};

        IDataSensor sensor1 = new DataSensor();
        IDataSensor sensor2 = new DataSensor();

        sensor1.Read(sensorData1);
        sensor2.Read(sensorData2);

        assertEquals(false,sensor1.FilterNoise());
        assertEquals(false,sensor2.FilterNoise());
    }

    @Test // TC_SSFN_3
    void TestFilterNoise3() {
        int[] sensorData1 = {160, 100, 102, 104, 150};
        int[] sensorData2 = {122, 98, 102, 104, 150};

        IDataSensor sensor1 = new DataSensor();
        IDataSensor sensor2 = new DataSensor();

        sensor1.Read(sensorData1);
        sensor2.Read(sensorData2);

        assertEquals(true,sensor1.FilterNoise());
        assertEquals(true,sensor2.FilterNoise());
    }

    @Test // TC_SSCD_1
    void TestCalculateData() {
        int[] sensorData1 = {160, 100, 102, 104, 150};
        int[] sensorData2 = {122, 98, 102, 104, 150};

        IDataSensor sensor1 = new DataSensor();
        IDataSensor sensor2 = new DataSensor();

        sensor1.Read(sensorData1);
        sensor2.Read(sensorData2);

        assertEquals(123,sensor1.CalculateData());
        assertEquals(115,sensor2.CalculateData());
    }
}