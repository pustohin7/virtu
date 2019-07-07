package ru.virtu.systems;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.virtu.systems.base.BaseCrudService;
import ru.virtu.systems.base.BaseSearchService;
import ru.virtu.systems.base.BaseService;
import ru.virtu.systems.dto.Contract;
import ru.virtu.systems.mapper.ContractRowMapper;
import ru.virtu.systems.query.SqlQuery;
import ru.virtu.systems.sc.base.BaseSC;
import ru.virtu.systems.service.ContractService;

/**
 * @author Alexey Pustohin
 */
@Transactional
@Service
public class ContractServiceImpl extends BaseService implements ContractService, BaseSearchService<Contract, BaseSC>, BaseCrudService<Contract> {
    @Override
    public String getTableName() {
        return "contract.contract_v";
    }

    @Override
    public RowMapper<Contract> getRowMapper() {
        return new ContractRowMapper();
    }

    @Override
    public void fillSC(BaseSC sc, SqlQuery query) {

    }

    @Override
    public BaseSC newDefaultSearchCondition() {
        return new BaseSC();
    }

    @Override
    public Contract save(Contract object) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
