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
import java.util.List;

/**
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


    private List<Street> streets;
    private List<Car> cars;
    private List<Node> nodes;
    private TextParsing textParsing;
    private List<int[]> carParameters;
    private List<int[]> streetParameters;
    private List<int[]> crossingParameters;

    public StreetNetwork() {
        this.streets = new ArrayList<>();
        this.cars = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.textParsing = null;
        this.carParameters = new ArrayList<>();
        this.streetParameters = new ArrayList<>();
        this.crossingParameters = new ArrayList<>();
    }


    @Override
    public void update() {
        //TODO update first all Streets then all Cars then crossings (traffic lights)
    }

    public void init() {
        initNodes();
        initStreets();
        addStreetsToNode();
        validateNodes();
        initCars();
        addCarsToStreet();
    }

    public String getCurrentCar(int carID) {
        for (Car car: cars) {
            if (car.getCarID() == carID) {
                return car.toString();
            }
        }
        throw new SimulationException(ExceptionMessages.CAR_NOT_IN_SIMULATION.format());
    }

    public void readFiles(String filePath) {
        textParsing = new TextParsing(filePath);
        carParameters = textParsing.validateCars();
        streetParameters = textParsing.validateStreets();
        crossingParameters = textParsing.validateCrossings();
    }

    private void initNodes() {
        for (int i = 0; i < crossingParameters.size(); i++) {

            if (crossingParameters.get(i)[GREEN_TIME] == ROUNDABOUT) {
                nodes.add(new Roundabout(crossingParameters.get(i)[ID]));
            } else {
                nodes.add(new Intersection(crossingParameters.get(i)[ID], crossingParameters.get(i)[GREEN_TIME]));
            }
        }
    }

    private void initStreets() {
        for (int i = 0; i < streetParameters.size(); i++) {
            int[] currentStreet = streetParameters.get(i);
            if (currentStreet[STREET_TYPE] == SINGLE_LANE) {
                streets.add(new Street(currentStreet[START_NODE], currentStreet[END_NODE], currentStreet[LENGTH],
                        currentStreet[SPEED_LIMIT], i));
            } else {
                streets.add(new FastTrackStreet(currentStreet[START_NODE], currentStreet[END_NODE],
                        currentStreet[LENGTH], currentStreet[SPEED_LIMIT], i));
            }
        }
    }

    private void initCars() {
        for (int i = 0; i < carParameters.size(); i++) {
            cars.add(new Car(carParameters.get(i)[ID], carParameters.get(i)[STREET]
                    , carParameters.get(i)[PREFERED_SPEED], carParameters.get(i)[ACCELERATION]));
        }
    }

    private void addStreetsToNode() {
        for (Street street : streets) {
            for (Node node : nodes) {
                if (node.getNodeID() == street.getStartNode()) {
                    node.getOutgoingStreets().add(street);
                }
                if (node.getNodeID() == street.getEndNode()) {
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
                                .format(street.getStreetID(), i));
                    }
                }
            }
        }
    }
}
