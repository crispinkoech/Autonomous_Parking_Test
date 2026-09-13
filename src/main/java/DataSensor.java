
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

    public boolean IsDataInRange(int minValue, int maxValue) {
        for (int i = 0; i < sensorData.length; i++) {
            if (sensorData[i] < minValue || sensorData[i] > maxValue) {
                return false;
            }
        }
        return true;
    }

    public boolean FilterNoise(int deviationThreshold) {
        // Implementation for filtering noise from sensor data
        int maxValData = sensorData[0];
        int minValData = sensorData[0];

        for (int n : sensorData) {
            if (n > maxValData) {
                maxValData = n;
            }
            if (n < minValData) {
                minValData = n;
            }
        }

        if ((maxValData - minValData) > deviationThreshold){
            return false;
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
