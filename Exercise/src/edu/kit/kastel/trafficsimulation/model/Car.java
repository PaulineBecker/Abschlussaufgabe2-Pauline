package edu.kit.kastel.trafficsimulation.model;

import edu.kit.kastel.trafficsimulation.io.Messages;
import edu.kit.kastel.trafficsimulation.model.node.Node;

/**
 * models a car in the traffic simulation
 * a car has a specific desired speed, can accelerate, has a current speed, a current location with the position
 * and the street id and know if it already moved in one tick or not
 * It implements the Updatable interface which means it can be updated in each time step ("tick") of the simulation.
 *
 * @author uyxib
 * @version 1.0
 */
public class Car implements Updatable {
    private static final int START_SPEED = 0;
    private static final int START_DESIRED_STREET = 0;

    private int acceleration;
    private int desiredStreet;
    private int preferedSpeed;
    private int currentSpeed;
    private int currentStreet;
    private int carID;
    private int currentPosition;
    private boolean isMoved;

    /**
     * Creates a new Car object with the given parameters.
     * @param carID The ID of the car
     * @param currentStreet The current street of the car
     * @param preferedSpeed The preferred speed of the car
     * @param acceleration The acceleration of the car
     */
    public Car(int carID, int currentStreet, int preferedSpeed, int acceleration) {
        this.acceleration = acceleration;
        this.desiredStreet = START_DESIRED_STREET;
        this.currentSpeed = START_SPEED;
        this.currentStreet = currentStreet;
        this.carID = carID;
        this.preferedSpeed = preferedSpeed;
        this.currentPosition = -1; //TODO
        this.isMoved = false;
    }

    /**
     * creates a String where the car is located with the car ID, currentStreet, currentSpeed and the currentPosition
     * @return the string output to the user that explains where the car is located
     */
    @Override
    public String toString() {
        return Messages.CAR_POSITION.format(this.carID, this.currentStreet, this.currentSpeed, this.currentPosition);
    }

    /**
     * Updates the car's desired street.
     */
    @Override
    public void update() {
        changeDesiredStreet();
    }

    /**
     * Changes the car's desired street based on the current desired street.
     */
    private void changeDesiredStreet() {
        if (desiredStreet < Node.MAXIMUM_STREETS) {
            desiredStreet++;
        } else if (desiredStreet == Node.MAXIMUM_STREETS) {
            desiredStreet = Node.START_STREET;
        }
    }

    private void accelerate() {
        //TODO
    }

    /**
     * Resets the car's speed in traffic to the starting speed.
     */
    private void resetSpeedInTraffic() {
        currentSpeed = START_SPEED;
    }

    /**
     * Returns the acceleration of the car.
     * @return The acceleration of the car
     */

    public int getAcceleration() {
        return acceleration;
    }

    /**
     * Sets the acceleration of the car.
     * @param acceleration The new acceleration of the car
     */

    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Returns the desired street of the car.
     * @return The desired street of the car
     */

    public int getDesiredStreet() {
        return desiredStreet;
    }

    /**
     * Sets the desired street of the car.
     * @param desiredStreet The new desired street of the car
     */

    public void setDesiredStreet(int desiredStreet) {
        this.desiredStreet = desiredStreet;
    }

    /**
     * Returns the current speed of the car
     * @return the current speed of the car
     */
    public int getCurrentSpeed() {
        return currentSpeed;
    }

    /**
     * Sets the current speed of the car to the specified value
     * @param currentSpeed the new current speed of the car
     */

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    /**
     * Returns the current street that the car is on
     * @return the current street of the car
     */

    public int getCurrentStreet() {
        return currentStreet;
    }

    /**
     * Sets the current street of the car to the specified value
     * @param currentStreet the new current street of the car
     */

    public void setCurrentStreet(int currentStreet) {
        this.currentStreet = currentStreet;
    }

    /**
     * Returns the ID of the car
     * @return the ID of the car
     */

    public int getCarID() {
        return carID;
    }

    /**
     * Returns the current position of the car
     * @return the current position of the car
     */

    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Sets the current position of the car to the specified value
     * @param currentPosition the new current position of the car
     */

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * Returns whether the car has moved or not
     * @return true if the car has moved, false otherwise
     */
    public boolean isMoved() {
        return isMoved;
    }

    /**
     * Sets whether the car has moved or not
     * @param moved true if the car has moved, false otherwise
     */

    public void setMoved(boolean moved) {
        isMoved = moved;
    }
}
