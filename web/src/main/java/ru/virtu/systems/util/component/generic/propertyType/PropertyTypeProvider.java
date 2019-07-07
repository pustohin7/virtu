package ru.virtu.systems.util.component.generic.propertyType;


import ru.virtu.systems.dto.PropertyType;
import ru.virtu.systems.sc.base.Select2SC;
import ru.virtu.systems.service.PropertyTypeService;
import ru.virtu.systems.util.component.select.provider.Select2ChoiceServiceProvider;

/**
 * @author Alexey Pustohin
 */
public class PropertyTypeProvider extends Select2ChoiceServiceProvider<PropertyType, Select2SC> {
    public PropertyTypeProvider(PropertyTypeService service) {
        super(service);
    }

    @Override
    protected String getDisplayText(PropertyType region) {
        return region.getName();
    }
}
