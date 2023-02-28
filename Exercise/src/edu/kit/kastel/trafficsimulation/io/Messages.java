package kit.edu.informatik.userinterface;

/**
 * @author uyxib
 * @verion 1.0
 */
public enum Messages {
    /**
     * The message printed when a user wants to now the position of a car
     * <p>
     * Expects format argument, in the following order:
     * ID of the car (int)
     * ID of the street (int)
     * current speed of the car (int)
     * current position of the car (int)
     *
     */
    CAR_POSITION("Car %s on Street %s with speed %s and position %s"),
    /**
     * The Message printed when the input files are read correctly or the simulation is done correctly
     */
    READY("READY");

    private final String message;

    /**
     * Instantiates a new Message with the given String.
     * @param message the corresponding string sentence of the message
     */

    Messages(String message) {
        this.message = message;
    }

    /**
     * Formats this message with the specified arguments.
     * <p>
     * Calls {@link String#format(String, Object...)} internally.
     *
     * @param args arguments referenced by the format specifiers in the format string
     * @return the formatted string
     */
    public String format(Object... args) {
        return String.format(this.message, args);
    }
}
