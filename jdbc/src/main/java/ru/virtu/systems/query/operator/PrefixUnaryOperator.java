package ru.virtu.systems.query.operator;

/**
 * @author Alexey Pustohin
 */
public class PrefixUnaryOperator extends UnaryOperator {

    public PrefixUnaryOperator(String operator, String columnName) {
        super(operator, columnName);
    }

    @Override
    public String getSql() {
        return operator + " " + columnName;
    }
}
