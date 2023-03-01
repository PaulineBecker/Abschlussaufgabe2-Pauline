package edu.kit.kastel.trafficsimulation.model.node;

import edu.kit.kastel.trafficsimulation.model.Updatable;

/**
 * @author uyxib
 * @version 1.0
 */
public class TrafficLight implements Updatable {

    private int remainingGreenTime;
    private final int greenPhaseDuration;

    public TrafficLight(int greenPhaseDuration) {
        this.remainingGreenTime = greenPhaseDuration;
        this.greenPhaseDuration = greenPhaseDuration;
    }

    public boolean isGreenPhaseOver(){
        if (remainingGreenTime == 0) {
            resetRemainingGreenTime();
            return true;
        }
        return false;
    }

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
