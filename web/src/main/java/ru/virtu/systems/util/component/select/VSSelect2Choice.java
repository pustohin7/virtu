package ru.virtu.systems.util.component.select;

import com.vaynberg.wicket.select2.ChoiceProvider;
import com.vaynberg.wicket.select2.Select2Choice;
import org.apache.wicket.model.IModel;

/**
 * @author Alexey Pustohin
 */
public class VSSelect2Choice<T> extends Select2Choice<T> implements IVSSelect2Choice {

    public VSSelect2Choice(String id, IModel<T> model, ChoiceProvider<T> provider) {
        super(id, model, provider);
        setMarkupId(id);
    }

    public VSSelect2Choice(String id, IModel<T> model) {
        super(id, model);
        setMarkupId(id);
    }

    public VSSelect2Choice(String id) {
        super(id);
        setMarkupId(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        vsInit();
    }
}
