package autonomous_parking.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import autonomous_parking.AutonomousParking;
import autonomous_parking.AutonomousParkingInterface;
import autonomous_parking.IActuator;
import autonomous_parking.IDataSensor;
import autonomous_parking.CarState;


class TestSuccessfulPark {
    
    private static final int[] FREE = {180, 180, 180, 180, 180};

    private static final int[] BLOCKED = {100, 100, 100, 100, 100};

    private static final int[] NOISE = {150, 100, 100, 100, 10};

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

        when(sensor.GetDataSensor())
            .thenAnswer(invocation -> {
                int i = index.getAndIncrement();

                if (i >= sequence.length) {
                    return sequence[sequence.length - 1];
                }

                return sequence[i];
            });



        when(sensor.CalculateData(any(int[].class)))
            .thenAnswer(invocation -> {
                int[] data = invocation.getArgument(0);
                return Arrays.stream(data).sum() / data.length;
            });

        when(sensor.FilterNoise(any(int[].class)))
            .thenAnswer(invocation -> {
                int [] data = invocation.getArgument(0);
                return !Arrays.equals(data, NOISE);
            });

        when(sensor.IsDataInRange(any(int[].class)))
            .thenAnswer(invocation -> {
                int[] data = invocation.getArgument(0);
                return !Arrays.equals(data, NOISE);
            });

        return sensor;
    }

    @Test
    void testOneFreeParkingSpot() {

        AtomicInteger position = new AtomicInteger(0);

        IActuator actuator = createMockActuator(position);
        IDataSensor sensor1 = createMockSensor(
            FREE,
            FREE,
            BLOCKED,
            NOISE,
            FREE,
            FREE,
            FREE,
            FREE,
            FREE,
            BLOCKED
        );
        IDataSensor sensor2 = createMockSensor(
            FREE,
            FREE,
            BLOCKED,
            NOISE,
            FREE,
            FREE,
            FREE,
            FREE,
            FREE,
            BLOCKED
        );
        AutonomousParkingInterface car =  new AutonomousParking(sensor1, sensor2, actuator);


        // -------------------------------------------------
        // 1. PARK
        // -------------------------------------------------

        boolean parked = car.Park();

        CarState carState = car.WhereIs();

        assertEquals(true, parked);

        assertEquals(9, carState.getPosition());
    }
}