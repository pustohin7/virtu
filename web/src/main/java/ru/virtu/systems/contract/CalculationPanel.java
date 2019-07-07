package ru.virtu.systems.contract;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.virtu.systems.dto.Calculation;
import ru.virtu.systems.dto.Contract;
import ru.virtu.systems.dto.PropertyType;
import ru.virtu.systems.service.CalculationService;
import ru.virtu.systems.util.component.date.DateIntervalComponent;
import ru.virtu.systems.util.component.date.VSDateField;
import ru.virtu.systems.util.component.form.VSFeedback;
import ru.virtu.systems.util.component.generic.propertyType.PropertyTypeSelect2Choice;
import ru.virtu.systems.util.component.link.StatelessAjaxSubmitLink;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Alexey Pustohin
 */
public class CalculationPanel extends GenericPanel<Calculation> {
    @SpringBean
    private CalculationService calculationService;
    private IModel<Contract> contractIModel;

    public CalculationPanel(String id) {
        super(id);
    }

    public CalculationPanel(String id, IModel<Contract> model) {
        super(id, new CompoundPropertyModel<>(model.getObject().getCalculation() != null ? PropertyModel.of(model,"calculation") : Model.of(new Calculation())));
        this.contractIModel = model;
        contractIModel.getObject().setCalculation(getModelObject());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        Form<Calculation> form = new Form<>("form", PropertyModel.of(contractIModel, "calculation"));
        add(form.setOutputMarkupId(true));

        VSFeedback feedback = new VSFeedback("feedback");
        form.add(feedback);

        RequiredTextField<BigDecimal> sumInsured = new RequiredTextField<>("sumInsured");
        form.add(sumInsured);

        form.add(new DateIntervalComponent("dateInterval").setRequired(true));

        form.add(new PropertyTypeSelect2Choice<PropertyType>("propertyType").setRequired(true));
        form.add(new TextField<Integer>("constructionYear").setRequired(true));
        form.add(new TextField<Double>("square").setRequired(true));
        VSDateField calculationDate = new VSDateField("calculationDate");
        form.add(calculationDate.setOutputMarkupId(true).setEnabled(false));
        TextField<Double> premiumTextField = new TextField<>("premium");
        form.add(premiumTextField.setOutputMarkupId(true).setEnabled(false));

        form.add(new StatelessAjaxSubmitLink("calculateButton", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                Calculation calculation = (Calculation) form.getModelObject();
                Double premium;
                try {
                    premium = calculationService.calculatePremium(calculation);
                } catch (Exception e) {
                    form.error(e.getMessage());
                    target.add(feedback);
                    return;
                }
                calculation.setPremium(premium);
                calculation.setCalculationDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                contractIModel.getObject().setCalculation(calculation);
                target.add(feedback, calculationDate, premiumTextField);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target, form);
                target.add(feedback);
            }
        });
    }
}
