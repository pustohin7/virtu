package ru.virtu.systems.contract;

import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.virtu.systems.base.ContainerPage;
import ru.virtu.systems.dto.Calculation;
import ru.virtu.systems.dto.Contract;
import ru.virtu.systems.service.ContractService;
import ru.virtu.systems.util.model.LongPPModel;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Alexey Pustohin
 */
public class ContractPage extends ContainerPage {
    @SpringBean
    private ContractService contractService;

    private StatelessForm<Contract> form;

    private LongPPModel idModel;
    public ContractPage() {
    }

    public ContractPage(PageParameters pageParameters) {
        super(pageParameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        createModels();

        Contract contract = getContractById(idModel.getObject());
        form = new StatelessForm<>("form", new CompoundPropertyModel<>(contract));
        add(form);

        form.add(new CalculationPanel("calculationPanel", PropertyModel.of(contract, "calculation")));
        form.add(new TextField<>("contractNo"));

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
}
