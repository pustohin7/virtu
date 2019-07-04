package ru.virtu.systems.util.component.link;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import ru.virtu.systems.util.functional.SerializableConsumer;

/**
 * @author Alexey Pustohin
 */
public class VSAjaxLink extends AjaxLink{

    private SerializableConsumer<AjaxRequestTarget> onClick;

    public VSAjaxLink(String id) {
        super(id);
        setMarkupId(id);
    }

    public VSAjaxLink(String id, IModel model) {
        super(id, model);
        setMarkupId(id);
    }

    public VSAjaxLink(String id, SerializableConsumer<AjaxRequestTarget> onClick) {
        super(id);
        setMarkupId(id);
        this.onClick = onClick;
    }

    public VSAjaxLink(String id, IModel model, SerializableConsumer<AjaxRequestTarget> onClick) {
        super(id, model);
        setMarkupId(id);
        this.onClick = onClick;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    @Override
    public void onClick(AjaxRequestTarget target) {
        if (onClick == null) {
            throw new IllegalStateException("Override onClick method");
        }

        onClick.accept(target);
    }
}
