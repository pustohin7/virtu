package ru.virtu.systems.util.component.table;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import ru.virtu.systems.util.component.paging.PagingToolbar;

import java.io.Serializable;
import java.util.List;

/**
 * @author Alexey Pustohin
 */
public class VSTable<T extends Serializable> extends DataTable<T, String> {
    protected DataTable<T, String> results;

    public VSTable(String id, List<? extends IColumn<T, String>> iColumns, ISortableDataProvider<T, String> dataProvider, long rowsPerPage) {
        super(id, iColumns, dataProvider, rowsPerPage);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        addBottomToolbar(new PagingToolbar(this) {
            @Override
            protected boolean scrollToTopAfterPageSwitch() {
                return VSTable.this.scrollToTopAfterPageSwitch();
            }
        });
        addTopToolbar(new HeadersToolbar<>(this, (ISortableDataProvider<T, String>) getDataProvider()));
        setOutputMarkupId(true);
    }

    protected boolean scrollToTopAfterPageSwitch() {
        return false;
    }
}
