package edu.kit.kastel.trafficsimulation.model.node;

/**
 * @author uyxib
 * @version 1.0
 */
public class Intersection extends Node {

    private TrafficLight trafficLight;
    private int currentGreenStreet;

    public Intersection(int nodeID, int greenPhaseDuration) {
        super(nodeID);
        currentGreenStreet = START_STREET;
        trafficLight = new TrafficLight(greenPhaseDuration);
    }


    @Override
    public void update() {
        trafficLight.update();
        if(trafficLight.isGreenPhaseOver()) {
            changeCurrentGreenStreet();
        }
    }

    private void changeCurrentGreenStreet() {
        if (currentGreenStreet < MAXIMUM_STREETS) {
            currentGreenStreet++;
        } else if (currentGreenStreet == MAXIMUM_STREETS) {
            currentGreenStreet = START_STREET;
        }
    }
}
