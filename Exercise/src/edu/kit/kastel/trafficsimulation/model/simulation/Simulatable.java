package edu.kit.kastel.trafficsimulation.model.simulation;


/**
 * The Simulatable interface provides a set of methods to simulate a traffic simulation.
 * It extends the Executable interface and includes the following methods:
 *
 * @author uyxib
 * @version 1.0
 */
public interface Simulatable extends Executable {

    /**
     * Displays the details of a specified object.
     * @param objectToShow the index of the object to display.
     * @return a String containing the details of the specified object.
     */
    String showCar(int objectToShow);

    /**
     * Reads data from a file.
     * @param filePath the path of the file to read.
     * @return a String containing the contents of the file.
     */
    String readFiles(String filePath);

    /**
     * Runs a simulation for a specified number of rounds.
     * @param simulationRounds the number of rounds to run the simulation for.
     * @return a String containing if the simulation was successful and if the simulation is done
     */
    String simulate(int simulationRounds);
}
