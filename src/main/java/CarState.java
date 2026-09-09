enum ParkingStatus {
    PARKED,
    UNPARKED
}

public class CarState {
    private int position;
    private ParkingStatus CurrParkingStatus;

    public CarState() {
        this.position = 0;
        this.CurrParkingStatus = ParkingStatus.UNPARKED;
    }

    public CarState(int position, ParkingStatus currParkingStatus) {
        this.position = position;
        this.CurrParkingStatus = currParkingStatus;
    }

    // Getters
    public int getPosition() {
        return position;
    }

    public ParkingStatus getCurrParkingStatus() {
        return CurrParkingStatus;
    }
}