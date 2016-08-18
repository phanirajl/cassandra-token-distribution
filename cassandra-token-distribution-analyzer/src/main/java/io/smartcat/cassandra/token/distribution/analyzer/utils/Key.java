package io.smartcat.cassandra.token.distribution.analyzer.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a key in Cassandra table.
 */
public class Key {

    /**
     * List of key elements.
     */
    private List<KeyElement> elements = new ArrayList<KeyElement>();

    /**
     * Adds one element to the key.
     * 
     * @param element Element to be added
     */
    public void addElement(KeyElement element) {
        elements.add(element);
    }

    /**
     * Get all key elements.
     * 
     * @return List of key elements
     */
    public List<KeyElement> getElements() {
        return elements;
    }

}
