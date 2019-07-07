package ru.virtu.systems.util.component.date;

import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;

/**
 * @author Alexey Pustohin
 */
public class VSDateField extends DateField {
    public VSDateField(String id) {
        super(id);
    }

    public VSDateField(String id, IModel<Date> model) {
        super(id, model);
    }

    @Override
    protected DateTextField newDateTextField(String id, PropertyModel<Date> dateFieldModel) {
        return DateTextField.forDatePattern(id, dateFieldModel, "dd.MM.yyyy");
    }
}
