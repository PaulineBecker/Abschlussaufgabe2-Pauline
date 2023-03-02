package edu.kit.kastel.trafficsimulation.io;

/**
 * Main class for the first task of the fifth assignment. Contains the entry point.
 *
 * @author uyxib
 * @version 1.0
 */
public final class Main {


    /**
     * Private constructor to avoid object generation.
     */
    private Main() {
        throw new IllegalStateException(ExceptionMessages.UTILITY_CLASS_INSTANTIATION.format());
    }

    /**
     * Entry point to the program.
     * @param args Commando Line Arguments
     */
    public static void main(String[] args) {

        Shell shell = new Shell();
        shell.simulate();
    }
}