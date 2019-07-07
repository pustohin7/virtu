package ru.virtu.systems.query.operator;

import java.util.Collections;
import java.util.List;

/**
 * @author Alexey Pustohin
 */
abstract class UnaryOperator implements SqlOperator {

    protected String operator;
    protected String columnName;

    public UnaryOperator(String operator, String columnName) {
        this.operator = operator;
        this.columnName = columnName;
    }

    @Override
    public List<Object> getArguments() {
        return Collections.emptyList();
    }

}
