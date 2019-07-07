package ru.virtu.systems.contract;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidator;
import ru.virtu.systems.base.ContainerPage;
import ru.virtu.systems.dto.Contract;
import ru.virtu.systems.dto.Insured;
import ru.virtu.systems.service.InsuredService;
import ru.virtu.systems.util.component.date.VSDateField;
import ru.virtu.systems.util.component.form.VSFeedback;
import ru.virtu.systems.util.component.link.StatelessAjaxSubmitLink;
import ru.virtu.systems.util.component.link.VSAjaxLink;
import ru.virtu.systems.util.component.validation.DocNumberValidator;
import ru.virtu.systems.util.component.validation.DocSerialValidator;

/**
 * @author Alexey Pustohin
 */
public class InsuredEditPage extends ContainerPage {
    @SpringBean
    private InsuredService insuredService;

    private IModel<Contract> contractIModel;
    public InsuredEditPage(IModel<Contract> model) {
        this.contractIModel = model;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        Form form = new Form<>("form", new CompoundPropertyModel<>(contractIModel.getObject().getInsured() != null ?
                PropertyModel.of(contractIModel, "insured") : Model.of(new Insured())));
        add(form.setOutputMarkupId(true));

        VSFeedback feedback = new VSFeedback("feedback");
        form.add(feedback);

        form.add(new RequiredTextField<>("firstName"));
        form.add(new RequiredTextField<>("lastName"));
        form.add(new TextField<>("middleName"));
        form.add(new VSDateField("birthDate").setRequired(true));
        form.add(new RequiredTextField<>("docNumber").add(new IValidator[]{new DocNumberValidator()}));
        form.add(new RequiredTextField<>("docSerial").add(new IValidator[]{new DocSerialValidator()}));

        form.add(new StatelessAjaxSubmitLink("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
               Insured insured = insuredService.save((Insured)form.getModelObject());

               contractIModel.getObject().setInsured(insured);
               setResponsePage(new ContractPage(contractIModel));
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedback);
            }
        });

        form.add(new VSAjaxLink("cancel", this::backPage));
    }

    private void backPage(AjaxRequestTarget target) {
        setResponsePage(new ContractPage(contractIModel));
    }
}
