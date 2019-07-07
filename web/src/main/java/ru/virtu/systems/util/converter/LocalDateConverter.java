package ru.virtu.systems.util.converter;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Alexey Pustohin
 */
public class LocalDateConverter implements IConverter<LocalDate> {

    private final DateTimeFormatter formatter;
    private final String pattern;

    public LocalDateConverter() {
        this("dd.MM.yyyy");
    }

    public LocalDateConverter(String pattern) {
        this.pattern = Args.notEmpty(pattern, "pattern");
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    protected LocalDate doConvertToObject(String value, Locale locale) {
        return LocalDate.parse(value, this.formatter);
    }

    protected String doConvertToString(LocalDate value, Locale locale) {
        return this.formatter.format(value);
    }

    @Override
    public LocalDate convertToObject(String value, Locale locale) throws ConversionException {
        if (Strings.isEmpty(value)) {
            return null;
        } else {
            try {
                return this.doConvertToObject(value, locale);
            } catch (RuntimeException var4) {
                throw this.newConversionException(var4);
            }
        }
    }

    @Override
    public String convertToString(LocalDate localDate, Locale locale) {
        try {
            return this.doConvertToString(localDate, locale);
        } catch (RuntimeException var4) {
            throw this.newConversionException(var4);
        }
    }

    private ConversionException newConversionException(RuntimeException cause) {
        return (new ConversionException(cause)).setVariable("format", this.pattern);
    }
}
