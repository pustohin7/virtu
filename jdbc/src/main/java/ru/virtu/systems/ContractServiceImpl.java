package ru.virtu.systems;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.virtu.systems.base.BaseCrudService;
import ru.virtu.systems.base.BaseSearchService;
import ru.virtu.systems.base.BaseService;
import ru.virtu.systems.dto.Address;
import ru.virtu.systems.dto.Calculation;
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
    public void fillDeletableSC(BaseSC sc, SqlQuery query) {

    }

    @Override
    public void fillSC(BaseSC sc, SqlQuery query) {

    }

    @Override
    public BaseSC newDefaultSearchCondition() {
        return new BaseSC();
    }

    @Override
    @Transactional
    public Contract save(Contract contract) {
        Long id;
        Calculation calculation = contract.getCalculation();
        Address address = contract.getAddress();
        if (contract.isNew()) {
            id =  jdbcTemplate.queryForObject("insert into contract.contract(contract_no, insured_id, create_date, date_from, date_to, premium," +
                            "property_type_id, insured_sum, square, construction_year, calculation_date, additional_info, country, region, district, " +
                            "   city, street, postcode, housing, letter, house, apartment)" +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id", Long.class, contract.getContractNo(),
                    contract.getInsured().getId(), contract.getCreateDate(), calculation.getDateInterval().getFrom(), calculation.getDateInterval().getTo(),
                    calculation.getPremium(), calculation.getPropertyType().getId(), calculation.getSumInsured(), calculation.getSquare(),
                    calculation.getConstructionYear(), calculation.getCalculationDate(), contract.getAdditionalInfo(), address.getCountry(), address.getRegion(),
                    address.getDistrict(), address.getCity(), address.getStreet(), address.getPostcode(), address.getHousing(), address.getLetter(),
                    address.getHouse(), address.getApartment());
        } else {
            id = jdbcTemplate.queryForObject("update contract.contract set contract_no = ?, insured_id = ?, create_date =?, " +
                            "date_from = ?, date_to = ?, premium = ?, property_type_id = ?, insured_sum = ?, square = ?," +
                            " construction_year = ?, calculation_date = ?, additional_info = ?, country = ?, region = ?, district = ?, " +
                            " city = ?, street = ?, postcode = ?, housing = ?, letter = ?, house = ?, apartment = ? " +
                            "where id = ? returning id", Long.class,  contract.getContractNo(), contract.getInsured().getId(), contract.getCreateDate(),
                    calculation.getDateInterval().getFrom(), calculation.getDateInterval().getTo(), calculation.getPremium(), calculation.getPropertyType().getId(),
                    calculation.getSumInsured(), calculation.getSquare(), calculation.getConstructionYear(), calculation.getCalculationDate(),
                    contract.getAdditionalInfo(), address.getCountry(), address.getRegion(), address.getDistrict(), address.getCity(), address.getStreet(),
                    address.getPostcode(), address.getHousing(), address.getLetter(), address.getHouse(), address.getApartment(), contract.getId());
        }
        return get(id).orElseThrow(() -> new RuntimeException("Contract has not been saved"));
    }

    @Override
    public void delete(long id) {

    }
}
