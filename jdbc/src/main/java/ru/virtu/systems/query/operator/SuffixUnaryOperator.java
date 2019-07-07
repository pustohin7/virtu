package ru.virtu.systems.query.operator;

/**
 * @author Alexey Pustohin
 */
public class SuffixUnaryOperator extends UnaryOperator {

    public SuffixUnaryOperator(String operator, String columnName) {
        super(operator, columnName);
    }

    @Override
    public String getSql() {
        return columnName + " " + operator;
    }

}
