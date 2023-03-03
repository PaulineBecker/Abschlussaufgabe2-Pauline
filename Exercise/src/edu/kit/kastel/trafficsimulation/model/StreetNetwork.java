package edu.kit.kastel.trafficsimulation.model;

import edu.kit.kastel.trafficsimulation.exception.SimulationException;
import edu.kit.kastel.trafficsimulation.io.ExceptionMessages;
import edu.kit.kastel.trafficsimulation.io.TextParsing;
import edu.kit.kastel.trafficsimulation.model.node.Intersection;
import edu.kit.kastel.trafficsimulation.model.node.Node;
import edu.kit.kastel.trafficsimulation.model.node.Roundabout;
import edu.kit.kastel.trafficsimulation.model.road.FastTrackStreet;
import edu.kit.kastel.trafficsimulation.model.road.Street;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * StreetNetwork represents a network of streets with cars and nodes. It implements the Updatable interface,
 * which defines a single update() method that updates the streets, resets all cars on streets, and updates the nodes.
 *
 * @author uyxib
 * @version 1.0
 */
public class StreetNetwork implements Updatable {

    private static final int START_NODE = 0;
    private static final int END_NODE = 1;
    private static final int SPEED_LIMIT = 4;
    private static final int LENGTH = 2;
    private static final int ID = 0;
    private static final int STREET_TYPE = 3;
    private static final int SINGLE_LANE = 1;
    private static final int ROUNDABOUT = 0;
    private static final int GREEN_TIME = 1;
    private static final int STREET = 1;
    private static final int PREFERED_SPEED = 2;
    private static final int ACCELERATION = 3;
    private final List<Street> streets;
    private final List<Car> cars;
    private final List<Node> nodes;
    private TextParsing textParsing;
    private List<int[]> carParameters;
    private List<int[]> streetParameters;
    private List<int[]> crossingParameters;

    /**
     * Constructs an empty StreetNetwork object with empty lists for streets, cars, and nodes, as well as null values
     * for textParsing and carParameters.
     */
    public StreetNetwork() {
        this.streets = new ArrayList<>();
        this.cars = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.textParsing = null;
        this.carParameters = new ArrayList<>();
        this.streetParameters = new ArrayList<>();
        this.crossingParameters = new ArrayList<>();
    }

    /**
     * Updates the streets in the network, resets all cars on streets, and updates the nodes.
     */
    @Override
    public void update() {
        updateStreets();
        resetAllCarsOnStreets();
        updateNodes();
    }

    /**
     * Initializes the nodes, streets, and cars in the network, validates the nodes, and checks for duplicate IDs
     * for both cars and nodes. Adds cars to the street and connects streets and nodes.
     */
    public void init() {
        initNodes();
        initStreets();
        addStreetsToNode();
        validateNodes();
        validateNodesExistsForStreets();
        initCars();
        addCarsToStreet();
        checkDuplicateCarIds();
        checkDuplicateNodeIds();
    }

    /**
     * Returns the String representation of the car with the given ID in the simulation.
     * @param carID the ID of the car to get the String representation of
     * @return the String representation of the car with the given ID
     * @throws SimulationException if no car with the given ID exists in the simulation
     */
    public String getCurrentCar(int carID) {
        for (Car car: cars) {
            if (car.getCarID() == carID) {
                return car.toString();
            }
        }
        throw new SimulationException(ExceptionMessages.CAR_NOT_IN_SIMULATION.format());
    }

    /**
     * Reads files containing parameters for cars, streets, and crossings from the given file path, validates the
     * parameters, and sets the carParameters, streetParameters, and crossingParameters fields in the StreetNetwork
     * object to the validated parameters.
     * @param filePath the path of the file to read parameters from
     */
    public void readFiles(String filePath) {
        textParsing = new TextParsing(filePath);
        carParameters = textParsing.validateCars();
        streetParameters = textParsing.validateStreets();
        crossingParameters = textParsing.validateCrossings();
    }

    /**
     * Returns the Node object with the given node ID.
     * @throws SimulationException if the node is not found in the list of nodes
     * @param nodeID the ID of the node to be returned
     * @param streetID the ID of the street associated with the node
     * @return the Node object with the given ID
     */
    public Node getNodeFromID(int nodeID, int streetID) {
        for (Node node : nodes) {
            if (node.getNodeID() == nodeID) {
                return node;
            }
        }
        throw new SimulationException(ExceptionMessages.STREET_WITH_ILLEGAL_NODE.format(streetID));
        //TODO prüfen ob exisitert also falls null exeption
    }

    /**
     * Initializes the nodes based on the crossing parameters, creating either an Intersection or a Roundabout object.
     */
    private void initNodes() {
        for (int i = 0; i < crossingParameters.size(); i++) {

            if (crossingParameters.get(i)[GREEN_TIME] == ROUNDABOUT) {
                nodes.add(new Roundabout(crossingParameters.get(i)[ID]));
            } else {
                nodes.add(new Intersection(crossingParameters.get(i)[ID], crossingParameters.get(i)[GREEN_TIME]));
            }
        }
    }

    /**
     * Initializes the streets based on the street parameters, creating either a Street or a FastTrackStreet object.
     * @throws SimulationException if the start and end nodes of a street are the same.
     */
    private void initStreets() {
        for (int i = 0; i < streetParameters.size(); i++) {
            int[] currentStreet = streetParameters.get(i);
            if (currentStreet[START_NODE] == currentStreet[END_NODE]) {
                throw new SimulationException(ExceptionMessages.INVALID_STREET_NODE.format(i));
            } else if (currentStreet[STREET_TYPE] == SINGLE_LANE) {
                streets.add(new Street(currentStreet[START_NODE], currentStreet[END_NODE], currentStreet[LENGTH],
                        currentStreet[SPEED_LIMIT], i,
                        getNodeFromID(currentStreet[START_NODE], i), getNodeFromID(currentStreet[END_NODE], i)));
            } else {
                streets.add(new FastTrackStreet(currentStreet[START_NODE], currentStreet[END_NODE],
                        currentStreet[LENGTH], currentStreet[SPEED_LIMIT], i,
                        getNodeFromID(currentStreet[START_NODE], i), getNodeFromID(currentStreet[END_NODE], i)));
            }
        }
    }

    /**
     * Initializes the cars based on the car parameters.
     */
    private void initCars() {
        for (int i = 0; i < carParameters.size(); i++) {
            cars.add(new Car(carParameters.get(i)[ID], carParameters.get(i)[STREET]
                    , carParameters.get(i)[PREFERED_SPEED], carParameters.get(i)[ACCELERATION]));
        }
    }

    private void addStreetsToNode() {
        for (Street street : streets) {
            for (Node node : nodes) {
                if (node.getNodeID() == street.getIntStartNode()) {
                    node.getOutgoingStreets().add(street);
                }
                if (node.getNodeID() == street.getIntEndNode()) {
                    node.getIncomingStreets().add(street);
                }
            }
        }
    }

    private void validateNodes() {
        for (Node node: nodes) {
            node.validateCorrespondingStreets(node.getIncomingStreets());
            node.validateCorrespondingStreets(node.getOutgoingStreets());
        }
    }

    private void addCarsToStreet() {
        for (Street street : streets) {
            int streetPosition = street.getLength();
            for (int i = 0; i < cars.size(); i++) {
                if (cars.get(i).getCurrentStreet() == street.getStreetID()) {
                    if (streetPosition >= 0) {
                        cars.get(i).setCurrentPosition(streetPosition); //aktuelle Position dem Auto geben von Straße
                        street.getCars().add(cars.get(i)); //Auto in Straße hinzufügen
                        streetPosition -= Street.SAVE_DISTANCE;
                    } else {
                        throw new SimulationException(ExceptionMessages.INVALID_NUMBER_OF_CARS_ON_STREET
                                .format(street.getStreetID(), i - 1));
                    }
                }
            }
        }
    }

    private void checkDuplicateCarIds() {
        HashSet<Integer> iDs = new HashSet<>();
        for (Car car : cars) {
            if (!iDs.add(car.getCarID())) {
                throw new SimulationException(ExceptionMessages.DUPLICATED_CAR_ID.format(car.getCarID()));
            }
        }
    }

    private void checkDuplicateNodeIds() {
        HashSet<Integer> iDs = new HashSet<>();
        for (Node node : nodes) {
            if (!iDs.add(node.getNodeID())) {
                throw new SimulationException(ExceptionMessages.DUPLICATED_CROSSING_ID.format(node.getNodeID()));
            }
        }
    }

    private void validateNodesExistsForStreets() {
        for (Street street : streets) {
            boolean startNodeExists = false;
            boolean endNodeExists = false;
            for (Node node : nodes) {
                if (street.getIntStartNode() == node.getNodeID()) {
                    startNodeExists = true;
                }
                if (street.getIntEndNode() == node.getNodeID()) {
                    endNodeExists = true;
                }
            }
            if (!startNodeExists || !endNodeExists) {
                throw new SimulationException(ExceptionMessages.STREET_WITH_ILLEGAL_NODE.format(street.getStreetID()));
            }
        }
    }

    private void updateNodes() {
        for (Node node : nodes) {
            node.update();
        }
    }

    private void updateStreets() {
        for (Street street : streets) {
            street.update();
        }
    }

    private void resetAllCarsOnStreets() {
        for (Street street : streets) {
            street.resetCarMoves();
        }
    }
}
