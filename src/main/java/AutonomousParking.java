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
Description
Pre-condition:
Post-condition:
Test-cases:
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
