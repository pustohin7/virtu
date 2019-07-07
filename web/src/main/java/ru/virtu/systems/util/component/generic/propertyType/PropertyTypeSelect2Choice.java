package ru.virtu.systems.util.component.generic.propertyType;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ru.virtu.systems.dto.PropertyType;
import ru.virtu.systems.dto.base.BaseEnity;
import ru.virtu.systems.service.PropertyTypeService;
import ru.virtu.systems.util.component.select.VSSelect2Choice;

/**
 * @author Alexey Pustohin
 */
public class PropertyTypeSelect2Choice<T extends BaseEnity> extends VSSelect2Choice<PropertyType> {

    @SpringBean
    private PropertyTypeService propertyTypeService;

    public PropertyTypeSelect2Choice(String id) {
        this(id, null);
    }

    public PropertyTypeSelect2Choice(String id, IModel<PropertyType> model) {
        super(id, model);

        vsInit();

        getSettings().setAllowClear(false);
        getSettings().setPlaceholder("Выберите тип");
        setProvider(new PropertyTypeProvider(propertyTypeService));
    }

}
