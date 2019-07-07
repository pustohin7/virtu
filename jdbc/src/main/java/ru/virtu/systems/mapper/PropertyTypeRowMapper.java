package ru.virtu.systems.mapper;

import org.springframework.lang.Nullable;
import ru.virtu.systems.base.BaseRowMapper;
import ru.virtu.systems.dto.PropertyType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Alexey Pustohin
 */
public class PropertyTypeRowMapper extends BaseRowMapper<PropertyType> {
    @Nullable
    @Override
    public PropertyType mapRow(ResultSet resultSet, int i) throws SQLException {
        PropertyType propertyType = new PropertyType();
        mapCodeNameDto(propertyType, resultSet);
        return propertyType;
    }
}
