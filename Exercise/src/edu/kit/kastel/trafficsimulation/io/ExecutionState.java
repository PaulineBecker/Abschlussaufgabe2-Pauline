package edu.kit.kastel.trafficsimulation.io;

/**
 * The different execution states the application can go through.
 * copied from Thomas Weber and then modified
 *
 * @author uyxib
 * @version 1.0
 */
public enum ExecutionState {
    /**
     * The program is finished and will shut down shortly.
     */
    EXITED,
    /**
     * The program is currently running.
     */
    RUNNING,
    /**
     * The programm is currently reading
     */
    READING,
}
