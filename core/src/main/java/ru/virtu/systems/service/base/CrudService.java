package ru.virtu.systems.service.base;


import ru.virtu.systems.dto.base.BaseEnity;

import java.util.Optional;

/**
 * @author Alexey Pustohin
 */
public interface CrudService<T extends BaseEnity> {

    Optional<T> get(long id);

    Optional<T> get(String code);

    T save(T object);

    void delete(long id);

}
