package edu.kit.kastel.trafficsimulation.model.node;

import edu.kit.kastel.trafficsimulation.exception.SimulationException;
import edu.kit.kastel.trafficsimulation.io.ExceptionMessages;
import edu.kit.kastel.trafficsimulation.model.Updatable;
import edu.kit.kastel.trafficsimulation.model.street.Street;

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
    public static final int MAXIMUM_STREETS = 3;
    /**
     * The maximum size of incoming / outgoing streets a node can have.
     */
    public static final int MAXIMUM_STREETS_SIZE = 4;
    /**
     * The minimum size of incoming / outgoing streets a node can have.
     */
    public static final int MINIMUM_STREETS_SIZE = 1;

    /**
     * The starting street index for a node.
     */
    public static final int START_STREET = 0;

    /**
     * the id of the current street that is green
     */
    protected int currentGreenStreet;
    /**
     * the id of the node
     */
    protected int nodeID;
    /**
     * list with all incoming Streets on the node (min 1 - max 4)
     */
    protected List<Street> incomingStreets;
    /**
     * list with all outgoing Streets on the node (min 1 - max 4)
     */
    protected List<Street> outgoingStreets;


    /**
     * Creates a new Node object with the specified ID and empty lists of incoming and outgoing streets.
     *
     * @param nodeID the ID of the node
     */
    protected Node(int nodeID) {
        this.nodeID = nodeID;
        this.incomingStreets = new ArrayList<>();
        this.outgoingStreets = new ArrayList<>();
        currentGreenStreet = START_STREET;
    }

    /**
     * Returns whether the specified street has a green light at this node.
     * @param streetID the ID of the street
     * @return true if the specified street has a green light, false otherwise
     */
    public abstract boolean hasStreetGreen(int streetID);


    /**
     * Validates the corresponding streets for this node, ensuring that it has between 1 and 4 streets.
     *
     * @throws SimulationException if the number of streets is invalid
     * @param streets the list of streets to validate
     */
    public void validateCorrespondingStreets(List<Street> streets) {
        if (streets.size() < MINIMUM_STREETS_SIZE || streets.size() > MAXIMUM_STREETS_SIZE) {
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

    /**
     * Returns the ID of the current green street at this node.
     * @return the ID of the current green street
     */
    public int getCurrentGreenStreet() {
        return currentGreenStreet;
    }

    /**
     * Sets the ID of the current green street at this node.
     * @param currentGreenStreet the ID of the new current green street
     */
    public void setCurrentGreenStreet(int currentGreenStreet) {
        this.currentGreenStreet = currentGreenStreet;
    }
}
