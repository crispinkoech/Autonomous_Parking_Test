enum ParkingStatus {
    PARKED,
    UNPARKED
}

public class CarState {
    private int position;
    private ParkingStatus CurrParkingStatus;
    private boolean IsFreeParkingLotDetected;

    public CarState() {
        this.position = 0;
        this.CurrParkingStatus = ParkingStatus.UNPARKED;
        this.IsFreeParkingLotDetected = false;
    }

    // Setters
    public void SetCurrCarPosition(int position) {
        this.position = position;
    }

    public void SetCurrParkingStatus(ParkingStatus currParkingStatus) {
        this.CurrParkingStatus = currParkingStatus;
    }

    public void SetFreeParkingLotStatus(boolean IsFreeParkingLotDetected) {
        this.IsFreeParkingLotDetected = IsFreeParkingLotDetected;
    }

    // Getters
    public int getCurrPosition() {
        return position;
    }

    public ParkingStatus getCurrParkingStatus() {
        return CurrParkingStatus;
    }

    public boolean getFreeParkingLotDetectedSts() {
        return IsFreeParkingLotDetected;
    }
}