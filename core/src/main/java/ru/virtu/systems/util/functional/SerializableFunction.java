package ru.virtu.systems.util.functional;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author Alexey Pustohin
 */
@FunctionalInterface
public interface SerializableFunction<T, R> extends Function<T, R>, Serializable {
}
