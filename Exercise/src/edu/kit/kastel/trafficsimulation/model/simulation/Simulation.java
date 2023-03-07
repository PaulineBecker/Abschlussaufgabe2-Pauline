package edu.kit.kastel.trafficsimulation.model.simulation;

import edu.kit.kastel.trafficsimulation.io.ExecutionState;
import edu.kit.kastel.trafficsimulation.io.Messages;
import edu.kit.kastel.trafficsimulation.model.StreetNetwork;

/**
 * The {@code Simulation} class represents a simulation of a street network.
 * It implements the {@code Simulatable} interface and provides methods about the status of the simulation and to
 * read files, simulate the street network, and show information about a car.
 *
 * @author uyxib
 * @version 1.0
 */
public class Simulation implements Simulatable {

    private ExecutionState executionState;
    private final StreetNetwork streetNetwork;

    /**
     * Constructs a new {@code Simulation} with the specified street network.
     * The Execution State is at first reading the streetNetwork
     * @param streetNetwork the street network to simulate
     */

    public Simulation(StreetNetwork streetNetwork) {
        this.executionState = executionState.READING;
        this.streetNetwork = streetNetwork;
    }

    /**
     * Returns whether this structure is active.
     * @return whether this structure is active
     */
    @Override
    public boolean isActive() {
        return executionState == ExecutionState.RUNNING;
    }

    /**
     * Quits this structure so {@link #isActive()} will return {@code false}.
     */
    @Override
    public void quit() {
        executionState = ExecutionState.EXITED;
    }

    /**
     * Returns whether this simulation is currently reading files.
     * @return whether this simulation is currently reading files
     */
    @Override
    public boolean isReading() { return executionState == ExecutionState.READING; }

    /**
     * Returns the current car with the specified ID.
     * @param carID the ID of the car to show
     * @return the current car with the specified ID
     */
    @Override
    public String showCar(int carID) {
        return this.streetNetwork.getCurrentCar(carID);
    }

    /**
     * Reads files from the specified path, initializes the street network,
     * and starts the simulation.
     * @param filePath the path of the files to read
     * @return a message indicating that the simulation is ready
     */
    @Override
    public String readFiles(String filePath) {
        streetNetwork.readFiles(filePath);
        streetNetwork.init();
        executionState = ExecutionState.RUNNING;
        return Messages.READY.format();
    }

    /**
     * Simulates the street network for the specified number of ticks.
     * @param numberOfTicks the number of ticks to simulate
     * @return a message indicating that the simulation is ready
     */
    @Override
    public String simulate(int numberOfTicks) {
        for (int i = 0; i < numberOfTicks; i++) {
            streetNetwork.update();
        }

        return Messages.READY.format();
    }

}
