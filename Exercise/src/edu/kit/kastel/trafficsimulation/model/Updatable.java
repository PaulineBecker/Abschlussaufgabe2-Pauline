package edu.kit.kastel.trafficsimulation.model;

/**
 * An interface to define objects that can be updated. Implementing classes should provide an implementation for the
 * {@link #update()} method.
 *
 * @author uyxib
 * @version 1.0
 */
public interface Updatable {

    /**
     * Updates the object. Implementing classes should define how the object is updated.
     */
    void update();
}
