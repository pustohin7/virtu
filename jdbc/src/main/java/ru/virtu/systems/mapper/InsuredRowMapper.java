package ru.virtu.systems.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import ru.virtu.systems.base.BaseRowMapper;
import ru.virtu.systems.dto.Insured;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Alexey Pustohin
 */
public class InsuredRowMapper extends BaseRowMapper<Insured> {
    @Nullable
    @Override
    public Insured mapRow(ResultSet resultSet, int i) throws SQLException {
        Insured insured = new Insured();
        mapBaseEntity(insured, resultSet, "insured_");
        insured.setBirthDate(getLocalDate(resultSet, "birth_date"));
        insured.setFirstName(getString(resultSet, "first_name"));
        insured.setLastName(getString(resultSet, "last_name"));
        insured.setMiddleName(getString(resultSet, "middle_name"));
        insured.setSummaryFio(mapSummaryFio(resultSet));
        return insured;
    }

    private String mapSummaryFio(ResultSet resultSet) throws SQLException {
        StringBuilder builder = new StringBuilder()
                .append(getString(resultSet, "last_name"))
                .append(StringUtils.SPACE)
                .append(getString(resultSet, "first_name"))
                .append(StringUtils.SPACE)
                .append(getString(resultSet, "middle_name"));

        return builder.toString();
    }
}
