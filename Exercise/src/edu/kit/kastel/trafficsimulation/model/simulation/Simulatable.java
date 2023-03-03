package edu.kit.kastel.trafficsimulation.model.simulation;

import edu.kit.kastel.trafficsimulation.model.simulation.Executable;

/**
 * @author uyxib
 * @verion 1.0
 */
public interface Simulatable extends Executable {

    public String showCar(int objectToShow);
    public String readFiles(String filePath);
    public String simulate(int simulationRounds);
}
