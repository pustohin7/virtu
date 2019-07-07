package ru.virtu.systems.query.operator;

/**
 * @author Alexey Pustohin
 */
public class LikeOperator extends BinaryOperator {

    public LikeOperator(String columnName, String argument) {
        super("like", columnName, argument);
    }

    public LikeOperator(String columnName, String argument, LikeDecorator decorator) {
        this(columnName, decorator.decorate(argument));
    }

}
