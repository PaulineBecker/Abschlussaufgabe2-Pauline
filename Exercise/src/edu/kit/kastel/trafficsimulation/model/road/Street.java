package edu.kit.kastel.trafficsimulation.model.road;

import edu.kit.kastel.trafficsimulation.model.Car;
import edu.kit.kastel.trafficsimulation.model.Updatable;
import edu.kit.kastel.trafficsimulation.model.road.Road;

import java.util.LinkedList;

/**
 * @author uyxib
 * @version 1.0
 */
public class Street implements Road, Updatable {

    private static final int SAVE_DISTANCE = 10;
    private int startNode;
    private int endNode;
    private int length;
    private int speedLimit;
    private int streetID;
    private LinkedList<Car> cars = new LinkedList<>();

    public Street(int startNode, int endNode, int length, int speedLimit, int streetID) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.length = length;
        this.speedLimit = speedLimit;
        this.streetID = streetID;
    }

    @Override
    public void validateDistance() {

    }

    @Override
    public void fillStreet() {

    }

    @Override
    public void update() {

    }
}
