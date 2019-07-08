package ru.virtu.systems.base;

import org.springframework.transaction.annotation.Transactional;
import ru.virtu.systems.dto.base.BaseEnity;
import ru.virtu.systems.query.SqlQuery;
import ru.virtu.systems.service.base.CrudService;


import java.util.Optional;

/**
 * @author Alexey Pustohin
 */
public interface BaseCrudService<T extends BaseEnity> extends CrudService<T>, ReadingService<T> {

    @Override
    @Transactional(readOnly = true)
    default Optional<T> get(long id) {
        SqlQuery query = new SqlQuery("select * from " + getTableName()).eq("id", id);
        return getJdbcTemplate().queryForOptional(query, getRowMapper());
    }

    @Override
    @Transactional(readOnly = true)
    default Optional<T> get(String code) {
        SqlQuery query = new SqlQuery("select * from " + getTableName()).eq("code", code);
        return getJdbcTemplate().queryForOptional(query, getRowMapper());
    }
}
