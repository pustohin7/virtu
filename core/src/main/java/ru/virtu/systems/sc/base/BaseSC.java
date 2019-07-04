package ru.virtu.systems.sc.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseSC implements Serializable {

    private Long offset;
    private Long limit;
    private List<OrderBy> ordering = new ArrayList<>();

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public void orderBy(String property) {
        ordering.add(new OrderBy(property));
    }

    public void orderBy(String property, OrderBy.Ordering ordering) {
        this.ordering.add(new OrderBy(property, ordering));
    }

    public Long getOffset() {
        return offset;
    }

    public Long getLimit() {
        return limit;
    }

    public List<OrderBy> getOrdering() {
        return Collections.unmodifiableList(ordering);
    }

    public void clearOrdering() {
        this.ordering.clear();
    }

}
