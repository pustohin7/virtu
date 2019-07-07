package ru.virtu.systems.query.operator;


import ru.virtu.systems.query.SqlQuery;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexey Pustohin
 */
public class BinaryOperator implements SqlOperator {

    protected String columnName;
    protected Object argument;
    private String operator;

    public BinaryOperator(String operator, String columnName, Object argument) {
        this.columnName = columnName;
        this.argument = argument;
        this.operator = operator;
    }

    @Override
    public String getSql() {
        if (argument instanceof SqlQuery) {
            return columnName + " " + operator + " (" + ((SqlQuery) argument).getSql() + ")";
        } else {
            return columnName + " " + operator + " ?";
        }
    }

    @Override
    public List<Object> getArguments() {
        if (argument instanceof SqlQuery) {
            return Arrays.asList(((SqlQuery) argument).getArguments());
        } else {
            return Collections.singletonList(argument);
        }
    }

    @Override
    public boolean includesToQuery() {
        return argument != null;
    }
}
