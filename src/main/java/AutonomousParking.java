interface AutonomousParkingInterface {
  public FreeSpots MoveForward();
  public int IsEmpty(); 
  public FreeSpots MoveBackward();
  public boolean Park();
  public String UnPark();
  public CarState WhereIs();
}

public class AutonomousParking implements AutonomousParkingInterface {
  
    /* Constants */
    public static final int ROAD_MIN_STRETCH = 0;
    public static final int ROAD_MAX_STRETCH = 500;
    public static final int PARKING_SPOT_LENGTH = 5;
    public static final int MIN_SENSOR_DETECTED_FREE_SPOT = 150;
  
  /* Car and parking lot status */
  private int currCarPosition;
  private ParkingStatus currParkingStatus;
  private int freeSpotsLength;
  CarState currCarState;

  /* Sensors */
  private IDataSensor sensor1;
  private IDataSensor sensor2;

  /* Set sensors and initial car/parking state */
  public AutonomousParking(IDataSensor sensor1, IDataSensor sensor2) {
    this.sensor1 = sensor1;
    this.sensor2 = sensor2;

    this.currCarPosition = 0;
    this.currParkingStatus = ParkingStatus.UNPARKED;
    this.freeSpotsLength = 0;
    this.currCarState = new CarState();
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
  public FreeSpots MoveForward() {
    // TODO: Implement MoveForward()
    return new FreeSpots(currCarPosition, freeSpotsLength);
  }

  /**
   * Description:
   * - Query both sensors at least 5 times
   * - Filter noise from each of sensor
   * - Return the distance to the nearest object on the right-hand side of the car
   *
   * Inputs:
   * - int[] SENSOR_DATA1 = {data_1, data_2, data_3, data_4, data_5};
   * - int[] SENSOR_DATA2 = {data_1, data_2, data_3, data_4, data_5};
   *
   * Outputs:
   * - Return a filtered distance value in centimetres
   *
   * Assumptions:
   * - Sensor data will be given as an array of 5 elements
   * - A sensor is considered invalid if its readings have a deviation greater than 80 cm:
   *   + deviation =  Max reading - Min reading
   * - Method to calculate overall sensor data is an average of 5 times
   *
   * Pre-condition:
   * - Both sensors data are available
   * - Sensor data range must be >= 0 and <= 200
   * - The sensors can be queried at least 5 times.
   *
   * Post-condition:
   * - If both sensors are invalid, return -1 
   * - If one sensor is valid, return its filtered data 
   * - If both are valid, return the minimum filtered data of them
   *
   * Test-cases:
   * - Boundary Values
   * - Equivalence Classes
   * - Decision Tables
   * - (Refer to the Test_Specification.xlsm for more details)
   */

  public int IsEmpty() {
    int filteredDataSensor1 = -1;
    int filteredDataSensor2 = -1;
    int filteredData = -1;

    /* Read data from sensors */
    sensor1.Read();
    sensor2.Read();

    /* Filter noise and check if sensor data is in range */
    boolean isSensor1Valid = sensor1.FilterNoise() && sensor1.IsDataInRange();
    boolean isSensor2Valid = sensor2.FilterNoise() && sensor2.IsDataInRange();

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
   * Description:
   * - Moves the car backwards by 1 meter
   * - Checks whether there is empty space to the right of the car
   * - If there is free space, incremented freeSpots by 1; otherwise reset it to zero.
   * 
   * Inputs:
   * - Queries the car position using `WhereIs` method
   * - Queries for free space to the right using `IsEmpty` method
   * 
   * Outputs:
   * - Returns the parking state of the car (position and freeSpots detected)
   *
   * Pre-condition:
   * - 2 <= position <= 500
   * - isParked = False
   * - 0 <= freeSpots <= 4
   *
   * Post-condition:
   * - 1 <= postion <= 499
   * - isParked = False
   * - 0 <= freeSpots <= 5
   * 
   * Test-cases:
   *   ______________________________________________________________
   *  | Conditions/Actions                |                         |
   *  |-----------------------------------|-------------------------|
   *  | c1: 2 <= position <= 500          |   T     T     T     F   |
   *  | c2: 0 <= freeSpots <= 4           |   T     T     F     -   |
   *  | c3: isEmpty ?                     |   T     F     -     -   |
   *  |-----------------------------------|-------------------------|
   *  | a1: wrong input/state             |   -     -     X     X   |
   *  | a2: position -= 1, freeSpots = 0  |   -     X     -     -   |
   *  | a2: position -= 1, freeSpots += 1 |   X     -     -     -   |
   *  |___________________________________|_________________________|
  */
  public FreeSpots MoveBackward() {
    // TODO: Implement MoveBackward()
    return new FreeSpots(currCarPosition, freeSpotsLength);
  }

/**
Description
Pre-condition:
Post-condition:
Test-cases:
*/
  public boolean Park() {
    /*Keep moving forward until getting a free spot or reaching a upper road stretch limit */
    while ((false == currCarState.getFreeParkingLotDetectedSts()) && (currCarState.getCurrPosition() < 500))
    {
      this.MoveForward(); // move 1m ahead and update car status
    }

    /*Could not find a free spot at the end of upper road stretch limit */
    if ((false == currCarState.getFreeParkingLotDetectedSts()) && (currCarState.getCurrPosition() >= 500))
    {
      currCarState.SetCurrParkingStatus(ParkingStatus.UNPARKED);
      return false;
    }

    /*Otherwise Park successfully */
    currCarState.SetCurrParkingStatus(ParkingStatus.PARKED);
    return true;
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
    return this.currCarState;
  }
}
