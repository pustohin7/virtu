package ru.virtu.systems.util.model;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.string.StringValueConversionException;

/**
 * @author Alexey Pustohin
 */
public class PPModel<T> implements IModel<T> {

    private Page page;
    private Class<T> clazz;
    private String parameterName;
    private T defaultValue;

    public PPModel(Page page, Class<T> clazz, String parameterName) {
        this(page, clazz, parameterName, null);
    }

    public PPModel(Page page, Class<T> clazz, String parameterName, T defaultValue) {
        this.page = page;
        this.clazz = clazz;
        this.parameterName = parameterName;
        this.defaultValue = defaultValue;
    }

    @Override
    public final T getObject() {
        StringValue stringValue = page.getPageParameters().get(parameterName);
        if (stringValue.isEmpty()) {
            return defaultValue;
        }

        try {
            return deserialize(stringValue);
        } catch (StringValueConversionException e) {
            return defaultValue;
        }
    }

    @Override
    public final void setObject(T object) {
        Object value = serialize(object);
        page.getPageParameters().set(parameterName, value);

        if (updateUrl()) {
            AjaxRequestTarget target = RequestCycle.get().find(AjaxRequestTarget.class);
            if (target != null) {
                String stringValue = value != null ? value.toString() : "";
                target.appendJavaScript(String.format(
                        "Wicket.FTUtils.updatePageParameter('%s', %s);",
                        parameterName, !stringValue.isEmpty() ? "'" + stringValue + "'" : null
                ));
            }
        }
    }

    protected boolean updateUrl() {
        return true;
    }

    @Override
    public void detach() {
        // do nothing
    }

    protected T deserialize(StringValue stringValue) throws StringValueConversionException {
        return stringValue.to(clazz);
    }

    protected Object serialize(T object) {
        return object;
    }

}
