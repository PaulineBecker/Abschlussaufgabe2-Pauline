package edu.kit.kastel.trafficsimulation.model;

import edu.kit.kastel.trafficsimulation.exception.SimulationException;
import edu.kit.kastel.trafficsimulation.io.ExceptionMessages;
import edu.kit.kastel.trafficsimulation.io.TextParsing;
import edu.kit.kastel.trafficsimulation.model.node.Node;
import edu.kit.kastel.trafficsimulation.model.road.Street;

import java.util.ArrayList;
import java.util.List;

/**
 * @author uyxib
 * @version 1.0
 */
public class StreetNetwork implements Updatable {

    private List<Street> streets;
    private List<Car> cars;
    private List<Node> nodes;
    private TextParsing textParsing;
    private List<Integer[]> carParameters;
    private List<Integer[]> streetParameters;
    private List<Integer[]> crossingParameters;

    public StreetNetwork() {
        this.streets = new ArrayList<>();
        this.cars = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.textParsing = null; //TODO
        this.carParameters = new ArrayList<>();
        this.streetParameters = new ArrayList<>();
        this.crossingParameters = new ArrayList<>();
    }


    @Override
    public void update() {
        //TODO update first all Streets then all Cars then crossings (traffic lights)
    }

    public void init(){
        //TODO Street network initialisieren erst Knoten dann Straßen dann Autos
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
}
