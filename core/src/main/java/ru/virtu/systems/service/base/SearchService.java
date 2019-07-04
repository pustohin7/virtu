package ru.virtu.systems.service.base;

import ru.virtu.systems.dto.base.BaseEnity;
import ru.virtu.systems.sc.base.BaseSC;

import java.util.List;

/**
 * @author Alexey Pustohin
 */
public interface SearchService<T extends BaseEnity, SC extends BaseSC> {

    List<T> find(SC sc);

    long count(SC sc);

    /**
     * Создать условие поиска сущностей с полями заполненными по умолчанию
     *
     * @return условие поиска
     */
    SC newDefaultSearchCondition();
}
