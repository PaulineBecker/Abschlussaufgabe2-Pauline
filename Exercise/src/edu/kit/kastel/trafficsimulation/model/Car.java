package edu.kit.kastel.trafficsimulation.model;

import edu.kit.kastel.trafficsimulation.io.Messages;
import edu.kit.kastel.trafficsimulation.model.node.Node;
import edu.kit.kastel.trafficsimulation.model.road.Street;

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
    /**
     * remaining meters a car can drive after the end of a street. If remaining meters > 0 check if car can cross street
     */
    private int remainingMeters;
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
        this.remainingMeters = 0;
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
     * Updates the car's desired street after the car entered in a new street after a crossing
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

    public void accelerate(int speedLimit) {
        int newSpeed = Math.min((currentSpeed + acceleration), speedLimit);
        currentSpeed = Math.min(newSpeed, preferedSpeed);
    }

    /**
     * Car drives with his currentSpeed if possible. First checks if the distance to the end of the street is long
     * enough, then checks the distance to the next car and drives as long as possible.
     * 1. Case: street long enough, distance of car long enough -> car drives with his currentSpeed
     * 2. Case: street long enough, distance of car too short -> car drives only the distance - save distance
     * 3. Case: street not long enough, distance of car long enough -> car drives the metersToGo till the end of street
     * 4. Case: street not long enough, distance of car long -> car drives only the distance - save distance
     * for case 3 & 4 car stores the remaining Meters and will check later if it can move to another street
     * additionally after driving sets boolean is moved to true
     * @param distance distance to the next car in the current street
     * @param length length of the current street
     */
    public void drive(int distance, int length) {
        int oldPosition = currentPosition;
        int metersToGo = length - currentPosition;
        if (metersToGo >= currentSpeed) {
            if ((distance == Street.NO_CAR_IN_FRONT) || (distance >= (currentSpeed + Street.SAVE_DISTANCE))) {
                currentPosition = currentPosition + currentSpeed;
            } else {
                currentPosition = currentPosition + (distance - Street.SAVE_DISTANCE);
            }
        } else {
            remainingMeters = currentSpeed - metersToGo;
            if ((distance == Street.NO_CAR_IN_FRONT) || (distance >= (metersToGo + Street.SAVE_DISTANCE))) {
                currentPosition = currentPosition + metersToGo;
            } else {
                currentPosition = currentPosition + (distance - Street.SAVE_DISTANCE);
            }
        }
        isMoved = true;
        resetSpeedInTraffic(oldPosition);
    }
    /**
     * Resets the car's is moved attribut to false so in this tick the car hasn't moved so far
     */
    public void resetIsMoved() {
        isMoved = false;
    }

    public void turn(Street newStreet) {
        if (newStreet.getCars().getLast().getCurrentPosition() >= 10) {
            currentPosition = 0;
            currentStreet = newStreet.getStreetID();
        }
    }

    /**
     * Resets the car's speed in traffic to the starting speed. if there's no traffic method does nothing
     * @param oldPosition the old position of the car before the tick
     */
    private void resetSpeedInTraffic(int oldPosition) {
        if (oldPosition == currentPosition) { //TODO das reine Abiegen zählt nicht als Bewegung checken
            currentSpeed = START_SPEED;
        }
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
