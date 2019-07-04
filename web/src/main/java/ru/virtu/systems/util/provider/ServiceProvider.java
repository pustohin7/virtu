package ru.virtu.systems.util.provider;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import ru.virtu.systems.dto.base.BaseEnity;
import ru.virtu.systems.sc.base.BaseSC;
import ru.virtu.systems.sc.base.OrderBy;
import ru.virtu.systems.service.base.SearchService;


import java.util.Iterator;

/**
 * @author Alexey Pustohin
 */
public class ServiceProvider<T extends BaseEnity, SC extends BaseSC> extends SortableDataProvider<T, String> {

    private SearchService<T, SC> searchService;
    private IModel<SC> scModel;

    public ServiceProvider(SearchService<T, SC> searchService, IModel<SC> scModel) {
        this.searchService = searchService;
        this.scModel = scModel;
    }

    @Override
    public Iterator<? extends T> iterator(long first, long count) {
        SC sc = scModel.getObject();
        sc.setOffset(first);
        sc.setLimit(count);

        SortParam<String> sortParam = getSort();
        if (sortParam != null) {
            sc.clearOrdering();
            sc.orderBy(sortParam.getProperty(), sortParam.isAscending() ? OrderBy.Ordering.ASC : OrderBy.Ordering.DESC);
        }

        return searchService.find(sc).iterator();
    }

    @Override
    public long size() {
        return searchService.count(scModel.getObject());
    }

    @Override
    public IModel<T> model(T object) {
        return Model.of(object);
    }

}
