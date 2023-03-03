package edu.kit.kastel.trafficsimulation.model.node;

/**
 * A class representing an intersection in a traffic simulation, which extends the abstract Node class.
 * An intersection has a traffic light, a current green street, and can be updated.
 *
 * @author uyxib
 * @version 1.0
 */
public class Intersection extends Node {

    private TrafficLight trafficLight;

    /**
     * Creates a new Intersection object with the specified node ID and green phase duration.
     * @param nodeID the ID of the intersection
     * @param greenPhaseDuration the duration of the green phase for the traffic light
     */
    public Intersection(int nodeID, int greenPhaseDuration) {
        super(nodeID, START_STREET);
        trafficLight = new TrafficLight(greenPhaseDuration);
    }

    /**
     * Updates the intersection by updating its traffic light and changing the current green street if necessary.
     */
    @Override
    public void update() {
        trafficLight.update();
        if (trafficLight.isGreenPhaseOver()) {
            changeCurrentGreenStreet();
        }
    }

    /**
     * checks if the given street has green and cars can drive or not
     * @param streetID the current incoming street of a node
     * @return true if the current incoming street has green and cars can drive, otherwise, return false
     */
    @Override
    public boolean hasStreetGreen(int streetID) {
        if (getIncomingStreets().get(currentGreenStreet).getStreetID() == streetID) {
            return true;
        }
        return false;
    }

    /**
     * Changes the current green street to the next street in the list, wrapping around if necessary.
     */
    private void changeCurrentGreenStreet() {
        if (currentGreenStreet < MAXIMUM_STREETS) {
            currentGreenStreet++;
        } else if (currentGreenStreet == MAXIMUM_STREETS) {
            currentGreenStreet = START_STREET;
        }
    }
}
