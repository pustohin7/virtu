package ru.virtu.systems.util.converter;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * @author Alexey Pustohin
 */
public class LocalDateConverter implements IConverter<Date> {

    private final DateTimeFormatter formatter;
    private final String pattern;

    public LocalDateConverter() {
        this("dd.MM.yyyy");
    }

    public LocalDateConverter(String pattern) {
        this.pattern = Args.notEmpty(pattern, "pattern");
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    protected Date doConvertToObject(String value, Locale locale) {
        LocalDate localDate = LocalDate.parse(value, this.formatter);

        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    protected String doConvertToString(Date value, Locale locale) {
        return this.formatter.format(value.toInstant());
    }

    @Override
    public Date convertToObject(String value, Locale locale) throws ConversionException {
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
    public String convertToString(Date localDate, Locale locale) {
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
