package ru.virtu.systems.util.functional;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * @author Alexey Pustohin
 */
@FunctionalInterface
public interface SerializableConsumer<T> extends Consumer<T>, Serializable {
}
