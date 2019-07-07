package ru.virtu.systems.query.operator;

/**
 * @author Alexey Pustohin
 */
public class CaseInsensitiveLikeOperator extends LikeOperator {

    public CaseInsensitiveLikeOperator(String columnName, String argument) {
        super(columnName, argument);
    }

    public CaseInsensitiveLikeOperator(String columnName, String argument, LikeDecorator decorator) {
        super(columnName, argument, decorator);
    }

    @Override
    public String getSql() {
        return "lower(" + columnName + ") like lower(?)";
    }
}
