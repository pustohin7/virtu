package ru.virtu.systems.contract;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import ru.virtu.systems.dto.Contract;
import ru.virtu.systems.dto.Insured;
import ru.virtu.systems.util.component.date.VSDateField;
import ru.virtu.systems.util.component.link.VSAjaxLink;

/**
 * @author Alexey Pustohin
 */
public class InsuredPanel extends GenericPanel<Insured> {

    private IModel<Contract> contractIModel;
    public InsuredPanel(String id) {
        super(id);
    }

    public InsuredPanel(String id, IModel<Contract> model) {

        super(id, new CompoundPropertyModel<>(model.getObject().getInsured() != null ? PropertyModel.of(model,"insured")
                : Model.of(new Insured())));
        this.contractIModel = model;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new TextField<>("insuredFio").setEnabled(false));
        add(new VSDateField("birthDate").setEnabled(false));
        add(new TextField<>("docNumber").setEnabled(false));
        add(new TextField<>("docSerial").setEnabled(false));

        add(new VSAjaxLink("selectInsured", this::selectInsured));
        add(new VSAjaxLink("editInsured", this::createInsured) {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                setEnabled(!InsuredPanel.this.getModelObject().isNew());
            }
        });
    }

    private void createInsured(AjaxRequestTarget target) {
        setResponsePage(new InsuredEditPage(contractIModel));
    }

    private void selectInsured(AjaxRequestTarget target) {
        contractIModel.getObject().setInsured(getModelObject());
        setResponsePage(new SearchInsuredPage(contractIModel));
    }


}
