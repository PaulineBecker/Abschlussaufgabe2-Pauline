package edu.kit.kastel.trafficsimulation.model.node;

/**
 * The Roundabout class represents a node that is also a roundabout. It extends the Node class and implements
 * the hasStreetGreen() method to always return true because every car can drive all the time through the node.
 *
 * @author uyxib
 * @version 1.0
 */
public class Roundabout extends Node {

    /**
     * Constant value representing all streets on the Roundabout are green.
     */
    public static final int ALL_STREETS_GREEN = -1;

    /**
     * Constructs a Roundabout object with the specified node ID and sets all the streets as green.
     * @param nodeID the ID of the roundabout node
     */
    public Roundabout(int nodeID) {
        super(nodeID, ALL_STREETS_GREEN);
    }

    /**
     * Does nothing as Roundabouts do not need to update.
     */
    @Override
    public void update() {
    }

    /**
     * Returns true as all streets on a Roundabout are always green.
     * @param streetID the ID of the street
     * @return true as all streets on a Roundabout are always green
     */
    @Override
    public boolean hasStreetGreen(int streetID) {
        return true;
    }
}
