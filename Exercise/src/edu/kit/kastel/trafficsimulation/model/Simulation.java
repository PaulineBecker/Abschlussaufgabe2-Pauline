package edu.kit.kastel.trafficsimulation.model;

import edu.kit.kastel.trafficsimulation.io.ExecutionState;
import edu.kit.kastel.trafficsimulation.io.Messages;

/**
 * @author uyxib
 * @version 1.0
 */
public class Simulation implements Executable {

    private ExecutionState executionState;
    private StreetNetwork streetNetwork;

    public Simulation(StreetNetwork streetNetwork) {
        this.executionState = executionState.READING;
        this.streetNetwork = streetNetwork;
    }

    /**
     * Returns whether or not this structure is active.
     * @return whether or not this structure is active
     */
    @Override public boolean isActive() {
        return executionState == ExecutionState.RUNNING;
    }

    /**
     * Quits this structure so {@link #isActive()} will return {@code false}.
     */
    @Override public void quit() {
        executionState = ExecutionState.EXITED;
    }

    @Override public boolean isReading() { return executionState == ExecutionState.READING; }

    public String showCar(int carID) {
        return this.streetNetwork.getCurrentCar(carID);
    }

    public String readFiles(String filePath) {
        streetNetwork.readFiles(filePath);
        streetNetwork.init();
        executionState = ExecutionState.RUNNING;
        return Messages.READY.format();
    }

    public String simulate(int numberOfTicks) {
        for (int i = 0; i < numberOfTicks; i++) {
            streetNetwork.update();
        }
        return Messages.READY.format();
    }
}
