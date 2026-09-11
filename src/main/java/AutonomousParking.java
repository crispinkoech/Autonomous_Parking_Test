import java.util.ArrayList;

interface AutonomousParkingInterface {
  public CarState MoveForward();
  public int IsEmpty(); 
  public String MoveBackward();
  public String Park();
  public String UnPark();
  public CarState WhereIs();
}

public class AutonomousParking implements AutonomousParkingInterface {
  private int currCarPosition;
  private ParkingStatus currParkingStatus;
  private int[] SensorData1;
  private int[] SensorData2;
  private int deviationThreshold;
  private int freeSpotsDistanceValid;
  private int minSensorValue;
  private int maxSensorValue;
  private int roadMinStretch;
  private int roadMaxStretch;

  public AutonomousParking() {
    this.currCarPosition = 0;
    this.currParkingStatus = ParkingStatus.UNPARKED;
    this.SensorData1 = Constant.SENSOR_DATA1;
    this.SensorData2 = Constant.SENSOR_DATA2;
    this.deviationThreshold = Constant.SENSOR_DEVIATION_THRESHOLD;
    this.freeSpotsDistanceValid = Constant.MIN_SENSOR_DETECTED_FREE_SPOT;
    this.minSensorValue = Constant.SENSOR_MIN_VALUE;
    this.maxSensorValue = Constant.SENSOR_MAX_VALUE;
    this.roadMinStretch = Constant.ROAD_MIN_STRETCH;
    this.roadMaxStretch = Constant.ROAD_MAX_STRETCH;
  }


  /**
   * Description:
   *  - Moves the car forward by 1 meter and checks whether there is an empty space
   *    to the right of the car in the new position.
   *  - If there is free space to the right, the parkingState.freeSpots is
   *    incremented by 1; otherwise, it is reset to zero.
   *
   *    Inputs:
   *      - Queries the car position using the `WhereIs` subroutine
   *      - Checks if there is free space to the right using `IsEmpty` subroutine
   *
   *    Outputs:
   *      - Returns the parkingState (position and freeSpots)
   *
   *    Assumptions:
   *      - This method is called by the `Park()` method only when the detected
   *        freeSpots are not enough to park the car (< 5m).
   *
   * Pre-condition:
   *  - 0 <= carState.position <= 499
   *  - carState.isParked = False
   *  - 0 <= parkingState.freeSpots <= 4
   *
   * Post-condition:
   *  - 1 <= carState.position <= 500
   *  - carState.isParked = False
   *  - 0 <= parkingState.freeSpots <= 5
   *
   * Test-cases:
   *   ______________________________________________________________
   *  | Conditions/Actions                |                         |
   *  |-----------------------------------|-------------------------|
   *  | c1: 0 <= position <= 499          |   T     T     T     F   |
   *  | c2: 0 <= freeSpots <= 4           |   T     T     F     -   |
   *  | c3: isEmpty ?                     |   T     F     -     -   |
   *  |-----------------------------------|-------------------------|
   *  | a1: wrong input/state             |   -     -     X     X   |
   *  | a2: position += 1, freeSpots = 0  |   -     X     -     -   |
   *  | a2: position += 1, freeSpots += 1 |   X     -     -     -   |
   *  |___________________________________|_________________________|
   *
   */
  public CarState MoveForward() {
    CarState currPos = new CarState();
    return currPos;
  }

  /**
Description
Pre-condition:
Post-condition:
Test-cases:
*/

  public int IsEmpty() {
    /* Instantiate sensors */
    DataSensor sensor1 = new DataSensor();
    DataSensor sensor2 = new DataSensor();
    int filteredDataSensor1 = -1;
    int filteredDataSensor2 = -1;
    int filteredData = -1;

    /* Initialize sensors */
    sensor1.SetDataSensor(SensorData1);
    sensor2.SetDataSensor(SensorData2);

    /* Filter noise and check if sensor data is in range */
    boolean isSensor1Valid = sensor1.FilterNoise(deviationThreshold) && sensor1.IsDataInRange(minSensorValue, maxSensorValue);
    boolean isSensor2Valid = sensor2.FilterNoise(deviationThreshold) && sensor2.IsDataInRange(minSensorValue, maxSensorValue);

    /* Calculate filtered data from valid sensors */
    if (isSensor1Valid) {
        filteredDataSensor1 = sensor1.CalculateData();
    }

    if (isSensor2Valid) {
        filteredDataSensor2 = sensor2.CalculateData();
    }

    /* Determine the final filtered data based on valid sensor readings */
    if (filteredDataSensor1 != -1 && filteredDataSensor2 != -1) {
        filteredData = Math.min(filteredDataSensor1, filteredDataSensor2);
    }
    else if (filteredDataSensor1 != -1) {
        filteredData = filteredDataSensor1;
    }
    else if (filteredDataSensor2 != -1) {
        filteredData = filteredDataSensor2;
    }

    /* Return the filtered data, which represents the distance to the nearest obstacle */
    /* If both sensors are invalid, return -1 
       If one sensor is valid, return its data 
       If both are valid, return the minimum of them */
    return filteredData;
  }

/**
Description
Pre-condition:
Post-condition:
Test-cases:
*/
  public String MoveBackward() {
    return "Moving Backward";
  }

/**
Description
Pre-condition:
Post-condition:
Test-cases:
*/
  public String Park() {
    return "Parking";
  }

/**
Description
Pre-condition:
Post-condition:
Test-cases:
*/
  public String UnPark() {
    return "Unparking";
  }

/**
Description
Pre-condition:
Post-condition:
Test-cases:
*/
  public CarState WhereIs() {
    CarState currCarState = new CarState(currCarPosition, currParkingStatus);
    return currCarState;
  }
}
