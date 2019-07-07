package ru.virtu.systems.contract;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import ru.virtu.systems.dto.Calculation;
import ru.virtu.systems.util.component.date.DateIntervalComponent;
import ru.virtu.systems.util.component.form.VSFeedback;
import ru.virtu.systems.util.component.link.StatelessAjaxSubmitLink;

import java.math.BigDecimal;

/**
 * @author Alexey Pustohin
 */
public class CalculationPanel extends GenericPanel<Calculation> {
    public CalculationPanel(String id) {
        super(id);
    }

    public CalculationPanel(String id, IModel<Calculation> model) {
        super(id, new CompoundPropertyModel<>(model.getObject() != null ? model : Model.of(new Calculation())));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        StatelessForm<Calculation> form = new StatelessForm<>("form", getModel());
        add(form.setOutputMarkupId(true));

        VSFeedback feedback = new VSFeedback("feedback");
        form.add(feedback);

        RequiredTextField<BigDecimal> sumInsured = new RequiredTextField<>("sumInsured");
        form.add(sumInsured);

        form.add(new DateIntervalComponent("dateInterval").setRequired(true));

        form.add(new StatelessAjaxSubmitLink("submitButton", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                Calculation calculation = (Calculation) form.getModelObject();
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target, form);
                target.add(feedback);
            }
        });
    }
}
