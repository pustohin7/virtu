package ru.virtu.systems.query.operator;


import ru.virtu.systems.query.SqlQuery;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Alexey Pustohin
 */
public class InOperator implements SqlOperator {

    private String columnName;

    private SqlQuery nestedQuery;
    private List<Object> arguments;

    public InOperator(String columnName, Collection<?> arguments) {
        this.columnName = columnName;
        if (arguments != null) {
            this.arguments = arguments.stream().filter(Objects::nonNull).collect(Collectors.toList());
        }
    }

    public InOperator(String columnName, SqlQuery nestedQuery) {
        this.columnName = columnName;
        this.nestedQuery = nestedQuery;
    }

    @Override
    public String getSql() {
        if (arguments != null) {
            StringBuilder qmBuilder = new StringBuilder();
            String prefix = "";
            for (int i = 0; i < arguments.size(); i++) {
                qmBuilder.append(prefix).append("?");
                prefix = ", ";
            }

            return columnName + " in (" + qmBuilder.toString() + ")";
        } else if (nestedQuery != null) {
            return columnName + " in (" + nestedQuery.getSql() + ")";
        }

        // Сюда никогда не дойдет :)
        return "1 = 1";
    }

    @Override
    public List<Object> getArguments() {
        if (arguments != null) {
            return arguments;
        } else if (nestedQuery != null) {
            return Arrays.asList(nestedQuery.getArguments());
        }

        // Сюда никогда не дойдет :)
        return Collections.emptyList();
    }

    @Override
    public boolean includesToQuery() {
        return nestedQuery != null || arguments != null && arguments.size() > 0;
    }
}
