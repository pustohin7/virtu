package ru.virtu.systems.contract;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidator;
import ru.virtu.systems.base.ContainerPage;
import ru.virtu.systems.dto.Calculation;
import ru.virtu.systems.dto.Contract;
import ru.virtu.systems.dto.base.DateInterval;
import ru.virtu.systems.service.ContractService;
import ru.virtu.systems.util.component.date.VSDateField;
import ru.virtu.systems.util.component.form.VSFeedback;
import ru.virtu.systems.util.component.link.StatelessAjaxSubmitLink;
import ru.virtu.systems.util.component.link.VSAjaxLink;
import ru.virtu.systems.util.component.validation.ContractNumberValidator;
import ru.virtu.systems.util.model.LongPPModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Alexey Pustohin
 */
public class ContractPage extends ContainerPage {
    @SpringBean
    private ContractService contractService;

    private Form<Contract> form;
    private IModel<Contract> contractIModel;

    private LongPPModel idModel;
    public ContractPage() {
        contractIModel = Model.of(getContractDefaultInit());
    }

    public ContractPage(PageParameters pageParameters) {
        super(pageParameters);
        createModels();
    }

    public ContractPage(IModel<Contract> contractIModel) {
        this.contractIModel = contractIModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        IModel<Contract> contract = contractIModel != null ? contractIModel : Model.of(getContractById(idModel.getObject()));
        form = new Form<>("form", new CompoundPropertyModel<>(contract));
        add(form);

        VSFeedback feedback = new VSFeedback("feedback");
        form.add(feedback);

        form.add(new CalculationPanel("calculationPanel", Model.of(contract)));

        form.add(new InsuredPanel("insuredPanel", Model.of(contract)));

        form.add(new TextField<>("contractNo").setRequired(true).add(new IValidator[]{new ContractNumberValidator()}));
        form.add(new VSDateField("createDate").setEnabled(false));


        form.add(new RequiredTextField("address.country"));
        form.add(new RequiredTextField<>("address.region"));
        form.add(new TextField<>("address.district"));
        form.add(new RequiredTextField<>("address.city"));
        form.add(new RequiredTextField<>("address.street"));
        form.add(new TextField<>("address.house"));
        form.add(new TextField<>("address.postcode"));
        form.add(new RequiredTextField<>("address.apartment"));
        form.add(new TextField<>("address.letter"));
        form.add(new TextField<>("address.housing"));

        form.add(new TextArea<>("additionalInfo"));

        form.add(new StatelessAjaxSubmitLink("save", form){
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                Contract contractModelForm = (Contract) form.getModelObject();
                if (contractModelForm.getInsured() == null) {
                    form.error(getString("insured.required"));
                    target.add(feedback);
                    return;
                }
                try {
                    contractService.save(contractModelForm);
                    setResponsePage(ContractsPage.class);
                } catch (Exception e) {
                    form.error(e.getMessage());
                    target.add(feedback);
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedback);
            }
        });
        form.add(new VSAjaxLink("goContractsPage", this::goContractsPage));
    }

    private void goContractsPage(AjaxRequestTarget target) {
        setResponsePage(ContractsPage.class);
    }

    private Contract getContractById(Long id) {
        Supplier<Contract> constructor = Contract::new;
        return Optional.ofNullable(id)
                .map(contractService::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .orElse(constructor.get());
    }

    private void createModels() {
        idModel = new LongPPModel(getPage(), "id");
    }

    private Contract getContractDefaultInit(){
        Contract contract = new Contract();
        Date now = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        contract.setCreateDate(now);
        DateInterval dateInterval = new DateInterval();
        dateInterval.setFrom(now);
        contract.setCalculation(new Calculation());
        contract.getCalculation().setDateInterval(dateInterval);
        return contract;
    }
}
