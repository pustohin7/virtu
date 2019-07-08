package ru.virtu.systems.util.component.date;

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

        final IValidator<DateInterval> validator = new DateIntervalValidator();
        if(validator != null) {
            add(validator);
        }
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

    private FormComponent<Date> newDateField(String id, IModel<Date> model) {
        return new VSDateField(id, model).setRequired(DateIntervalComponent.this.isRequired());
    }

    private class DateIntervalValidator implements IValidator<DateInterval> {

        @Override
        public void validate(IValidatable<DateInterval> validatable) {
            DateInterval interval = validatable.getValue();
            LocalDate dateFrom = dateToLocalDate(interval.getFrom());
            LocalDate dateTo = dateToLocalDate(interval.getTo());

            if (dateFrom == null || dateTo == null) {
                ValidationError error = new ValidationError();
                error.addKey("interval.error.empty");
                validatable.error(error);
                return;
            }
            if (isDateFromAfterDateTo(dateFrom, dateTo)) {
                ValidationError error = new ValidationError();
                error.addKey(DateIntervalComponent.class.getSimpleName())
                        .setVariable("from", getString("from"))
                        .setVariable("to", getString("to"));
                validatable.error(error);
            }
            if (dateTo.minusYears(1L).isAfter(dateFrom)) {
                ValidationError error = new ValidationError();
                error.addKey("interval.error.year");
                validatable.error(error);
            }
            if (LocalDate.now().isAfter(dateFrom)) {
                ValidationError error = new ValidationError();
                error.addKey("interval.error.min");
                validatable.error(error);
            }


        }

        private boolean isDateFromAfterDateTo(LocalDate from, LocalDate to) {
            return from != null && to != null && from.isAfter(to);
        }
        private LocalDate dateToLocalDate(Date date) {
            return date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        }
    }
}
