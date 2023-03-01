package edu.kit.kastel.trafficsimulation.model.node;

import edu.kit.kastel.trafficsimulation.model.Updatable;
import edu.kit.kastel.trafficsimulation.model.road.Street;

import java.util.ArrayList;
import java.util.List;

/**
 * @author uyxib
 * @version 1.0
 */
public abstract class Node implements Updatable {

    public static final int MAXIMUM_STREETS = 4;
    public static final int START_STREET = 0;

    private int nodeID;
    private List<Street> incomingStreets;
    private List<Street> outgoingStreets;

    public Node(int nodeID) {
        this.nodeID = nodeID;
        this.incomingStreets = new ArrayList<>();
        this.outgoingStreets = new ArrayList<>();
    }
}
