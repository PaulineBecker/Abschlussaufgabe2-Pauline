package edu.kit.kastel.trafficsimulation.io;

/**
 * Exception Messages enum that contains all error messages when an exceptions is thrown.
 *
 * @author uyxib
 * @version 1.0
 */
public enum ExceptionMessages {

    /**
     * Error message if the user tries to do something on a street network that is not loaded yet.
     */
    NO_LOADED_STREET_NETWORK("Error: Street network is yet to be loaded."),
    /**
     * Error message in case of a player tries to add a space at the end of his / her command
     */
    ILLEGAL_SPACE("Error: Adding a space at the end of your command is illegal."),
    /**
     * Error message if the player tries to plant on a negativ yCoordinate that is not part of the gameboard.
     */
    NEGATIVE_COORDINATE("Error: Negative number is here not allowed."),
    /**
     * Error message if the player tries to enter not the expected amount of three arguments.
     */
    ONE_EXPECTED_ARGUMENTS("Error: Only one argument is expected."),
    /**
     * Error message if the seed to shuffle the tiles is not in the range of a long.
     */
    TO_HIGH_NOT_NUMERIC("Error: The %s too high or not numeric to parse to an int."),
    /**
     * Error message if the command is not found
     */
    COMMAND_NOT_FOUND("Error: Command not found. Please enter a valid command.");

    private final String message;

    /**
     * Instantiates a new Exception message with the given String.
     * @param message the corresponding string sentence that explains the Exception messsage
     */

    ExceptionMessages(String message) {
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
