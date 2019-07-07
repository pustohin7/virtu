package ru.virtu.systems.query.operator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Pustohin
 */
public class DirectConditionOperator implements SqlOperator {

    private String condition;

    public DirectConditionOperator(String condition) {
        this.condition = condition;
    }

    @Override
    public String getSql() {
        return condition;
    }

    @Override
    public List<Object> getArguments() {
        return new ArrayList<>();
    }

}
