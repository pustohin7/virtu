package ru.virtu.systems.util;

import java.util.Arrays;

/**
 * @author Alexey Pustohin
 */
public interface Codable<T> {

    T getCode();

    static <T, E extends Enum<E> & Codable<T>> E findByCode(Class<E> clazz, T code) {
        if (code == null) {
            return null;
        }

        return Arrays.stream(clazz.getEnumConstants())
                .filter(v -> code.equals(v.getCode()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Value for code is not set"));
    }

}
