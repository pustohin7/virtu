package ru.virtu.systems.contract;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.virtu.systems.base.ContainerPage;
import ru.virtu.systems.dto.Contract;
import ru.virtu.systems.dto.Insured;
import ru.virtu.systems.sc.InsuredSC;
import ru.virtu.systems.service.InsuredService;
import ru.virtu.systems.util.component.link.StatelessAjaxSubmitLink;
import ru.virtu.systems.util.component.link.VSAjaxLink;
import ru.virtu.systems.util.component.table.VSTable;
import ru.virtu.systems.util.provider.ServiceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Alexey Pustohin
 */
public class SearchInsuredPage extends ContainerPage {

    @SpringBean
    private InsuredService insuredService;

    private IModel<InsuredSC> conditionModel;
    private IModel<Contract> contractIModel;

    private Insured selectedInsured;

    public SearchInsuredPage(IModel<Contract> contractIModel) {
        this.contractIModel = contractIModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        conditionModel = new CompoundPropertyModel<>(insuredService.newDefaultSearchCondition());
        Form form = new Form<>("searchForm", conditionModel);
        form.add(new TextField<>("firstName"));
        form.add(new TextField<>("lastName"));
        form.add(new TextField<>("middleName"));

        add(form.setOutputMarkupId(true));

        VSAjaxLink selectLink = new VSAjaxLink("select", this::selectInsured) {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                setEnabled(selectedInsured != null);
            }
        };
        form.add(selectLink);
        List<IColumn<Insured, String>> columns = new ArrayList<>();
        columns.add(new PropertyColumn<>(Model.of("ФИО"), "insuredFio"));
        columns.add(new PropertyColumn<>(Model.of("Дата рождения"), "birthDate"));
        columns.add(new PropertyColumn<>(Model.of("Паспортные данные"), "documentFull"));

        ServiceProvider<Insured, InsuredSC> insuredProvider = new ServiceProvider<>(insuredService, conditionModel);

        VSTable<Insured> table = new VSTable<Insured>("table", columns, insuredProvider, 10) {

            @Override
            protected Item<Insured> newRowItem(String id, int index, IModel model) {
                Item item = super.newRowItem(id, index, model);
                item.add(new AjaxEventBehavior("click") {
                             @Override
                             protected void onEvent(AjaxRequestTarget target) {
                                 selectedInsured = (Insured) model.getObject( );
                                 target.add(selectLink);
                             }
                         }
                );
                return item;
            }
        };
        add(table);
        StatelessAjaxSubmitLink searchLink = new StatelessAjaxSubmitLink("searchLink", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                target.add(table);
            }
        };
        form.add(searchLink);
        form.setDefaultButton(searchLink);

        form.add(new VSAjaxLink("createLink", this::createInsured));
        form.add(new VSAjaxLink("closeLink", this::close));
    }

    private void close(AjaxRequestTarget target) {
        setResponsePage(new ContractPage(contractIModel));
    }

    private void createInsured(AjaxRequestTarget target) {
        setResponsePage(new InsuredEditPage(contractIModel));
    }

    private void selectInsured(AjaxRequestTarget target) {
        Insured insured =  Optional.ofNullable(selectedInsured.getId())
                .map(insuredService::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .orElse(new Insured());
        contractIModel.getObject().setInsured(insured);
        setResponsePage(new ContractPage(contractIModel));
    }
}
