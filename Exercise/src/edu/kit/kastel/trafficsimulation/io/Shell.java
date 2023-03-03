package edu.kit.kastel.trafficsimulation.io;

import edu.kit.kastel.trafficsimulation.exception.SimulationException;
import edu.kit.kastel.trafficsimulation.model.simulation.Simulation;
import edu.kit.kastel.trafficsimulation.model.StreetNetwork;

import java.util.Scanner;


/**
 * instantiates the shell class with the in and ouput of the user who want to simulate traffic
 *
 * @author uyxib
 * @version 1.0
 */
public class Shell {
    /**
     * simulate a traffic simulation, reads the input of the user and prints out the result.
     * checks if the status of the simulation matches the input
     */

    public void simulate() {

        final StreetNetwork streetNetwork = new StreetNetwork();
        Simulation simulation = new Simulation(streetNetwork);
        Scanner inputScanner = new Scanner(System.in);
        while (Commands.getSimulation2().isReading()) {
            final String input = inputScanner.nextLine();
            try {
                print(input);
            } catch (final SimulationException exception) {
                System.err.println(exception.getMessage());
            }
        }

        while (Commands.getSimulation2().isActive()) {
            final String input = inputScanner.nextLine();
            try {
                print(input);
            } catch (final SimulationException exception) {
                System.err.println(exception.getMessage());
            }
        }
        inputScanner.close();
    }

    private void print(String input) {
        final String output = Commands.executeCommand(input);
        if (output != null) {
            System.out.println(output);
        }
    }
}
