package ru.virtu.systems.util.component.date;

import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import ru.virtu.systems.dto.base.DateInterval;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Alexey Pustohin
 */
public class DateIntervalComponent extends FormComponentPanel<DateInterval> {

/*    private DateField from;
    private DateField to;

    public DateIntervalComponent(String id) {
        super(id);
    }

    public DateIntervalComponent(String id, IModel<DateInterval> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        from = new DateField("from");
        add(from);
        to = new DateField("to");
        add(to);
    }*/


    protected FormComponent<Date> from;
    protected FormComponent<Date> to;

    public DateIntervalComponent(String id) {
        this(id, null);
    }

    public DateIntervalComponent(String id, IModel<DateInterval> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setType(DateInterval.class);

        from = newDateField("from", new Model<>());
        add(from);

        to = newDateField("to", new Model<>());
        add(to);

        final IValidator<DateInterval> validator = newDefaultValidator();
        if(validator != null) {
            add(validator);
        }
    }

    protected IValidator<DateInterval> newDefaultValidator() {
        return new DateIntervalBoundsValidator();
    }

    public FormComponent<Date> getFrom() {
        return from;
    }

    public FormComponent<Date> getTo() {
        return to;
    }

    @Override
    protected void onBeforeRender() {
        DateInterval interval = getModelObject();
        if (interval == null) {
            from.setModelObject(null);
            to.setModelObject(null);
            from.clearInput();
            to.clearInput();
        } else {
            from.setModelObject(interval.getFrom());
            to.setModelObject(interval.getTo());
        }
        super.onBeforeRender();
    }

    @Override
    public void convertInput() {
        DateInterval interval = new DateInterval();
        interval.setFrom(from.getConvertedInput());
        interval.setTo(to.getConvertedInput());
        if (interval.getFrom() == null && interval.getTo() == null) {
            interval = null;
        }
        setConvertedInput(interval);
    }


    @Override
    public void updateModel() {
        DateInterval edited = getConvertedInput();

        DateInterval current = getModelObject();
        if (current != null) {
            current.setFrom(edited == null ? null : edited.getFrom());
            current.setTo(edited == null ? null : edited.getTo());
            setModelObject(current);
        } else {
            setModelObject(edited);
        }
        super.updateModel();
    }


    /*
     * Фабричный метод для создания компоненты редактирования даты.
     * По умолчанию возвращает компонент с ограниченем даты не больше сегодняшней.
     *
     * @param id    ид компонента
     * @param model модель
     * @return компонент редактирования даты
      */
    protected FormComponent<Date> newDateField(String id, IModel<Date> model) {
        return new DateField(id, model).setRequired(DateIntervalComponent.this.isRequired());
    }

    /**
     * Выполняет проверку на то что значение 'с' не больше 'по'
     */
    private class DateIntervalBoundsValidator implements IValidator<DateInterval> {
        private static final long serialVersionUID = 2848256289590917121L;

        @Override
        public void validate(IValidatable<DateInterval> validatable) {
            DateInterval interval = validatable.getValue();

            if (isDateFromAfterDateTo(interval.getFrom(), interval.getTo())) {

                ValidationError error = new ValidationError();
                error.addKey(DateIntervalComponent.class.getSimpleName())
                        .setVariable("from", extractLabel(DateIntervalComponent.this.from.getLabel()))
                        .setVariable("to", extractLabel(DateIntervalComponent.this.to.getLabel()));
                validatable.error(error);
            }
        }

        private boolean isDateFromAfterDateTo(Date from, Date to) {
            LocalDate dateFrom = from != null ? from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
            LocalDate dateTo = to != null ? to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
            return dateFrom != null && dateTo != null && dateFrom.isAfter(dateTo);
        }

        private String extractLabel(IModel<String> label) {
            return label != null ? label.getObject() : null;
        }
    }

/*    public class IntervalDatePicker extends PtsLocalDatePicker {
        public IntervalDatePicker(String id, IModel<LocalDate> model) {
            super(id, model);
        }

        @Override
        protected void onConfigure() {
            super.onConfigure();
            setRequired(DateIntervalComponent.this.isRequired());
        }

    }*/
}
