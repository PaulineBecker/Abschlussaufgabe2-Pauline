package edu.kit.kastel.trafficsimulation.model.node;

/**
 * @author uyxib
 * @version 1.0
 */
public class Roundabout extends Node {

    public static final int ALL_STREETS_GREEN = -1;


    public Roundabout(int nodeID) {
        super(nodeID, ALL_STREETS_GREEN);
    }

    @Override
    public void update() {
    }
}
