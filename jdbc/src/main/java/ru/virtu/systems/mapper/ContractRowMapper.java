package ru.virtu.systems.mapper;

import org.springframework.lang.Nullable;
import ru.virtu.systems.base.BaseRowMapper;
import ru.virtu.systems.dto.Calculation;
import ru.virtu.systems.dto.Contract;
import ru.virtu.systems.dto.Insured;
import ru.virtu.systems.dto.PropertyType;
import ru.virtu.systems.dto.base.DateInterval;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Alexey Pustohin
 */
public class ContractRowMapper extends BaseRowMapper<Contract> {
    @Nullable
    @Override
    public Contract mapRow(ResultSet resultSet, int i) throws SQLException {
        Contract contract = new Contract();
        mapBaseEntity(contract, resultSet);
        contract.setContractNo(getInt(resultSet, "contract_no"));
        contract.setCreateDate(getDate(resultSet, "create_date"));
        contract.setInsured(new InsuredRowMapper() {
            @Override
            protected void mapBaseEntityInsured(Insured insured, ResultSet resultSet) throws SQLException {
                mapBaseEntity(insured, resultSet, "insured_");
            }
        }.mapRow(resultSet, i));
        contract.setValidaty(getString(resultSet, "validaty"));
        contract.setCalculation(mapCalculation(resultSet));
        contract.setAdditionalInfo(getString(resultSet, "additional_info"));

        return contract;
    }

    private Calculation mapCalculation(ResultSet resultSet) throws SQLException {
        Calculation calculation = new Calculation();
        calculation.setPremium(getDouble(resultSet, "premium"));
        calculation.setCalculationDate(getDate(resultSet, "calculation_date"));
        calculation.setConstructionYear(getInt(resultSet, "construction_year"));

        PropertyType propertyType = new PropertyType();
        mapCodeNameDto(propertyType, resultSet, "property_type_");

        calculation.setPropertyType(propertyType);
        calculation.setSumInsured(getDouble(resultSet, "insured_sum"));
        calculation.setSquare(getDouble(resultSet,"square"));
        DateInterval dateInterval = new DateInterval();
        dateInterval.setFrom(getDate(resultSet, "date_from"));
        dateInterval.setTo(getDate(resultSet, "date_to"));
        calculation.setDateInterval(dateInterval);

        return calculation;
    }
}
