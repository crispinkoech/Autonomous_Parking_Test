package autonomous_parking;

public interface IDataSensor {
    public void Read(int[] sensorData);
    public int[] GetDataSensor();
    public boolean IsDataInRange();
    public boolean FilterNoise();
    public int CalculateData();
}
