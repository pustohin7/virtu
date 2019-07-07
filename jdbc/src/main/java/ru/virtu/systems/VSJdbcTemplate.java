package ru.virtu.systems;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.virtu.systems.query.SqlQuery;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

/**
 * @author Alexey Pustohin
 */
public class VSJdbcTemplate extends JdbcTemplate {

    public VSJdbcTemplate() {
    }

    public VSJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public VSJdbcTemplate(DataSource dataSource, boolean lazyInit) {
        super(dataSource, lazyInit);
    }

    public <T> List<T> query(SqlQuery query, RowMapper<T> rowMapper) throws DataAccessException {
        return query(query.getSql(), rowMapper, query.getArguments());
    }

    public <T> List<T> queryForList(SqlQuery query, Class<T> elementType) throws DataAccessException {
        return queryForList(query.getSql(), elementType, query.getArguments());
    }

    public <T> T queryForObject(SqlQuery query, RowMapper<T> rowMapper) throws DataAccessException {
        return queryForObject(query.getSql(), rowMapper, query.getArguments());
    }

    public <T> T queryForObject(SqlQuery query, Class<T> requiredType) throws DataAccessException {
        return queryForObject(query.getSql(), requiredType, query.getArguments());
    }

    public <T> Optional<T> queryForOptional(SqlQuery query, RowMapper<T> rowMapper) throws DataAccessException {
        try {
            return Optional.of(queryForObject(query, rowMapper));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    public <T> Optional<T> queryForOptional(SqlQuery query, Class<T> requiredType) throws DataAccessException {
        try {
            return Optional.of(queryForObject(query, requiredType));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

}