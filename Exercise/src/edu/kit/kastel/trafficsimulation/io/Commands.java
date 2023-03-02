package edu.kit.kastel.trafficsimulation.io;

import edu.kit.kastel.trafficsimulation.exception.SimulationException;
import edu.kit.kastel.trafficsimulation.model.Simulation;
import edu.kit.kastel.trafficsimulation.model.StreetNetwork;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * List of available commands with their command line interaction expressions.
 * Copied from Thomas Weber and Moritz Gstuer and then modified from
 *
 * @author uyxib
 * @version 1.0
 */
public enum Commands {

    /**
     * return information position, speed of a given car
     */
    CAR_POSITION("^position " + Commands.ALL_INPUT) {
        @Override
        public String execute(String input, Simulation simulation) {
            Commands.validateActiveSimulation(simulation);
            Commands.validateSpaceAtEnd(input);
            String[] inputList = Commands.getSplittedString(Commands.replaceAllInput(this, input));
            Commands.validateArgumentsLength(inputList);
            int inputID = Commands.validateNumeric(inputList[0]);
            Commands.legalNumber(inputID);
            return simulation.showCar(inputID);
        }
    },

    /**
     * input of a user to get the information position, speed of a given car
     */
    SIMULATE("^simulate " + Commands.ALL_INPUT) {
        @Override
        public String execute(String input, Simulation simulation) {
            Commands.validateActiveSimulation(simulation);
            Commands.validateSpaceAtEnd(input);
            String[] inputList = Commands.getSplittedString(Commands.replaceAllInput(this, input));
            Commands.validateArgumentsLength(inputList);
            int inputNumber = Commands.validateNumeric(inputList[0]);
            Commands.legalNumber(inputNumber);
            return simulation.simulate(inputNumber);
        }
    },

    /**
     * command to read in the given files
     */
    LOAD("^load " + Commands.ALL_INPUT) {

        @Override
        public String execute(String input, Simulation simulation) {
            Commands.validateSpaceAtEnd(input);
            String[] inputList = Commands.getSplittedString(Commands.replaceAllInput(this, input));
            Commands.validateArgumentsLength(inputList);
            String filePath = inputList[0];
            return simulation.readFiles(filePath);
        }
    },

    /**
     * command to quit the game
     */
    QUIT("quit") {

        @Override
        public String execute(String input, Simulation simulation) {
            simulation.quit();
            return null;
        }
    };

    /**
     * Regex that every input is allowed after a specific input command
     */
    private static final String ALL_INPUT = ".*";
    private static final String EMPTY_STRING = "";
    private static final String COMMAND_SEPERATOR = " ";
    /**
     * The separator between lines of output.
     */
    private static final String LINE_SEPARATOR = System.lineSeparator();

    /**
     * The pattern of this command.
     */
    private final Pattern pattern;

    /**
     * one of the allowed uiCommands
     */
    private final String uiCommand;

    /**
     * Instantiates a new command with the given String. The given String must be a
     * compilable {@link Pattern}.
     *
     * @param pattern the pattern of this command
     */
    Commands(final String pattern) {
        this.uiCommand = pattern;
        this.pattern = Pattern.compile(pattern);
    }

    /**
     * Executes the command contained in the input if there is any, returns an error
     * message otherwise. If a command is found in the input, returns the result of
     * this input performed on the given playlist.
     *
     * @param input the line of input
     * @param simulation the {@link StreetNetwork} the command is executed on
     *
     * @return the result of the command execution, may contain error messages or be
     *         null if there is no output
     */
    public static String executeCommand(final String input, final Simulation simulation) {
        for (final Commands command : Commands.values()) {
            final Matcher matcher = command.pattern.matcher(input);
            if (matcher.matches()) {
                return command.execute(input, simulation);
            }
        }
        return ExceptionMessages.COMMAND_NOT_FOUND.format();
    }

    /**
     * Executes the given input on the given scrabble.
     *
     * @param input     the line of input
     * @param simulation the game where the command is executed on
     * @return the result of the command execution, may contain error messages or be
     * null if there is no output
     */
    abstract String execute(String input, Simulation simulation);

    private static String replaceAllInput(Commands command, String input) {
        return input.replaceAll(command.uiCommand.replace(ALL_INPUT, EMPTY_STRING), EMPTY_STRING);
    }

    private static String[] getSplittedString(String input) {
        return input.split(COMMAND_SEPERATOR);
    }

    private static void legalNumber(int inputNumber) {
        if (inputNumber < 0) {
            throw new SimulationException(ExceptionMessages.NEGATIVE_COORDINATE.format());
        }
    }

    private static void validateSpaceAtEnd(String input) {
        if (input.endsWith(COMMAND_SEPERATOR)) {
            throw new SimulationException(ExceptionMessages.ILLEGAL_SPACE.format());
        }
    }

    private static void validateArgumentsLength(String[] inputList) {
        if (inputList.length != 1) {
            throw new SimulationException(ExceptionMessages.ONE_EXPECTED_ARGUMENTS.format());
        }
    }

    private static void validateActiveSimulation(Simulation simulation) {
        if (!simulation.isActive()) {
            throw new SimulationException(ExceptionMessages.NO_LOADED_STREET_NETWORK.format());
        }
    }

    /**
     * checks if the given input is a valid number to parse to an int
     * @throws SimulationException if you can't parse it to an int
     * @param inputNumber the given input of a player
     * @return the input in form of an int
     */
    private static int validateNumeric(String inputNumber) {
        try {
            return Integer.parseInt(inputNumber);
        } catch (NumberFormatException exception) {
            throw new SimulationException(ExceptionMessages.TO_HIGH_NOT_NUMERIC.format(inputNumber));
        }
    }
}
