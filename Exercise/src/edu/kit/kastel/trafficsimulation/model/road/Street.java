package edu.kit.kastel.trafficsimulation.model.road;

import edu.kit.kastel.trafficsimulation.model.Car;
import edu.kit.kastel.trafficsimulation.model.Updatable;


import java.util.LinkedList;

/**
 * @author uyxib
 * @version 1.0
 */
public class Street implements Road, Updatable {
    /**
     * save distance between cars
     */

    public static final int SAVE_DISTANCE = 10;
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

    public int getStartNode() {
        return startNode;
    }

    public void setStartNode(int startNode) {
        this.startNode = startNode;
    }

    public int getEndNode() {
        return endNode;
    }

    public void setEndNode(int endNode) {
        this.endNode = endNode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public int getStreetID() {
        return streetID;
    }

    public LinkedList<Car> getCars() {
        return cars;
    }

    public void setCars(LinkedList<Car> cars) {
        this.cars = cars;
    }
}
