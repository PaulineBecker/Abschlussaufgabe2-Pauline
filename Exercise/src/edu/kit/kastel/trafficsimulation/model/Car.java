package edu.kit.kastel.trafficsimulation.model;

import edu.kit.kastel.trafficsimulation.io.Messages;

/**
 * models a car in the trafficsimulation
 * a car has a specific desired speed, can accelerate, has a current speed, a current location with the position
 * and the street id and know if it already moved in one tick or not
 *
 * @author uyxib
 * @version 1.0
 */
public class Car {

    private int acceleration;
    private int desiredSpeed;
    private int currentSpeed;
    private int currentStreet;
    private int carID;
    private int currentPosition;
    private boolean isMoved;

    /**
     * creates a String where the car is located with the car ID, currentStreet, currentSpeed and the currentPosition
     * @return the string output to the user that explains where the car is located
     */
    @Override
    public String toString() {
        return Messages.CAR_POSITION.format(this.carID, this.currentStreet, this.currentSpeed, this.currentPosition);
    }

}
