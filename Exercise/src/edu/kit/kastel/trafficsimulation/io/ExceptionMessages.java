package edu.kit.kastel.trafficsimulation.io;

/**
 * Exception Messages enum that contains all error messages when an exceptions are thrown.
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
     * Exception message in case of utility class instantiation.
     */
    UTILITY_CLASS_INSTANTIATION("Utility class cannot be instantiated."),
    /**
     * Error message in case of a player tries to add a space at the end of his / her command
     */
    ILLEGAL_SPACE("Error: Adding a space at the end of your command is illegal."),
    /**
     * Error message if the use tries to simulate with negative ticks or the position a negative car
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
     * Error message if the car is not part of the current simulation
     */
    CAR_NOT_IN_SIMULATION("Error: This car is not part of the current simulation."),
    /**
     * Error message if a file contains invalid strings
     */
    NO_VALID_STRING_LINE("Error: This %s file contains invalid %ss"),
    /**
     * Error message if a file contains invalid strings
     */
    FILE_IS_EMPTY("Error: This %s file is empty."),
    /**
     * Error message if a file contains invalid strings
     */
    INVALID_NUMBER_OF_STREETS("Error: The %d. node has an invalid number of streets."),
    /**
     * Error message if more cars try to be on one street than possible
     */
    INVALID_NUMBER_OF_CARS_ON_STREET("Error: Street %d cannot have more than %d cars."),
    /**
     * Error message if a street has the same start and end node.
     */
    INVALID_STREET_NODE("Error: Street %d cannot have the same start- and end node."),
    /**
     * Error message if the ID of cars exists more than only once
     */
    DUPLICATED_CAR_ID("Error: The car ID %d exists more than once."),
    /**
     * Error message if the ID of crossings exists more than only once
     */
    DUPLICATED_CROSSING_ID("Error: The crossing ID %d exists more than once."),
    /**
     * Error message if the ID of crossings exists more than only once
     */
    STREET_WITH_ILLEGAL_NODE("Error: The street %d has a node that doesn't exist."),
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
