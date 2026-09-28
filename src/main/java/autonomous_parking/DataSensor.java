package autonomous_parking;

public class DataSensor implements IDataSensor {

    public static final int SENSOR_MIN_VALUE = 0;
    public static final int SENSOR_MAX_VALUE = 200;
    public static final int SENSOR_DEVIATION_THRESHOLD = 80;

    protected int[] sensorData;

    public DataSensor() {
        this.sensorData = new int[] {-1, -1, -1, -1, -1};
    }

    public void Read(int[] sensorData) {
        this.sensorData = sensorData;
    }

    public int[] GetDataSensor() {
        return this.sensorData;
    }

    public boolean IsDataInRange() {
        for (int i = 0; i < this.sensorData.length; i++) {
            if (this.sensorData[i] < SENSOR_MIN_VALUE || this.sensorData[i] > SENSOR_MAX_VALUE) {
                return false;
            }
        }
        return true;
    }

    public boolean FilterNoise() {
        // Implementation for filtering noise from sensor data
        int maxValData = this.sensorData[0];
        int minValData = this.sensorData[0];

        for (int n : this.sensorData) {
            if (n > maxValData) {
                maxValData = n;
            }
            if (n < minValData) {
                minValData = n;
            }
        }

        if ((maxValData - minValData) > SENSOR_DEVIATION_THRESHOLD) {
            return false;
        }
        
        return true;
    }

    public int CalculateData() {

        int sum = 0;

        for (int i = 0; i < this.sensorData.length; i++) {
            sum += this.sensorData[i];
        }

        return (int)(sum/this.sensorData.length);
    }

}
