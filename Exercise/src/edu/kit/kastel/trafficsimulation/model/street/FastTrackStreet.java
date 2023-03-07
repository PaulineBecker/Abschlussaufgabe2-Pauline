package edu.kit.kastel.trafficsimulation.model.street;

import edu.kit.kastel.trafficsimulation.model.Car;
import edu.kit.kastel.trafficsimulation.model.node.Node;

/**
 * A FastTrackStreet class that extends the Street class and implements the Overtakeable interface.
 * It represents a street where vehicles can overtake each other. This class inherits the attributes
 * and methods from its superclass. Additionally, in this special street cars can overtake each other.
 *
 * @author uyxib
 * @version 1.0
 */
public class FastTrackStreet extends Street implements Overtakeable {
    /**
     * car need more than 20 meter to the end of the street to overtake.
     * If Overtaking is possible, drives 20 m as the simulation of Overtaking.
     */
    private static final int OVERTAKE = 20;
    private static final int MIN_DISTANCE_TWO_CARS_IN_FRONT = 30;
    private static final int TWO_CARS_IN_FRONT = 2;
    private static final int CAR_INT_FRONT = 1;

    /**
     * Constructs a FastTrackStreet object with the specified parameters.
     * @param intStartNode the start node of the street
     * @param intEndNode the end node of the street
     * @param length the length of the street in meters
     * @param speedLimit the speed limit of the street in kilometers per hour
     * @param streetID the ID of the street
     * @param startNode the start node object of the street
     * @param endNode the end node object of the street
     */
    public FastTrackStreet(int intStartNode, int intEndNode, int length, int speedLimit, int streetID,
                           Node startNode, Node endNode) {
        super(intStartNode, intEndNode, length, speedLimit, streetID, startNode, endNode);
    }


    @Override
    public boolean overtakeVehicle(Car car) {
        if (car.getRemainingMeters() < OVERTAKE) {
            return false;
        }
        int currentCarIndex = cars.indexOf(car);
        int metersToGo  = length - car.getCurrentPosition();
        int distanceFirstCar = calculateDistance(currentCarIndex, cars);
        int distanceSecondCar = Street.NO_CAR_IN_FRONT;
        if (currentCarIndex >= TWO_CARS_IN_FRONT) {
            distanceSecondCar = calculateDistance(currentCarIndex - CAR_INT_FRONT, cars);
        }
        if (distanceFirstCar == Street.NO_CAR_IN_FRONT || metersToGo < OVERTAKE) {
            return false;
        } else {
            if (distanceSecondCar == Street.NO_CAR_IN_FRONT || distanceSecondCar >= OVERTAKE) {
                car.setCurrentPosition(car.getCurrentPosition() + OVERTAKE);
                car.setRemainingMeters(car.getRemainingMeters() - OVERTAKE);
                cars.remove(currentCarIndex);
                cars.add(currentCarIndex - CAR_INT_FRONT, car);
                car.drive(calculateDistance(cars.indexOf(car), cars), length, car.getRemainingMeters());
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        boolean hasOvertaken = false;
        int i = 0;
        while (i < cars.size()) {
            Car currentCar = cars.get(i);
            if (!currentCar.isMoved()) {
                currentCar.accelerate(speedLimit);
                currentCar.drive(calculateDistance(i, cars), length, currentCar.getCurrentSpeed());
                if (!overtakeVehicle(currentCar)) {
                    if (!turnCar(currentCar)) {
                        i++;
                    }
                } else {
                    i++;
                    hasOvertaken = true;
                }
                currentCar.resetSpeedInTraffic(hasOvertaken);
            } else {
                i++;
            }

        }
    }
}
