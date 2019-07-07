package ru.virtu.systems.util.component.select.provider;

import com.vaynberg.wicket.select2.Response;
import com.vaynberg.wicket.select2.TextChoiceProvider;
import ru.virtu.systems.dto.base.BaseEnity;
import ru.virtu.systems.sc.base.Select2SC;
import ru.virtu.systems.service.base.SearchService;

import java.util.Collection;
import java.util.List;

/**
 * @author Alexey Pustohin
 */
public abstract class Select2ChoiceServiceProvider<T extends BaseEnity, SC extends Select2SC> extends TextChoiceProvider<T> {

    private SearchService<T, SC> service;

    public Select2ChoiceServiceProvider(SearchService<T, SC> service) {
        this.service = service;
    }

    @Override
    protected Object getId(T choice) {
        return choice.getId();
    }

    @Override
    public void query(String term, int page, Response<T> response) {
        SC sc = getSC();
        sc.setTerm(term);
        sc.setOffset(page * 10L);
        sc.setLimit(10L);

        long count = service.count(sc);
        List<T> res = service.find(sc);

        response.addAll(res);
        response.setHasMore(count > (page + 1) * 10);
    }

    @Override
    public Collection<T> toChoices(Collection<String> collection) {
        SC sc = getSC();
        sc.setIds(collection);
        return service.find(sc);
    }

    protected SC getSC() {
        return service.newDefaultSearchCondition();
    }

}
