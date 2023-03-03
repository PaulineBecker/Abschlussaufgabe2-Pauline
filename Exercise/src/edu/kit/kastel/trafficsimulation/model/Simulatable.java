package edu.kit.kastel.trafficsimulation.model;

/**
 * @author uyxib
 * @verion 1.0
 */
public interface Simulatable extends Executable {

    public String showCar(int objectToShow);
    public String readFiles(String filePath);
    public String simulate(int simulationRounds);
}
