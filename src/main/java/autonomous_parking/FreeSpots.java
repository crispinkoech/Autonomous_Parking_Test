package autonomous_parking;

public class FreeSpots {
    int position;
    int freeSpotsLength;

    /**
     * Description:
     * - Creates a FreeSpots object containing the position and length
     *   of a current detected free parking spot.
     *
     * Inputs:
     * - position: Position of the car associated with the free spot.
     * - freeSpotsLength: Length of the free parking spot.
     *
     * Outputs:
     * - Initializes a new FreeSpots object.
     *
     * Pre-condition:
     * - position and freeSpotsLength are provided by the caller.
     *
     * Post-condition:
     * - position contains the provided position.
     * - freeSpotsLength contains the provided free spot length.
     */
    public FreeSpots(int position, int freeSpotsLength) {
        this.position = position;
        this.freeSpotsLength = freeSpotsLength;
    }
}
