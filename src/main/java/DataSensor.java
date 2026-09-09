
public class DataSensor {

    private int[] sensorData;

    public DataSensor() {
        this.sensorData = new int[] {-1, -1, -1, -1, -1};
    }

    public void SetDataSensor(int[] sensorData) {
        this.sensorData = sensorData;
    }

    public int[] GetDataSensor() {
        return sensorData;
    }

    public boolean FilterNoise(int deviationThreshold) {
        // Implementation for filtering noise from sensor data
        for (int i = 0; i < sensorData.length - 1; i++) {
            if ((sensorData[i] == -1 )||(Math.abs(sensorData[i] - sensorData[i+1]) > deviationThreshold)) {
                    return false;
                }
            }
        return true;
    }

    public int CalculateData() {

        int sum = 0;

        for (int i = 0; i < sensorData.length; i++) {
            sum += sensorData[i];
        }

        return (int)(sum/sensorData.length);
    }

}
