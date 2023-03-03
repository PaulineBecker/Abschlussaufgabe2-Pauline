package edu.kit.kastel.trafficsimulation.io;

import edu.kit.kastel.trafficsimulation.exception.SimulationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Encapsulations the object to parse the input of car, crossings and street files.
 *
 * @author uyxib
 * @version 1.0
 */
public class TextParsing {
    /**
     * the regex that matches one line with the parameters of a street
     */

    private static final String STREET_REGEX = "^[0-9]+-->[0-9]+:(?:10|[1-9][0-9]{1,3}|10000)m,"
            + "[1-2]x,(?:[5-9]|[1-3][0-9]|40)max$";
    /**
     * the regex that matches one line with the parameters of a car
     */
    private static final String CAR_REGEX = "^\\d+,\\d+,(?:20|2[1-9]|3[0-9]|40),(?:[1-9]|10)$";
    /**
     * the regex that matches one line with the parameters of a crossing
     */
    private static final String CROSSING_REGEX = "^[0-9]+:[0|3-9|10]t$";
    private static final String CROSSING = "crossing";
    private static final String CAR = "car";
    private static final String STREET = "street";
    private static final String KOMMA = ",";
    private static final String COLON = ":";
    private static final String STREET_SPLIT = "\\d+";
    private final SimulationFileLoader simulationFileLoader;
    private List<String> carInput;
    private List<String> streetInput;
    private List<String> crossingInput;

    /**
     * instantiates a new textParser with the simulationFileLoader, and empty List of cars, crossings and streets
     * @param filePath filePath where the files a located to read the files
     */

    public TextParsing(String filePath) {
        this.carInput = new ArrayList<>();
        this.crossingInput = new ArrayList<>();
        this.streetInput = new ArrayList<>();
        try {
            this.simulationFileLoader = new SimulationFileLoader(filePath);
        } catch (IOException exception) {
            throw new SimulationException(exception.getMessage());
        }
    }

    /**
     * validates the input File of Crossings if the lines representing one crossing matches the crossing-regex
     * splits the input string and gets only the digits of the string
     * @throws SimulationException if the file is not found, the crossings input Strings don't match the pattern
     * @return list of Integers Arrays, Every integer array entry contains the int values of a crossing
     */

    public List<int[]> validateCrossings() {
        List<int[]> crossingParameters = new ArrayList<>();
        try {
            crossingInput = simulationFileLoader.loadCrossings();
        } catch (IOException exception) {
            throw new SimulationException(exception.getMessage());
        }
        validatePatternMatch(crossingInput, CROSSING_REGEX, CROSSING);
        for (String inputRow : crossingInput) {
            String[] crossingParameter = getSplitString(inputRow, COLON);
            crossingParameter[1] = removeLastChar(crossingParameter[1]);
            crossingParameters.add(stringArrayToInt(crossingParameter));
        }
        sortNodes(crossingParameters);
        return crossingParameters;
    }

    /**
     * validates the input File of Cars if the lines representing one car matches the car-regex
     * splits the input string and gets only the digits of the string
     * @throws SimulationException if the file is not found, the car input Strings don't match the pattern
     * @return list of Integers Arrays, Every integer array entry contains the int values of a crossing
     */

    public List<int[]> validateCars() {
        List<int[]> carParameters = new ArrayList<>();
        try {
            carInput = simulationFileLoader.loadCars();
        } catch (IOException exception) {
            throw new SimulationException(exception.getMessage());
        }
        validatePatternMatch(carInput, CAR_REGEX, CAR);
        for (String inputRow : carInput) {
            String[] carParameter = getSplitString(inputRow, KOMMA);
            carParameters.add(stringArrayToInt(carParameter));
        }
        return carParameters;
    }

    /**
     * validates the input File of Streets if the lines representing one Street matches the Street-regex
     * splits the input string and gets only the digits of the string
     * @throws SimulationException if the file is not found, the Street input Strings don't match the pattern
     * @return list of Integers Arrays, Every integer array entry contains the int values of a crossing
     */
    public List<int[]> validateStreets() {
        List<int[]> streetParameters = new ArrayList<>();
        try {
            streetInput = simulationFileLoader.loadStreets();
        } catch (IOException exception) {
            throw new SimulationException(exception.getMessage());
        }
        validatePatternMatch(streetInput, STREET_REGEX, STREET);
        for (String inputRow : streetInput) {
            String[] streetParameter = extractNumbers(inputRow);
            streetParameters.add(stringArrayToInt(streetParameter));
        }
        //TODO StartKnoten != Endknoten
        return streetParameters;
    }

    private void validatePatternMatch(List<String> inputList, String regex, String file) {
        for (String string : inputList) {
            if (!string.matches(regex)) {
                throw new SimulationException(ExceptionMessages.NO_VALID_STRING_LINE.format(file, file));
            }
        }
    }

    private String[] getSplitString(String input, String split) {
        return input.split(split);
    }

    private int[] stringArrayToInt(String[] stringArray) {
        int[] intArray = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            try {
                intArray[i] = Integer.parseInt(stringArray[i]);
            } catch (NumberFormatException exception) {
                throw new SimulationException(ExceptionMessages.TO_HIGH_NOT_NUMERIC.format(stringArray));
            }
        }
        return intArray;
    }

    private String removeLastChar(String stringToRemove) {
        return stringToRemove.substring(0, stringToRemove.length() - 1);
    }

    private void validateEmptyFile(List<String> inputList, String file) {
        if (inputList.isEmpty()) {
            throw new SimulationException(ExceptionMessages.FILE_IS_EMPTY.format(file));
        }
    } //TODO nötig??

    private static String[] extractNumbers(String input) {
        String[] output = new String[5];
        Pattern pattern = Pattern.compile(STREET_SPLIT);
        Matcher matcher = pattern.matcher(input);

        int i = 0;
        while (matcher.find() && i < 5) {
            output[i] = matcher.group();
            i++;
        }
        return output;
    }

    private void sortNodes(List<int[]> crossingParameters) {
        crossingParameters.sort(new Comparator<>() {
            private static final int INDEX = 0;
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[INDEX], o2[INDEX]);
            }
        });
    }

}
