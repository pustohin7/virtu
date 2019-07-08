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
        mapBaseEntityInsured(insured, resultSet);
//        mapBaseEntity(insured, resultSet, "insured_");
        insured.setBirthDate(getDate(resultSet, "birth_date"));
        insured.setFirstName(getString(resultSet, "first_name"));
        insured.setLastName(getString(resultSet, "last_name"));
        insured.setMiddleName(getString(resultSet, "middle_name"));
        insured.setDocNumber(getInt(resultSet,"doc_number"));
        insured.setDocSerial(getInt(resultSet,"doc_serial"));
        insured.setInsuredFio(mapSummaryFio(resultSet));
        insured.setDocumentFull(mapDocumentFull(insured));
        return insured;
    }

    private String mapDocumentFull(Insured insured) {
        StringBuilder builder = new StringBuilder()
                .append(insured.getDocNumber())
                .append(StringUtils.SPACE)
                .append(insured.getDocSerial());
        return builder.toString();
    }

    private String mapSummaryFio(ResultSet resultSet) throws SQLException {
        StringBuilder builder = new StringBuilder()
                .append(getString(resultSet, "last_name"))
                .append(StringUtils.SPACE)
                .append(getString(resultSet, "first_name"))
                .append(StringUtils.SPACE)
                .append(getString(resultSet, "middle_name") != null ?
                        getString(resultSet, "middle_name") : StringUtils.EMPTY);

        return builder.toString();
    }

    protected void mapBaseEntityInsured(Insured insured, ResultSet resultSet) throws SQLException {
        mapBaseEntity(insured, resultSet);
    }
}
