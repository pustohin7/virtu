package ru.virtu.systems.base;

import org.springframework.jdbc.core.RowMapper;
import ru.virtu.systems.dto.base.BaseEnity;
import ru.virtu.systems.dto.base.CodeNameDto;
import ru.virtu.systems.util.Codable;

import java.io.FileDescriptor;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Alexey Pustohin
 */
public abstract class BaseRowMapper<T> implements RowMapper<T> {

    protected final <E extends BaseEnity> void mapBaseEntity(E object, ResultSet rs, String prefix) throws SQLException {
        if (prefix == null) {
            prefix = "";
        }

        object.setId(getLong(rs, prefix + "id"));
    }

    protected final <E extends BaseEnity> void mapBaseEntity(E object, ResultSet rs) throws SQLException {
        mapBaseEntity(object, rs, null);
    }

    protected final <E extends CodeNameDto> void mapCodeNameDto(E object, ResultSet rs) throws SQLException {
        mapCodeNameDto(object, rs, null);
    }

    protected final <E extends CodeNameDto> void mapCodeNameDto(E object, ResultSet rs, String prefix) throws SQLException {
        mapBaseEntity(object, rs, prefix);
        if (prefix == null) {
            prefix = "";
        }

        object.setName(getString(rs, prefix + "name"));
        object.setCode(getString(rs, prefix + "code"));
    }

    /*
        Хелперы для начитки колонок
     */

    protected final Integer getInt(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getObject(columnLabel, Integer.class);
    }

    protected final Long getLong(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getObject(columnLabel, Long.class);
    }

    protected final Double getDouble(ResultSet rs, String columnLabel) throws SQLException{
        return rs.getObject(columnLabel, Double.class);
    }

    protected final String getString(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getString(columnLabel);
    }

    protected final BigDecimal getBigDecimal(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getBigDecimal(columnLabel);
    }

    protected final Boolean getBoolean(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getObject(columnLabel, Boolean.class);
    }

    protected final <E> List<E> getList(ResultSet rs, String columnLabel) throws SQLException {
        //noinspection unchecked
        return Arrays.asList((E[]) rs.getArray(columnLabel).getArray());
    }

    protected final Date getDate(ResultSet rs, String columnLabel) throws SQLException {
        Date date = rs.getDate(columnLabel);
        if (date == null) {
            return null;
        }

        return date;
    }

    protected final LocalDate getLocalDate(ResultSet rs, String columnLabel) throws SQLException {
        Date date = rs.getDate(columnLabel);
        if (date == null) {
            return null;
        }

        return date.toLocalDate();
    }

    protected final LocalDateTime getLocalDateTime(ResultSet rs, String columnLabel) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnLabel);
        if (timestamp == null) {
            return null;
        }

        return timestamp.toLocalDateTime();
    }

    protected final <E extends Enum<E> & Codable<String>> E getCodableByString(ResultSet rs, String columnLabel, Class<E> clazz) throws SQLException {
        return Codable.findByCode(clazz, getString(rs, columnLabel));
    }
}
