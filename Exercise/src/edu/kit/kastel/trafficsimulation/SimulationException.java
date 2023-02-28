package kit.edu.informatik;

/**
 * Encapsulates an exception that may occur during a Street Network Simulation
 *
 * @author uyxib
 * @version 1.0
 */
public class SimulationException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Game Exception with the give message.
     *
     * @param message the message of the exception
     */
    public SimulationException(String message) {
        super(message);
    }
}
