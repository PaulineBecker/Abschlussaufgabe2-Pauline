package edu.kit.kastel.trafficsimulation.model.road;

/**
 * @author uyxib
 * @version 1.0
 */
public class FastTrackStreet extends Street implements Overtakable {

    public FastTrackStreet(int startNode, int endNode, int length, int speedLimit, int streetID) {
        super(startNode, endNode, length, speedLimit, streetID);
    }

    @Override
    public void overtakeVehicle() {

    }
}
