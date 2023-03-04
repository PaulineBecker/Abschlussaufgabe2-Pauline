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
    /**
     * status of the car if it didn't drive and start stauts of the position before setting car on street
     */
    public static final int NO_DRIVE = -1;
    private static final int START_SPEED = 0;
    private static final int START_DESIRED_STREET = 0;
    private static final int NO_REMAINING_METERS = 0;

    private int acceleration;
    private int desiredStreet;
    private final int preferredSpeed;
    private int currentSpeed;
    private int currentStreet;
    private final int carID;
    private int currentPosition;
    private int oldPosition;
    /**
     * remaining meters a car can drive after the end of a street. If remaining meters > 0 check if car can cross street
     */
    private int remainingMeters;
    private boolean isMoved;

    /**
     * Creates a new Car object with the given parameters.
     * @param carID The ID of the car
     * @param currentStreet The current street of the car
     * @param preferredSpeed The preferred speed of the car
     * @param acceleration The acceleration of the car
     */
    public Car(int carID, int currentStreet, int preferredSpeed, int acceleration) {
        this.acceleration = acceleration;
        this.desiredStreet = START_DESIRED_STREET;
        this.currentSpeed = START_SPEED;
        this.currentStreet = currentStreet;
        this.carID = carID;
        this.preferredSpeed = preferredSpeed;
        this.currentPosition = NO_DRIVE;
        this.oldPosition = NO_DRIVE;
        this.remainingMeters = NO_REMAINING_METERS;
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
     * Updates the car's desired street after the car entered a new street after a crossing
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

    /**
     * Increases the speed of the vehicle up to the specified speed limit.
     * The new speed is determined by adding the acceleration to the current speed,
     * then taking the minimum of that value and the speed limit, and finally taking
     * the minimum of that value and the preferred speed.
     * @param speedLimit the maximum speed limit for the vehicle
     */
    public void accelerate(int speedLimit) {
        int newSpeed = Math.min((currentSpeed + acceleration), speedLimit);
        currentSpeed = Math.min(newSpeed, preferredSpeed);
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
     * @param currentSpeed the current speed of the car
     * @param length length of the current street
     */
    public void drive(int distance, int length, int currentSpeed) {
        oldPosition = currentPosition;
        int metersToGo = length - currentPosition;
        if (metersToGo >= currentSpeed) {
            if ((distance == Street.NO_CAR_IN_FRONT) || (distance >= (currentSpeed + Street.SAVE_DISTANCE))) {
                currentPosition = currentPosition + currentSpeed;
            } else {
                currentPosition = currentPosition + (distance - Street.SAVE_DISTANCE);
                remainingMeters = currentSpeed - (currentPosition - oldPosition);
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
    }

    /**
     * Resets the car's is moved attribut to false so in this tick the car hasn't moved so far
     */
    public void resetMoveParameters() {
        isMoved = false;
        remainingMeters = 0;
        oldPosition = -1;
    }

    /**
     * Turns the vehicle onto the specified new street if the conditions are met.
     * The conditions for turning are that the new street must either be empty or the last
     * car on the street must have moved at least 10 meters away, and the remaining distance
     * to travel must be greater than 0.
     * If the vehicle successfully turns onto the new street, its current position is set to 0
     * and its current street is updated to the ID of the new street.
     * @param newStreet the new street to turn onto
     * @return {@code true} if the vehicle successfully turns onto the new street, {@code false} otherwise
     */
    public boolean turn(Street newStreet) {
        if ((newStreet.getCars().isEmpty() || newStreet.getCars().getLast().getCurrentPosition() >= 10)
                && remainingMeters > 0) {
            currentPosition = 0;
            currentStreet = newStreet.getStreetID();
            return true;
        }
        return false;
    }

    /**
     * Resets the car's speed in traffic to the starting speed. if there's no traffic method does nothing
     * @param hasOvertaken true if car has overtaken, otherwise false
     */
    public void resetSpeedInTraffic(boolean hasOvertaken) {
        if (oldPosition == currentPosition && !hasOvertaken) { //TODO das reine Abiegen zählt nicht als Bewegung checken
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
     * Returns the remaining distance in meters.
     * @return the remaining distance in meters
     */
    public int getRemainingMeters() {
        return remainingMeters;
    }

    /**
     * Sets the new reamingMeters that a car has left to drive in one tick.
     * @param remainingMeters the remaining distance in meters
     */
    public void setRemainingMeters(int remainingMeters) {
        this.remainingMeters = remainingMeters;
    }

    /**
     * Returns the old position of the car before moving
     * @return the old position of the car
     */
    public int getOldPosition() {
        return oldPosition;
    }
}
