package autonomous_parking;
/**
 * Description:
 * - Represents the current parking status of the vehicle.
 * - The vehicle can either be parked or unparked.
 *
 * Values:
 * - PARKED:
 *   The vehicle is currently parked.
 *
 * - UNPARKED:
 *   The vehicle is currently not parked.
 *
 * Usage:
 * - Used by AutonomousParking to track and validate the current
 *   parking state of the vehicle.
 *
 * State transitions:
 *
 * | Current Status | Action  | Resulting Status |
 * |----------------|---------|------------------|
 * | UNPARKED       | Park()  | PARKED           |
 * | PARKED         | UnPark()| UNPARKED         |
 */
public enum ParkingStatus {
  PARKED,
  UNPARKED
}
