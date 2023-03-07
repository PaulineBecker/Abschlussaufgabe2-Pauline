package edu.kit.kastel.trafficsimulation.model.street;

import edu.kit.kastel.trafficsimulation.model.Car;

/**
 * An interface representing a vehicle that can overtake another vehicle.
 * To implement this interface, a class must define the overtakeVehicle method, which
 * takes a Car object and returns a boolean value indicating whether the Overtake was successful.
 *
 * @author uyxib
 * @version 1.0
 */
public interface Overtakeable {


    /**
     * Overtakes the given car, if possible.
     * @param car the car to overtake.
     * @return true if the Overtake was successful, false otherwise.
     */
    boolean overtakeVehicle(Car car);
}
