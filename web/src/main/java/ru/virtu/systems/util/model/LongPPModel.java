package ru.virtu.systems.util.model;

import org.apache.wicket.Page;

/**
 * @author Alexey Pustohin
 */
public class LongPPModel extends PPModel<Long> {

    public LongPPModel(Page page, String parameterName) {
        super(page, Long.class, parameterName);
    }

    public LongPPModel(Page page, String parameterName, Long defaultValue) {
        super(page, Long.class, parameterName, defaultValue);
    }

}
