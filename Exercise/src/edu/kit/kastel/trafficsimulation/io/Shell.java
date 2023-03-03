package edu.kit.kastel.trafficsimulation.io;

import edu.kit.kastel.trafficsimulation.exception.SimulationException;

import java.util.Scanner;


/**
 * instantiates the shell class with the in and output of the user who want to simulate traffic
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
