package autonomous_parking;

public class Actuator implements IActuator {

    int currCarPosition;

    public Actuator(){
        this.currCarPosition = 0;
    }

    /**
     * Description:
     *  - Moves the car forward by 1 meter.
     *
     * Inputs:
     *  - Checks the current car's position
     * 
     * Outputs:
     *  - Updates the car's position
     *
     * Pre-condition:
     * - 0 <= carState.position <= 499
     *
     * Post-condition:
     * - 1 <= carState.position <= 500
     * 
     * Test cases:
     *   __________________________________________________
     *  | Conditions/Actions        | TC-ACU-1 | TC-ACU-2 |
     *  |---------------------------|----------|----------|
     *  | c1: 0 <= position <= 499  |   True   |   False  |
     *  |---------------------------|----------|----------|
     *  | a1: wrong input/state     |    -     |    X     |
     *  | a2: position += 1         |    X     |    -     |
     *  |___________________________|__________|_________ |
     * 
    */
    public void UpOneStep(){
        if (currCarPosition < 0 || currCarPosition > 499) {
            throw new IllegalStateException("Invalid car position");
        }
        currCarPosition += 1;
    }

    /**
     * Description:
     *  - Moves the car backwards by 1 meter.
     *
     * Inputs:
     *  - Checks the current car's position
     * 
     * Outputs:
     *  - Updates the car's position
     *
     * Pre-condition:
     * - 1 <= carState.position <= 500
     *
     * Post-condition:
     * - 0 <= carState.position <= 499
     * 
     * Test cases:
     *   __________________________________________________
     *  | Conditions/Actions        | TC-ACD-1 | TC-ACD-2 |
     *  |---------------------------|----------|----------|
     *  | c1: 1 <= position <= 500  |   True   |   False  |
     *  |---------------------------|----------|----------|
     *  | a1: wrong input/state     |    -     |    X     |
     *  | a2: position -= 1         |    X     |    -     |
     *  |___________________________|__________|_________ |
     * 
    */
    public void DownOneStep(){
        if (currCarPosition < 1 || currCarPosition > 500) {
            throw new IllegalStateException("Invalid car position");
        }
        currCarPosition -= 1;
    }

    public int GetPosition() {
        return currCarPosition;
    }
}
