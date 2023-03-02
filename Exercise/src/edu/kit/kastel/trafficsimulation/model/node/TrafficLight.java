package edu.kit.kastel.trafficsimulation.model.node;

import edu.kit.kastel.trafficsimulation.model.Updatable;

/**
 * A class representing a traffic light that can be updated.
 * @author uyxib
 * @version 1.0
 */
public class TrafficLight implements Updatable {

    private int remainingGreenTime;
    private final int greenPhaseDuration;

    /**
     * Creates a new TrafficLight object with the specified green phase duration. By initialisation the remaining Green
     * Time is equals the greenPhaseDuration
     * @param greenPhaseDuration the duration of the green phase for the traffic light.
     */

    public TrafficLight(int greenPhaseDuration) {
        this.remainingGreenTime = greenPhaseDuration;
        this.greenPhaseDuration = greenPhaseDuration;
    }

    /**
     * Checks if the green phase of the traffic light is over.
     * If it is over, resets the remaining green time and returns true; otherwise, returns false.
     * @return true if the green phase of the traffic light is over; otherwise, false
     */
    public boolean isGreenPhaseOver() {
        if (remainingGreenTime == 0) {
            resetRemainingGreenTime();
            return true;
        }
        return false;
    }

    /**
     * Updates the traffic light by decreasing the remaining green time if it is greater than zero.
     */
    @Override
    public void update() {
        if (remainingGreenTime > 0) {
            remainingGreenTime--;
        }
    }

    private void resetRemainingGreenTime() {
        remainingGreenTime = greenPhaseDuration;
    }
}
