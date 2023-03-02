package edu.kit.kastel.trafficsimulation.model.road;

import edu.kit.kastel.trafficsimulation.model.Car;
import edu.kit.kastel.trafficsimulation.model.Updatable;
import edu.kit.kastel.trafficsimulation.model.node.Node;
import edu.kit.kastel.trafficsimulation.model.node.Roundabout;


import java.util.Iterator;
import java.util.LinkedList;

/**
 * A class representing a street that implements Updatable interfaces.
 * Cars are driving on the streets and they always have to keep the save distance of 10.
 * so every street can have only length / 10 + 1 car on the street.
 *
 * @author uyxib
 * @version 1.0
 */
public class Street implements Updatable {

    /**
     * save distance between cars
     */
    public static final int SAVE_DISTANCE = 10;
    /**
     * distance if there's no car in front of the current Car
     */
    public static final int NO_CAR_IN_FRONT = -1;
    private int startNode;
    private int endNode;
    private int length;
    private int speedLimit;
    private final int streetID;
    private LinkedList<Car> cars = new LinkedList<>();

    public Street(int startNode, int endNode, int length, int speedLimit, int streetID) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.length = length;
        this.speedLimit = speedLimit;
        this.streetID = streetID;
    }

    @Override
    public void update() {
        for (int i = 0; i < cars.size(); i++) {
            cars.get(i).accelerate(speedLimit);
            cars.get(i).drive(calculateDistance(i), length);
        }

    }

    public void turnCar(Car car, Node node) {
        if (car.getCurrentPosition() != length) {
            return;
        }
        if (node.getCurrentGreenStreet() == Roundabout.ALL_STREETS_GREEN) {
            //TODO wie checken ob Straße grün hat
            //TODO wie findet er neue Straße zum übergeben
        }
    }

    public void resetCarMoves() {
        for (Car car : cars) {
            car.resetIsMoved();
        }
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

    private int calculateDistance(int index) {
        if (cars.size() - 1 > index) {
            return (cars.get(index + 1).getCurrentPosition() - cars.get(index).getCurrentPosition());
        } else {
            return NO_CAR_IN_FRONT;
        }
    }
}
