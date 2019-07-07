package ru.virtu.systems.util.component.validation;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import java.util.regex.Pattern;

/**
 * @author Alexey Pustohin
 */
public class DocNumberValidator implements IValidator<Integer> {
    private static Pattern PATTERN = Pattern.compile("^\\d{4}$");
    @Override
    public void validate(IValidatable<Integer> validatable) {
        Integer docNumber = validatable.getValue();
        if (!PATTERN.matcher(docNumber.toString()).matches()) {
            final ValidationError error = new ValidationError();
            error.addKey(getClass().getSimpleName());
            validatable.error(error);
        }
    }
}
