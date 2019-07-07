package ru.virtu.systems.base;

import org.springframework.transaction.annotation.Transactional;
import ru.virtu.systems.dto.base.BaseEnity;
import ru.virtu.systems.dto.base.CodeNameDto;
import ru.virtu.systems.sc.base.BaseSC;


/**
 * @author Alexey Pustohin
 */
public interface BaseCompoundService<T extends BaseEnity, SC extends BaseSC> extends BaseSearchService<T, SC>, BaseCrudService<T> {

    @Transactional(readOnly = true)
    default <D extends CodeNameDto> boolean isCodeUnique(D object) {
        if (object.isNew()) {
            return !get(object.getCode()).isPresent();
        } else {
            return get(object.getCode())
                    .map(t -> t.getId().equals(object.getId()))
                    .orElse(true);
        }
    }

}
