package ru.virtu.systems.sc.base;

import java.io.Serializable;

/**
 * @author Alexey Pustohin
 */
public class OrderBy implements Serializable {

    private String property;
    private Ordering ordering;

    public OrderBy(String property, Ordering ordering) {
        this.property = property;
        this.ordering = ordering;
    }

    public OrderBy(String property) {
        this.property = property;
        this.ordering = Ordering.DESC;
    }

    public String getProperty() {
        return property;
    }

    public Ordering getOrdering() {
        return ordering;
    }

    public static enum Ordering {
        ASC, DESC
    }

}
