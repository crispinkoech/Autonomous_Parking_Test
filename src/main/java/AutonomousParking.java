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

  private int currCarPosition = 0;
  private ParkingStatus currParkingStatus = ParkingStatus.UNPARKED;
  private int[] SensorData1 = {101, 100, 102, 104, 105};
  private int[] SensorData2 = {103, 100, 101, 99, 98};
  private int deviationThreshold = 80;

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
    DataSensor sensor1 = new DataSensor();
    DataSensor sensor2 = new DataSensor();
    int filteredDataSensor1 = -1;
    int filteredDataSensor2 = -1;
    int filteredData = -1;

    sensor1.SetDataSensor(SensorData1);
    sensor2.SetDataSensor(SensorData2);

    boolean isSensor1Valid = sensor1.FilterNoise(deviationThreshold);
    boolean isSensor2Valid = sensor2.FilterNoise(deviationThreshold);

    if (isSensor1Valid) {
        sensor1.CalculateData();
        filteredDataSensor1 = sensor1.GetDataSensor()[0];
    }

    if (isSensor2Valid) {
        sensor2.CalculateData();
        filteredDataSensor2 = sensor2.GetDataSensor()[0];
    }

    if (filteredDataSensor1 != -1 && filteredDataSensor2 != -1) {
        filteredData = Math.min(filteredDataSensor1, filteredDataSensor2);
    }
    else if (filteredDataSensor1 != -1) {
        filteredData = filteredDataSensor1;
    }
    else if (filteredDataSensor2 != -1) {
        filteredData = filteredDataSensor2;
    }

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
   * Description:
   *  - Moves the car forward (of the start of the 5m parking stretch) and to the left
   *    of the parking spot.
   *  - If the car is already unparked, the above functionality is skipped.
   * 
   *    Inputs:
   *      - Queries the car's parked state.
   *
   *    Outputs:
   *      - Modifies the car's parked state (if the car is parked).
   *
   *    Assumption:
   *      - The car's length is less than 5m, such that the extra space allows
   *        for wiggle room for the car to park or unpark.
   *
   * Pre-condition:
   *  - 1 <= carState.position <= 500
   *
   * Post-condition:
   *  - carState.isParked = False
   *  - carState.position remains unchanged.
   *
   * Test-cases:
   *   _________________________________________________________________________
   *  | Conditions/Actions                                      |               |
   *  |---------------------------------------------------------|---------------|
   *  | c1: 1 <= position <= 500                                |   T   T   F   |
   *  | c1: car.isParked ?                                      |   T   F   -   |
   *  |---------------------------------------------------------|---------------|
   *  | a1: wrong input/state                                   |   -   -   X   |
   *  | a2: do nothing                                          |   -   X   -   |
   *  | a3: carState.isParked = False, car.position is the same |   X   -   -   |
   *  |_________________________________________________________|_______________|
   *
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
