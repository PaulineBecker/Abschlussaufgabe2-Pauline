package edu.kit.kastel.trafficsimulation.model.node;

import edu.kit.kastel.trafficsimulation.exception.SimulationException;
import edu.kit.kastel.trafficsimulation.io.ExceptionMessages;
import edu.kit.kastel.trafficsimulation.model.Updatable;
import edu.kit.kastel.trafficsimulation.model.road.Street;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class representing a node in a transportation network that implements the Updatable interface.
 * A node has an ID, incoming streets, and outgoing streets.
 *
 * @author uyxib
 * @version 1.0
 */
public abstract class Node implements Updatable {
    /**
     * The maximum number of streets a node can have.
     */
    public static final int MAXIMUM_STREETS = 4;

    /**
     * The starting street index for a node.
     */
    public static final int START_STREET = 0;

    private int nodeID;
    private List<Street> incomingStreets;
    private List<Street> outgoingStreets;
    protected int currentGreenStreet;

    /**
     * Creates a new Node object with the specified ID and empty lists of incoming and outgoing streets.
     *
     * @param nodeID the ID of the node
     */
    public Node(int nodeID, int START_STREET) {
        this.nodeID = nodeID;
        this.incomingStreets = new ArrayList<>();
        this.outgoingStreets = new ArrayList<>();
        currentGreenStreet = START_STREET;
    }


    /**
     * Validates the corresponding streets for this node, ensuring that it has between 1 and 4 streets.
     * Throws a SimulationException if the number of streets is invalid.
     *
     * @throws SimulationException if the number of streets is invalid
     * @param streets the list of streets to validate
     */
    public void validateCorrespondingStreets(List<Street> streets) {
        if (streets.size() < 1 || streets.size() > 4) {
            throw new SimulationException(ExceptionMessages.INVALID_NUMBER_OF_STREETS.format(nodeID));
        }
    }

    /**
     * Returns the ID of this node.
     * @return the ID of the node
     */
    public int getNodeID() {
        return nodeID;
    }

    /**
     * Returns the incoming streets of this node.
     * @return the incoming streets of the node
     */

    public List<Street> getIncomingStreets() {
        return incomingStreets;
    }

    /**
     * Returns the outgoing streets of this node.
     * @return the outgoing streets of the node
     */

    public List<Street> getOutgoingStreets() {
        return outgoingStreets;
    }

    public int getCurrentGreenStreet() {
        return currentGreenStreet;
    }

    public void setCurrentGreenStreet(int currentGreenStreet) {
        this.currentGreenStreet = currentGreenStreet;
    }
}
