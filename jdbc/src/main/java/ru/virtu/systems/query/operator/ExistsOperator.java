package ru.virtu.systems.query.operator;

import ru.virtu.systems.query.SqlQuery;

import java.util.Arrays;
import java.util.List;

/**
 * @author Alexey Pustohin
 */
public class ExistsOperator implements SqlOperator {

    private SqlQuery query;

    public ExistsOperator(SqlQuery query) {
        this.query = query;
    }

    @Override
    public String getSql() {
        return "exists(" + query.getSql() + ")";
    }

    @Override
    public List<Object> getArguments() {
        return Arrays.asList(query.getArguments());
    }
}
