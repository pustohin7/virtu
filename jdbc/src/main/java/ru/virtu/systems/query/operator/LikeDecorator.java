package ru.virtu.systems.query.operator;


import ru.virtu.systems.util.functional.SerializableFunction;

/**
 * @author Alexey Pustohin
 */
public enum LikeDecorator {
    ANYLEFT(s -> "%" + s),
    ANYRIGHT(s -> s + "%"),
    ANYBOTH(s -> "%" + s + "%")
    ;

    private SerializableFunction<String, String> decorator;

    LikeDecorator(SerializableFunction<String, String> decorator) {
        this.decorator = decorator;
    }

    public String decorate(String argument) {
        if (argument == null) {
            return null;
        }

        return decorator.apply(argument);
    }
}