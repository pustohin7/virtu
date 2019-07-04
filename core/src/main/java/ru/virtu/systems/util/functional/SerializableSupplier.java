package ru.virtu.systems.util.functional;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * @author Alexey Pustohin
 */
@FunctionalInterface
public interface SerializableSupplier<T> extends Supplier<T>, Serializable {
}
