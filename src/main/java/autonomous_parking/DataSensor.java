package autonomous_parking;

public class DataSensor implements IDataSensor {

    public static final int SENSOR_MIN_VALUE = 0;
    public static final int SENSOR_MAX_VALUE = 200;
    public static final int SENSOR_DEVIATION_THRESHOLD = 80;

    protected int[] sensorData;

    public DataSensor() {
        this.sensorData = new int[] {-1, -1, -1, -1, -1};
    }

    /**
     * Description:
     * - Stores the provided sensor data as the current sensor data.
     *
     * Inputs:
     * - Sensor data array containing the latest sensor measurements.
     *
     * Outputs:
     * - Updates the internal sensorData array.
     *
     * Pre-condition:
     * - The input sensor data array is provided by the a real sensor.
     *
     * Post-condition:
     * - sensorData contains the provided sensor measurements.
     */
    public void Read(int[] sensorData) {
        this.sensorData = sensorData;
    }

    /**
     * Description:
     * - Returns the current sensor measurements stored in the sensor.
     *
     * Inputs:
     * - None.
     *
     * Outputs:
     * - Returns the current sensorData array.
     *
     * Pre-condition:
     * - sensorData has been initialized.
     *
     * Post-condition:
     * - The current sensorData array is returned without modification.
     *
     * Test-cases (Annotated as TC-1, 2, etc.):
     *
     * | Conditions/Actions              | TC_SSG_1 |
     * |---------------------------------|----------|
     * | c1: sensorData is initialized   | True     |
     * | a1: return sensorData           | X        |
     */
    public int[] GetDataSensor() {
        return this.sensorData;
    }

    /**
     * Description:
     * - Checks whether all sensor measurements are within the valid sensor range.
     * - A sensor value is considered valid when it is between 0 and 200 inclusive.
     *
     * Inputs:
     * - Current sensorData array.
     *
     * Outputs:
     * - Returns true if all sensor measurements are within the valid range.
     * - Returns false if at least one sensor measurement is outside the valid range.
     *
     * Pre-condition:
     * - sensorData contains sensor measurements.
     *
     * Post-condition:
     * - Returns true when every sensor value satisfies:
     *   SENSOR_MIN_VALUE <= sensor value <= SENSOR_MAX_VALUE.
     * - Returns false when at least one sensor value is below the minimum
     *   or above the maximum.
     *
     * Test-cases (Annotated as TC-1, 2, etc.):
     *
     * | Conditions/Actions                                      | TC_SSIDR_1 | TC_SSIDR_2 | TC_SSIDR_3  |
     * |---------------------------------------------------------|------------|------------|-------------|
     * | c1: sensorData < SENSOR_MIN_VALUE                       | False      | True       | False       |
     * | c2: sensorData > SENSOR_MAX_VALUE                       | False      | False      | True        |
     * | a1: return false                                        | -          | X          | X           |
     * | a2: return true                                         | X          | -          | -           |
     *
     * Boundary values:
     * - 0 is considered valid.
     * - 200 is considered valid.
     */

    public boolean IsDataInRange() {
        for (int i = 0; i < this.sensorData.length; i++) {
            if (this.sensorData[i] < SENSOR_MIN_VALUE || this.sensorData[i] > SENSOR_MAX_VALUE) {
                return false;
            }
        }
        return true;
    }

    /**
     * Description:
     * - Determines whether the sensor data is noise or not.
     * - The difference between the maximum and minimum sensor values is used
     *   to detect abnormal noise.
     *
     * Inputs:
     * - Current sensorData array.
     *
     * Outputs:
     * - Returns true if the deviation between the maximum and minimum values
     *   is less than or equal to SENSOR_DEVIATION_THRESHOLD.
     * - Returns false if the deviation is greater than SENSOR_DEVIATION_THRESHOLD.
     *
     * Pre-condition:
     * - sensorData contains sensor measurements.
     *
     * Post-condition:
     * - maxValData contains the maximum value in sensorData.
     * - minValData contains the minimum value in sensorData.
     * - Returns true when:
     *   maxValData - minValData <= SENSOR_DEVIATION_THRESHOLD.
     * - Returns false when:
     *   maxValData - minValData > SENSOR_DEVIATION_THRESHOLD.
     *
     * Test-cases (Annotated as TC-1, 2, etc.):
     *
     * | Conditions/Actions                         | TC_SSFN_1 | TC_SSFN_2 | TC_SSFN_3 |
     * |--------------------------------------------|-----------|-----------|-----------|
     * | c1: maxValData - minValData > 80           | False     | True      | False     |
     * | c2: maxValData - minValData = 80           | True      | -         | False     |
     * | a1: return false                           | -         | X         | -         |
     * | a2: return true                            | X         | -         | X         |
     *
     * Boundary values:
     * - A deviation of 80 is considered valid.
     * - A deviation greater than 80 is considered noise.
     */
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

    /**
     * Description:
     * - Calculates the average value of all sensor measurements.
     *
     * Inputs:
     * - Current sensorData array.
     *
     * Outputs:
     * - Returns the arithmetic mean of all sensor measurements.
     *
     * Pre-condition:
     * - sensorData contains at least one sensor measurement.
     *
     * Post-condition:
     * - The returned value is the integer average of all values in sensorData.
     * - The original sensorData array is not modified.
     *
     * Test-cases (Annotated as TC-1, 2, etc.):
     *
     * | Conditions/Actions                       | TC_SSCD_1 |
     * |------------------------------------------|-----------|
     * | c1: sensorData contains valid values     | True      |
     * | a1: return integer average               | X         |
     *
     * Example:
     * - sensorData = {100, 120, 140, 160, 180}
     * - sum = 700
     * - number of values = 5
     * - returned average = 140
     */
    public int CalculateData() {

        int sum = 0;

        for (int i = 0; i < this.sensorData.length; i++) {
            sum += this.sensorData[i];
        }

        return (int)(sum/this.sensorData.length);
    }

}
