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

    // Setters
    public void SetCurrCarPosition(int position) {
        this.position = position;
    }

    public void SetCurrParkingStatus(ParkingStatus currParkingStatus) {
        this.CurrParkingStatus = currParkingStatus;
    }


    // Getters
    public int getCurrPosition() {
        return position;
    }

    public ParkingStatus getCurrParkingStatus() {
        return CurrParkingStatus;
    }
}