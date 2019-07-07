package ru.virtu.systems.util.component.validation;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import java.util.regex.Pattern;

/**
 * @author Alexey Pustohin
 */
public class ContractNumberValidator implements IValidator<Integer> {
    private static Pattern PATTERN = Pattern.compile("^\\d{6}$");
    @Override
    public void validate(IValidatable<Integer> validatable) {
        Integer contractNo = validatable.getValue();
        if (!PATTERN.matcher(contractNo.toString()).matches()) {
            final ValidationError error = new ValidationError();
            error.addKey(getClass().getSimpleName());
            validatable.error(error);
        }
    }
}
