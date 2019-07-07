package ru.virtu.systems.base;

import ru.virtu.systems.VSJdbcTemplate;
import ru.virtu.systems.query.SqlQuery;
import ru.virtu.systems.query.operator.LikeDecorator;
import ru.virtu.systems.sc.base.BaseSC;
import ru.virtu.systems.sc.base.IDeletableSC;
import ru.virtu.systems.sc.base.ITermSC;
import ru.virtu.systems.sc.base.Select2SC;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.Serializable;

/**
 * @author Alexey Pustohin
 */
public abstract class BaseService implements Serializable {

    protected VSJdbcTemplate jdbcTemplate;

    @Resource
    public final void setDataSource(DataSource dataSource) {
        jdbcTemplate = new VSJdbcTemplate(dataSource);
    }

    public final VSJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public final void fillPaging(BaseSC sc, SqlQuery query) {
        query.limit(sc.getLimit())
                .offset(sc.getOffset())
                .orderBy(sc.getOrdering());
    }

    public final void fillTermSC(ITermSC sc, SqlQuery query) {
        fillTermSC(sc, query, "name");
    }

    public final void fillTermSC(ITermSC sc, SqlQuery query, String... termColumns) {
        for (String termColumn : termColumns) {
            query.ilike(termColumn, sc.getTerm(), LikeDecorator.ANYBOTH);
        }
    }

    public final void fillSelect2SC(Select2SC sc, SqlQuery query) {
        fillSelect2SC(sc, query, "id::varchar", new String[] { "name" });
    }

    public final void fillSelect2SC(Select2SC sc, SqlQuery query, String... termColumns) {
        fillSelect2SC(sc, query, "id::varchar", termColumns);
    }

    public final void fillSelect2SC(Select2SC sc, SqlQuery query, String idColumn, String... termColumns) {
        query.in(idColumn, sc.getIds());
        fillTermSC(sc, query, termColumns);
    }

}