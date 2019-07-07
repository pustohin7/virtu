package ru.virtu.systems.base;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.virtu.systems.dto.base.BaseEnity;
import ru.virtu.systems.query.SqlQuery;
import ru.virtu.systems.sc.base.BaseSC;
import ru.virtu.systems.service.base.SearchService;


import java.util.List;

/**
 * @author Alexey Pustohin
 */
public interface BaseSearchService<T extends BaseEnity, SC extends BaseSC> extends SearchService<T, SC>, ReadingService<T> {

    @Override
    @Transactional(readOnly = true)
    default List<T> find(SC sc) {
        SqlQuery query = new SqlQuery("select * from " + getTableName());
        fillDeletableSC(sc, query);
        fillPaging(sc, query);
        fillSC(sc, query);

        return getJdbcTemplate().query(query, getRowMapper());
    }

    @Override
    @Transactional(readOnly = true)
    default long count(SC sc) {
        SqlQuery query = new SqlQuery("select count(*) from " + getTableName());
        fillDeletableSC(sc, query);
        fillSC(sc, query);

        return getJdbcTemplate().queryForObject(query, Long.class);
    }

    void fillPaging(BaseSC sc, SqlQuery query);

    void fillDeletableSC(BaseSC sc, SqlQuery query);

    default void fillSC(SC sc, SqlQuery query) {

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    SC newDefaultSearchCondition();
}
