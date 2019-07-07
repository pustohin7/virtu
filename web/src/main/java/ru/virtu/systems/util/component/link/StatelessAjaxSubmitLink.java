package ru.virtu.systems.util.component.link;

import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;

/**
 * @author Alexey Pustohin
 */
public class StatelessAjaxSubmitLink extends AjaxSubmitLink {

    public StatelessAjaxSubmitLink(String id) {
        super(id);
    }

    public StatelessAjaxSubmitLink(String id, Form<?> form) {
        super(id, form);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setMarkupId(getId());
    }

    @Override
    protected boolean getStatelessHint() {
        return true;
    }
}