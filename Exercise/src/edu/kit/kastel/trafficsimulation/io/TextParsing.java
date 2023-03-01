package edu.kit.kastel.trafficsimulation.io;

import edu.kit.kastel.trafficsimulation.exception.SimulationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author uyxib
 * @version 1.0
 */
public class TextParsing {

    private static final String STREET_REGEX = "^[0-9]+-->[0-9]+:(?:10|[1-9][0-9]{1,3}|10000)m," +
            "[1-2]x,(?:[5-9]|[1-3][0-9]|40)max$";
    private static final String CAR_REGEX = "^\\d+,\\d+,(?:20|2[1-9]|3[0-9]|40),(?:[1-9]|10)$";
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

    public List<int[]> validateCrossings() {
        List<int[]> crossingParameters = new ArrayList<>();
        try {
            crossingInput = simulationFileLoader.loadCrossings();
        } catch (IOException exception) {
            throw new SimulationException(exception.getMessage());
        }
        validatePatternMatch(crossingInput, CROSSING_REGEX, CROSSING);
        for (String inputRow : crossingInput) {
            String[] crossingParameter = getSplittedString(inputRow, COLON);
            crossingParameter[1] = removeLastChar(crossingParameter[1]);
            crossingParameters.add(stringArraytoInt(crossingParameter));
        }
        sortNodes(crossingParameters);
        return crossingParameters;
    }

    public List<int[]> validateCars() {
        List<int[]> carParameters = new ArrayList<>();
        try {
            carInput = simulationFileLoader.loadCars();
        } catch (IOException exception) {
            throw new SimulationException(exception.getMessage());
        }
        validatePatternMatch(carInput, CAR_REGEX, CAR);
        for (String inputRow : carInput) {
            String[] carParameter = getSplittedString(inputRow, KOMMA);
            carParameters.add(stringArraytoInt(carParameter));
        }
        return carParameters;
    }

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
            streetParameters.add(stringArraytoInt(streetParameter));
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

    private String[] getSplittedString(String input, String split) {
        return input.split(split);
    }

    private int[] stringArraytoInt(String[] stringArray) {
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
        return stringToRemove.substring(0, stringToRemove.length()-1);
    }

    private void validateEmptyFile(List<String> inputList, String file) {
        if (inputList.isEmpty()) {
            throw new SimulationException(ExceptionMessages.FILE_IS_EMPTY.format(file));
        }
    }

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
