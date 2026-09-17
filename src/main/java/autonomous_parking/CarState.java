package autonomous_parking;

enum ParkingStatus {
    PARKED,
    UNPARKED
}

public class CarState {
    int position;
    ParkingStatus CurrParkingStatus;

    public CarState(int position, ParkingStatus parkingStatus) {
        this.position = position;
        this.CurrParkingStatus = parkingStatus;
    }
}