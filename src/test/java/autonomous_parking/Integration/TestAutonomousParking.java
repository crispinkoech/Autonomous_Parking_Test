package autonomous_parking.Integration;

import java.time.temporal.ValueRange;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import autonomous_parking.AutonomousParking;
import autonomous_parking.DataSensor;
import autonomous_parking.ParkingStatus;
import autonomous_parking.Actuator;

public class TestAutonomousParking {
    private static Actuator actuator = new Actuator();

    private static DataSensor sensor1 = spy(new DataSensor());
    private static DataSensor sensor2 = spy(new DataSensor());

    private static final int[] FREE = {180, 180, 180, 180, 180};
    private static final int[] BLOCKED = {100, 100, 100, 100, 100};
    private static final int[] OUT_OF_RANGE = {-1, 220, 10, 500, 20};


    @BeforeAll
    static void setupParkingStreet() {
        /**
         * Let's set up our free parking stretches as below:
         * - 11 to 13: 3 free spots (not enough)
         * - 101 to 108: 8 spots free (enough)
         * - 254 to 258: 5 spots free (enough)
        */
        ValueRange spot1 = ValueRange.of(11, 13);
        ValueRange spot2 = ValueRange.of(101, 108);
        ValueRange spot3 = ValueRange.of(254, 258);

        /* Pick one sensor (randomly) to return out-of-range-values (from a random position) */
        int randomSensorId = (int) (Math.random() * 2);
        int randomPosition = (int) ((Math.random() * 500) + 1);

        /* Set up mock values for sensor 1 */
        when(sensor1.GetDataSensor()).thenAnswer(invocation -> {
            int position = actuator.GetPosition();
            if (randomSensorId == 0 && position >= randomPosition) {
                /* Use out of range values */
                sensor1.Read(OUT_OF_RANGE);
            }
            else if (spot1.isValidValue(position) || spot2.isValidValue(position) || spot3.isValidValue(position)) {
                sensor1.Read(FREE);
            } else {
                sensor1.Read(BLOCKED);
            }
            return invocation.callRealMethod();
        });

        /* Set up mock values for sensor 2 */
        when(sensor2.GetDataSensor()).thenAnswer(invocation -> {
            int position = actuator.GetPosition();
            if (randomSensorId == 1 && position >= randomPosition) {
                /* Use out of range values */
                sensor2.Read(OUT_OF_RANGE);
            }
            else if (spot1.isValidValue(position) || spot2.isValidValue(position) || spot3.isValidValue(position)) {
                sensor2.Read(FREE);
            } else {
                sensor2.Read(BLOCKED);
            }
            return invocation.callRealMethod();
        });
    }

    @Test
    void testCompleteParkingAndUnparking() {
        AutonomousParking car = new AutonomousParking(sensor1, sensor2, actuator);

        /* Call the parking method */
        assertDoesNotThrow(() -> car.Park());
        assertEquals(car.WhereIs().getParkingStatus(), ParkingStatus.PARKED);
        assertEquals(car.WhereIs().getPosition(), 258);

        /* Test unparking and move to end of street */
        assertDoesNotThrow(() -> car.UnPark());
        assertEquals(car.WhereIs().getParkingStatus(), ParkingStatus.UNPARKED);
        while(car.WhereIs().getPosition() < AutonomousParking.ROAD_MAX_STRETCH) {
            assertDoesNotThrow(() -> car.MoveForward());
        }
        assertEquals(car.WhereIs().getPosition(), AutonomousParking.ROAD_MAX_STRETCH);
    }
}
