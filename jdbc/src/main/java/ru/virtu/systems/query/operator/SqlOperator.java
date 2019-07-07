package ru.virtu.systems.query.operator;

import java.io.Serializable;
import java.util.List;

/**
 * @author Alexey Pustohin
 */
public interface SqlOperator extends Serializable {

    String getSql();

    List<Object> getArguments();

    default boolean includesToQuery() {
        return true;
    }

}
