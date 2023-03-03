package edu.kit.kastel.trafficsimulation.model.road;

import edu.kit.kastel.trafficsimulation.model.Car;

/**
 * @author uyxib
 * @verion 1.0
 */
public interface Overtakable {

    public boolean overtakeVehicle(Car car);
}
