package ru.virtu.systems.mapper;

import org.springframework.lang.Nullable;
import ru.virtu.systems.base.BaseRowMapper;
import ru.virtu.systems.dto.Contract;

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
        contract.setContractNo(getString(resultSet, "contract_no"));
        contract.setCreateDate(getLocalDate(resultSet, "create_date"));
        contract.setPremium(getBigDecimal(resultSet, "premium"));
        contract.setInsured(new InsuredRowMapper().mapRow(resultSet, i));
        contract.setValidaty(getString(resultSet, "validaty"));

        return contract;
    }
}
