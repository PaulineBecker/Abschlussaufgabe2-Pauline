package edu.kit.kastel.trafficsimulation.model.road;

import edu.kit.kastel.trafficsimulation.model.node.Node;

/**
 * A FastTrackStreet class that extends the Street class and implements the Overtakable interface.
 * It represents a street where vehicles can overtake each other. This class inherits the attributes
 * and methods from its superclass. Additionally, in this special street cars can overtake each other.
 *
 * @author uyxib
 * @version 1.0
 */
public class FastTrackStreet extends Street implements Overtakable {

    /**
     * Constructs a FastTrackStreet object with the specified parameters.
     * @param intstartNode the start node of the street
     * @param intEndNode the end node of the street
     * @param length the length of the street in meters
     * @param speedLimit the speed limit of the street in kilometers per hour
     * @param streetID the ID of the street
     * @param startNode the start node object of the street
     * @param endNode the end node object of the street
     */
    public FastTrackStreet(int intstartNode, int intEndNode, int length, int speedLimit, int streetID,
                           Node startNode, Node endNode) {
        super(intstartNode, intEndNode, length, speedLimit, streetID, startNode, endNode);
    }


    @Override
    public void overtakeVehicle() {

    }
}
