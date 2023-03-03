package edu.kit.kastel.trafficsimulation.model.road;

import edu.kit.kastel.trafficsimulation.model.Car;
import edu.kit.kastel.trafficsimulation.model.Updatable;
import edu.kit.kastel.trafficsimulation.model.node.Node;
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
    protected int intStartNode;
    protected int intEndNode;
    protected final Node startNode;
    protected final Node endNode;
    protected int length;
    protected int speedLimit;
    protected final int streetID;
    protected LinkedList<Car> cars = new LinkedList<>();


    /**
     * Constructs a new street object with the given parameters.
     * @param intStartNode The start node ID of the street.
     * @param intendNode The end node ID of the street.
     * @param length The length of the street in meters.
     * @param speedLimit The speed limit of the street in meters per second.
     * @param streetID The ID of the street.
     * @param startNode The start node of the street.
     * @param endNode The end node of the street.
     **/
    public Street(int intStartNode, int intendNode, int length, int speedLimit, int streetID,
                  Node startNode, Node endNode) {
        this.intStartNode = intStartNode;
        this.intEndNode = intendNode;
        this.length = length;
        this.speedLimit = speedLimit;
        this.streetID = streetID;
        this.startNode = startNode;
        this.endNode = endNode;
    }

    /**
     * Updates the street and its cars according to their current status.
     * Moves each car forward and turns it to the new street if it reaches the end of the current street.
     * Resets move parameters for all cars on the street.
     */
    @Override
    public void update() {
        int i = 0;
        while (i < cars.size()) {
            Car currentCar = cars.get(i);
            if (!currentCar.isMoved()) {
                currentCar.accelerate(speedLimit);
                currentCar.drive(calculateDistance(i, cars), length, currentCar.getCurrentSpeed());
                if (!turnCar(currentCar)) {
                    i++;
                }
                currentCar.resetSpeedInTraffic();
            } else {
                i++;
            }

        }
    }

    protected boolean turnCar(Car car) {

        if (car.getCurrentPosition() != length) {
            return false;
        }
        if (!endNode.hasStreetGreen(streetID)) {
            return false;
        }
        Street newOutgoingStreet = getNewStreet(car);
        if (!car.turn(newOutgoingStreet)) {
            return false;
        }
        cars.remove(car);
        newOutgoingStreet.getCars().addLast(car);
        car.drive(calculateDistance(newOutgoingStreet.cars.size() - 1, newOutgoingStreet.getCars()),
                newOutgoingStreet.getLength(), car.getRemainingMeters());
        car.update();
        return true;
    }

    /**
     * Resets the move parameters for all cars on the street.
     */
    public void resetCarMoves() {
        for (Car car : cars) {
            car.resetMoveParameters();
        }
    }

    /**
     * Returns the start node ID of the street.
     * @return The start node ID of the street.
     */
    public int getIntStartNode() {
        return intStartNode;
    }

    /**
     * Sets the start node ID of the street.
     * @param intStartNode The start node ID of the street to set.
     */
    public void setIntStartNode(int intStartNode) {
        this.intStartNode = intStartNode;
    }

    /**
     * Returns the end node ID of the street.
     * @return The end node ID of the street.
     */
    public int getIntEndNode() {
        return intEndNode;
    }

    /**
     * Sets the end node of this street to the specified integer value.
     * @param intEndNode the integer value representing the end node of this street
     */
    public void setIntEndNode(int intEndNode) {
        this.intEndNode = intEndNode;
    }

    /**
     * Returns the length of this street.
     * @return the length of this street
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the length of this street to the specified integer value.
     * @param length the integer value representing the length of this street
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Returns the speed limit of this street.
     * @return the speed limit of this street
     */
    public int getSpeedLimit() {
        return speedLimit;
    }

    /**
     * Sets the speed limit of this street to the specified integer value.
     * @param speedLimit the integer value representing the speed limit of this street
     */
    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    /**
     * Returns the ID of this street.
     * @return the ID of this street
     */
    public int getStreetID() {
        return streetID;
    }

    /**
     * Returns the list of cars currently on this street.
     * @return the list of cars currently on this street
     */
    public LinkedList<Car> getCars() {
        return cars;
    }

    /**
     * Sets the list of cars currently on this street to the specified LinkedList of Car objects.
     * @param cars the LinkedList of Car objects representing the list of cars currently on this street
     */
    public void setCars(LinkedList<Car> cars) {
        this.cars = cars;
    }

    /**
     * Calculates and returns the distance between the car at the specified index and the car in front of it.
     * If there is no car in front of it, returns a constant value indicating that there is no car in front.
     * @param index the index of the car for which to calculate the distance to the car in front
     * @param cars the LinkedList of Car objects representing the list of cars currently on this street
     * @return the distance between the car at the specified index and the car in front of it,
     * or a constant value indicating that there is no car in front
     */
    protected int calculateDistance(int index, LinkedList<Car> cars) {
        if (index > 0) {
            return (cars.get(index - 1).getCurrentPosition()
                    - cars.get(index).getCurrentPosition());
        } else {
            return NO_CAR_IN_FRONT;
        }
    }

    /**
     * Returns the next street for the specified car based on its desired street.
     * If the desired street is not valid, returns the starting street.
     * @param car the Car object for which to find the next street
     * @return the next street for the specified car based on its desired street
     */
    private Street getNewStreet(Car car) {
        if (endNode.getOutgoingStreets().size() - 1 >= car.getDesiredStreet()) {
            return  endNode.getOutgoingStreets().get(car.getDesiredStreet());
        } else {
            return endNode.getOutgoingStreets().get(Node.START_STREET);
        }
    }
}
