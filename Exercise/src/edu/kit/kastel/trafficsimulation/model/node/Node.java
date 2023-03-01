package edu.kit.kastel.trafficsimulation.model.node;

import edu.kit.kastel.trafficsimulation.exception.SimulationException;
import edu.kit.kastel.trafficsimulation.io.ExceptionMessages;
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

    public void validateCorrespondingStreets(List<Street> streets) {
        if (streets.size() < 1 || streets.size() > 4) {
            throw new SimulationException(ExceptionMessages.INVALID_NUMBER_OF_STREETS.format(nodeID));
        }
    }

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    public List<Street> getIncomingStreets() {
        return incomingStreets;
    }

    public void setIncomingStreets(List<Street> incomingStreets) {
        this.incomingStreets = incomingStreets;
    }

    public List<Street> getOutgoingStreets() {
        return outgoingStreets;
    }

    public void setOutgoingStreets(List<Street> outgoingStreets) {
        this.outgoingStreets = outgoingStreets;
    }
}
