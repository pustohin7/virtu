package ru.virtu.systems.base;

import org.springframework.jdbc.core.RowMapper;

import ru.virtu.systems.VSJdbcTemplate;
import ru.virtu.systems.dto.base.BaseEnity;

/**
 * @author Alexey Pustohin
 */
interface ReadingService<T extends BaseEnity> {

    VSJdbcTemplate getJdbcTemplate();

    String getTableName();

    RowMapper<T> getRowMapper();

}
