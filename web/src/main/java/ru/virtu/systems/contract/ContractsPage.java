package ru.virtu.systems.contract;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.virtu.systems.base.ContainerPage;
import ru.virtu.systems.dto.Contract;
import ru.virtu.systems.sc.base.BaseSC;
import ru.virtu.systems.service.ContractService;
import ru.virtu.systems.util.component.link.VSAjaxLink;
import ru.virtu.systems.util.component.table.VSTable;
import ru.virtu.systems.util.provider.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Pustohin
 */
public class ContractsPage extends ContainerPage {
    @SpringBean
    private ContractService contractService;
    private Contract selectedContract;

    private VSAjaxLink openContract;
    private VSAjaxLink createContract;

    public ContractsPage() {
    }

    public ContractsPage(PageParameters pageParameters) {
        super(pageParameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        List<IColumn<Contract, String>> columns = new ArrayList<>();
        columns.add(new PropertyColumn<>(Model.of("Серия-Номер"), "contractNo"));
        columns.add(new PropertyColumn<>(Model.of("Дата заключения"), "createDate"));
        columns.add(new PropertyColumn<>(Model.of("Страхователь"), "insured.summaryFio"));
        columns.add(new PropertyColumn<>(Model.of("Премия"), "premium"));
        columns.add(new PropertyColumn<>(Model.of("Срок действия"),"validaty"));

        ServiceProvider<Contract, BaseSC> contractProvider = new ServiceProvider<>(contractService, Model.of(new BaseSC()));

        VSTable<Contract> table = new VSTable<Contract>("contractTable", columns, contractProvider, 10) {

            @Override
            protected Item<Contract> newRowItem(String id, int index, IModel model) {
                Item item = super.newRowItem(id, index, model);
                item.add(new AjaxEventBehavior("click") {
                             @Override
                             protected void onEvent(AjaxRequestTarget target) {
                                 selectedContract = (Contract)model.getObject( );
                                 target.add(openContract, createContract);
                             }

                             @Override
                             public boolean getStatelessHint(Component component) {
                                 return true;
                             }
                         }
                );
                return item;
            }
        };
        add(table);
        openContract = new VSAjaxLink("openContract", this::openContract) {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                setEnabled(selectedContract != null);
            }
        };
        add(openContract);
        createContract = new VSAjaxLink("createContract", this::createContract);
        add(createContract);
    }

    private void createContract(AjaxRequestTarget target) {
        setResponsePage(ContractPage.class);
    }

    private void openContract(AjaxRequestTarget target) {
        PageParameters pageParameters = new PageParameters();
        pageParameters.add("id", selectedContract.getId());
        setResponsePage(ContractPage.class, pageParameters);
    }
}
