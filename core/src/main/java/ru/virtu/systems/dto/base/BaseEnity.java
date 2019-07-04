package ru.virtu.systems.dto.base;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Alexey Pustohin
 */
public class BaseEnity implements Serializable {

    private Long id;

    public boolean isNew() {
        return id == null || id < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEnity baseEnity = (BaseEnity) o;
        return Objects.equals(id, baseEnity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
