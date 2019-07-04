package ru.virtu.systems.base;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @author Alexey Pustohin
 */
public class AbstractSearcPanel<T> extends Panel {

    private Form<T> searchForm;
    private DataTable<T, String> resultTable;
    public AbstractSearcPanel(String id) {
        super(id);
    }

    public AbstractSearcPanel(String id, IModel<?> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        resultTable = createResultTable();
        resultTable.setOutputMarkupId(true);
        add(resultTable);
    }

    protected DataTable<T, String> createResultTable() {
        return null;
    }
}
