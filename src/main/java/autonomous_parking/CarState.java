package autonomous_parking;

public class CarState {
    int position;
    ParkingStatus CurrParkingStatus;

    /**
     * Description:
     * - Creates a CarState object containing the current position
     *   and parking status of the vehicle.
     *
     * Inputs:
     * - position: Current position of the vehicle.
     * - parkingStatus: Current parking status of the vehicle.
     *
     * Outputs:
     * - Initializes a new CarState object.
     *
     * Pre-condition:
     * - A position and parking status are provided.
     *
     * Post-condition:
     * - position contains the provided vehicle position.
     * - CurrParkingStatus contains the provided parking status.
     */
    public CarState(int position, ParkingStatus parkingStatus) {
        this.position = position;
        this.CurrParkingStatus = parkingStatus;
    }

    /**
     * Description:
     * - Returns the current position of the vehicle.
     *
     * Inputs:
     * - None.
     *
     * Outputs:
     * - Returns the current vehicle position.
     *
     * Pre-condition:
     * - The CarState object has been initialized.
     *
     * Post-condition:
     * - The current position is returned without modification.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Description:
     * - Returns the current parking status of the vehicle.
     *
     * Inputs:
     * - None.
     *
     * Outputs:
     * - Returns the current ParkingStatus.
     *
     * Pre-condition:
     * - The CarState object has been initialized.
     *
     * Post-condition:
     * - The current parking status is returned without modification.
     */
    public ParkingStatus getParkingStatus() {
        return CurrParkingStatus;
    }
}