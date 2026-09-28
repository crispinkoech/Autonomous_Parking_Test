package autonomous_parking.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import autonomous_parking.AutonomousParking;
import autonomous_parking.AutonomousParkingInterface;
import autonomous_parking.IActuator;
import autonomous_parking.IDataSensor;
import autonomous_parking.CarState;


class IT_Park {
    
    private static final int[] FREE = {180, 180, 180, 180, 180};

    private static final int[] BLOCKED = {100, 100, 100, 100, 100};

    private static final int[] NOISE = {150, 100, 100, 100, 10};

    private static final int[] OUTOFRANGE = {-1, 220, 10, 500, 20};


    private IActuator createMockActuator(AtomicInteger position) 
    {

        IActuator actuator = mock(IActuator.class);

        when(actuator.GetPosition()).thenAnswer(invocation -> position.get());

        doAnswer(invocation -> {
            position.incrementAndGet();
            return null;
        }).when(actuator).UpOneStep();

        doAnswer(invocation -> {
            position.decrementAndGet();
            return null;
        }).when(actuator).DownOneStep();

        return actuator;
    }

    private IDataSensor createMockSensor(int[]... sequence) 
    {
        IDataSensor sensor = mock(IDataSensor.class);
            
        AtomicInteger index = new AtomicInteger(0);

        int[][] currentData = {sequence[0]};

        when(sensor.GetDataSensor())
            .thenAnswer(invocation -> {
                int i = index.getAndIncrement();

                if (i < sequence.length) {
                    return currentData[0] = sequence[i];
                }

                return currentData[0];
            });



        when(sensor.CalculateData())
            .thenAnswer(invocation -> {
                int[] data = currentData[0];
                return Arrays.stream(data).sum() / data.length;
            });

        when(sensor.FilterNoise())
            .thenAnswer(invocation -> {
                int [] data = currentData[0];
                return !Arrays.equals(data, NOISE);
            });

        when(sensor.IsDataInRange())
            .thenAnswer(invocation -> {
                int[] data = currentData[0];
                return !Arrays.equals(data, OUTOFRANGE);
            });

        return sensor;
    }

    private int [][] createRoadConditions(
        int [][] free,
        int [][] noise,
        int [][] ofoutrange
        )
    {
        int roadLength = 500;
        int[][] roadConditions = new int[roadLength][5];

        // Default everything to BLOCKED
        for (int i = 0; i < roadLength; i++) {
            roadConditions[i] = BLOCKED.clone();
        }

        // FREE ranges
        for (int[] range : free) {
            for (int pos = range[0]; pos <= range[1]; pos++) {
                roadConditions[pos - 1] = FREE.clone();
            }
        }

        // NOISE ranges
        for (int[] range : noise) {
            for (int pos = range[0]; pos <= range[1]; pos++) {
                roadConditions[pos - 1] = NOISE.clone();
            }
        }

        // of out range ranges
        for (int[] range : ofoutrange) {
            for (int pos = range[0]; pos <= range[1]; pos++) {
                roadConditions[pos - 1] = OUTOFRANGE.clone();
            }
        }

        return roadConditions;
    }

	@Test
    void testOneFreeParkingSpot() {

        AtomicInteger position = new AtomicInteger(0);

        IActuator actuator = createMockActuator(position);

        int[][] road = createRoadConditions(
            new int[][] { // Free blocks
                {1, 2}, // 2
                {4, 5}, // 2
                {155, 159}, // 5
            },
            new int[][] { // Noise blocks
                {11, 12}
            },
            new int[][] { // Out of range blocks
                {20, 25}
            }
        );

        IDataSensor sensor1 = createMockSensor(road);
        IDataSensor sensor2 = createMockSensor(road);
        AutonomousParkingInterface car =  new AutonomousParking(sensor1, sensor2, actuator);


        // -------------------------------------------------
        // 1. PARK
        // -------------------------------------------------

        boolean parked = car.Park();

        CarState carState = car.WhereIs();

        assertEquals(true, parked);

        assertEquals(159, carState.getPosition());
    }

    @Test
    void testTwoFreeParkingSpot() {

        AtomicInteger position = new AtomicInteger(0);

        IActuator actuator = createMockActuator(position);

        int[][] road = createRoadConditions(
            new int[][] { // Free blocks
                {1, 2}, // 2
                {10, 20}, // 11
                {155, 160}, // 6 -> Choose
            },
            new int[][] { // Noise blocks
                {11, 12}
            },
            new int[][] { // Out of range blocks
                {20, 25}
            }
        );

        IDataSensor sensor1 = createMockSensor(road);
        IDataSensor sensor2 = createMockSensor(road);
        AutonomousParkingInterface car =  new AutonomousParking(sensor1, sensor2, actuator);


        // -------------------------------------------------
        // 1. PARK
        // -------------------------------------------------

        boolean parked = car.Park();

        CarState carState = car.WhereIs();

        assertEquals(true, parked);

        assertEquals(160, carState.getPosition());
    }

    @Test
    void testNoneFreeParkingSpot() {

        AtomicInteger position = new AtomicInteger(0);

        IActuator actuator = createMockActuator(position);

        int[][] road = createRoadConditions(
            new int[][] { // Free blocks
                {1, 2}, // 2
                {20, 23}, // 4
                {155, 158}, // 4
            },
            new int[][] { // Noise blocks
                {11, 12}
            },
            new int[][] { // Out of range blocks
                {20, 25}
            }
        );

        IDataSensor sensor1 = createMockSensor(road);
        IDataSensor sensor2 = createMockSensor(road);
        AutonomousParkingInterface car =  new AutonomousParking(sensor1, sensor2, actuator);


        // -------------------------------------------------
        // 1. PARK
        // -------------------------------------------------

        boolean parked = car.Park();

        CarState carState = car.WhereIs();

        assertEquals(false, parked);

        assertEquals(500, carState.getPosition());
    }
    @Test
    void testSensorOneBroken() {

        AtomicInteger position = new AtomicInteger(0);

        IActuator actuator = createMockActuator(position);

        int[][] road_2 = createRoadConditions(
            new int[][] { // Free blocks
                {1, 2}, // 2
                {20, 23}, // 4
                {155, 159}, // 5
            },
            new int[][] { // Noise blocks
                {11, 12}
            },
            new int[][] { // Out of range blocks
                {20, 25}
            }
        );

        int[][] road_1 = createRoadConditions(
            new int[][] { // Free blocks
                {1, 2}, // 2
            },
            new int[][] { // Noise blocks
                {1, 2}, // 2
                {20, 23}, // 4
                {155, 159}, // 4
            },
            new int[][] { // Out of range blocks
                {20, 25}
            }
        );

        IDataSensor sensor1 = createMockSensor(road_1);
        IDataSensor sensor2 = createMockSensor(road_2);
        AutonomousParkingInterface car =  new AutonomousParking(sensor1, sensor2, actuator);


        // -------------------------------------------------
        // 1. PARK
        // -------------------------------------------------

        boolean parked = car.Park();

        CarState carState = car.WhereIs();

        assertEquals(true, parked);

        assertEquals(159, carState.getPosition());
    }
    @Test
    void testSensorTwoBroken() {

        AtomicInteger position = new AtomicInteger(0);

        IActuator actuator = createMockActuator(position);

        int[][] road_1 = createRoadConditions(
            new int[][] { // Free blocks
                {1, 2}, // 2
                {20, 23}, // 4
                {99, 110}, // 12
            },
            new int[][] { // Noise blocks
                {11, 12}
            },
            new int[][] { // Out of range blocks
                {20, 25}
            }
        );

        int[][] road_2 = createRoadConditions(
            new int[][] { // Free blocks
                {1, 2}, // 2
            },
            new int[][] { // Noise blocks
                {1, 2}, // 2
                {20, 23}, // 4
                {99, 110}, // 12
            },
            new int[][] { // Out of range blocks
                {20, 25}
            }
        );

        IDataSensor sensor1 = createMockSensor(road_1);
        IDataSensor sensor2 = createMockSensor(road_2);
        AutonomousParkingInterface car =  new AutonomousParking(sensor1, sensor2, actuator);


        // -------------------------------------------------
        // 1. PARK
        // -------------------------------------------------

        boolean parked = car.Park();

        CarState carState = car.WhereIs();

        assertEquals(true, parked);

        assertEquals(110, carState.getPosition());
    }
    @Test
    void testBothSensorsBroken() {

        AtomicInteger position = new AtomicInteger(0);

        IActuator actuator = createMockActuator(position);

        int[][] road_1 = createRoadConditions(
            new int[][] { // Free blocks
                {1, 2}, // 2
            },
            new int[][] { // Noise blocks
                {1, 2}, // 2
                {20, 23}, // 4
                {99, 110}, // 12
            },
            new int[][] { // Out of range blocks
                {20, 25}
            }
        );

        int[][] road_2 = createRoadConditions(
            new int[][] { // Free blocks
                {1, 2}, // 2
            },
            new int[][] { // Noise blocks
                {1, 2}, // 2
                {20, 23}, // 4
                {99, 110}, // 12
            },
            new int[][] { // Out of range blocks
                {20, 25}
            }
        );

        IDataSensor sensor1 = createMockSensor(road_1);
        IDataSensor sensor2 = createMockSensor(road_2);
        AutonomousParkingInterface car =  new AutonomousParking(sensor1, sensor2, actuator);


        // -------------------------------------------------
        // 1. PARK
        // -------------------------------------------------

        boolean parked = car.Park();

        CarState carState = car.WhereIs();

        assertEquals(false, parked);

        assertEquals(500, carState.getPosition());
    }
}