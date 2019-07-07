package ru.virtu.systems.service;

import ru.virtu.systems.dto.PropertyType;
import ru.virtu.systems.sc.base.Select2SC;
import ru.virtu.systems.service.base.CrudService;
import ru.virtu.systems.service.base.SearchService;

/**
 * @author Alexey Pustohin
 */
public interface PropertyTypeService extends SearchService<PropertyType, Select2SC>, CrudService<PropertyType> {
}
