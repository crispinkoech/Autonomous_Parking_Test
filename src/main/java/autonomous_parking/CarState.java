package autonomous_parking;

public class CarState {
    int position;
    ParkingStatus CurrParkingStatus;

    public CarState(int position, ParkingStatus parkingStatus) {
        this.position = position;
        this.CurrParkingStatus = parkingStatus;
    }

    public int getPosition() {
        return position;
    }

    public ParkingStatus getParkingStatus() {
        return CurrParkingStatus;
    }
}