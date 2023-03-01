package edu.kit.kastel.trafficsimulation.model;

import edu.kit.kastel.trafficsimulation.io.Messages;
import edu.kit.kastel.trafficsimulation.model.node.Node;

/**
 * models a car in the traffic simulation
 * a car has a specific desired speed, can accelerate, has a current speed, a current location with the position
 * and the street id and know if it already moved in one tick or not
 *
 * @author uyxib
 * @version 1.0
 */
public class Car implements Updatable {
    private static final int START_SPEED = 0;

    private int acceleration;
    private int desiredStreet;
    private int currentSpeed;
    private int currentStreet;
    private int carID;
    private int currentPosition;
    private boolean isMoved;

    public Car(int acceleration, int desiredStreet, int currentStreet, int carID, int currentPosition) {
        this.acceleration = acceleration;
        this.desiredStreet = desiredStreet;
        this.currentSpeed = START_SPEED;
        this.currentStreet = currentStreet;
        this.carID = carID;
        this.currentPosition = currentPosition;
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

    @Override
    public void update() {
        changeDesiredStreet();
    }

    private void changeDesiredStreet() {
        if (desiredStreet < Node.MAXIMUM_STREETS) {
            desiredStreet++;
        } else if (desiredStreet == Node.MAXIMUM_STREETS) {
            desiredStreet = Node.START_STREET;
        }
    }

    private void accelerate(){
        //TODO
    }

    private void resetSpeedInTraffic() {
        currentSpeed = START_SPEED;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }

    public int getDesiredStreet() {
        return desiredStreet;
    }

    public void setDesiredStreet(int desiredStreet) {
        this.desiredStreet = desiredStreet;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public int getCurrentStreet() {
        return currentStreet;
    }

    public void setCurrentStreet(int currentStreet) {
        this.currentStreet = currentStreet;
    }

    public int getCarID() {
        return carID;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public boolean isMoved() {
        return isMoved;
    }

    public void setMoved(boolean moved) {
        isMoved = moved;
    }
}
