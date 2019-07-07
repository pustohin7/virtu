package ru.virtu.systems.util.component.paging;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.WebMarkupContainer;
import ru.virtu.systems.util.model.LambdaModel;

/**
 * @author Alexey Pustohin
 */
public class StatelessPagingToolbar extends AbstractToolbar {

    public StatelessPagingToolbar(DataTable<?, ?> table) {
        super(table);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        WebMarkupContainer rowToSpan = new WebMarkupContainer("rowToSpan");
        rowToSpan.add(new AttributeModifier(
                "colspan",
                new LambdaModel<>(getTable().getColumns()::size)
        ));
        add(rowToSpan);

        rowToSpan.add(new StatelessPagingPanel<DataTable>("pagingPanel", getTable()) {
            @Override
            protected Component[] getComponentsToUpdate() {
                return new Component[] { getTable() };
            }

            @Override
            protected boolean scrollToTopAfterPageSwitch() {
                return StatelessPagingToolbar.this.scrollToTopAfterPageSwitch();
            }
        });
    }

    protected boolean scrollToTopAfterPageSwitch() {
        return false;
    }

}