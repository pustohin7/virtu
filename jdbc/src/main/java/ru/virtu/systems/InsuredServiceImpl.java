package ru.virtu.systems;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.virtu.systems.base.BaseCompoundService;
import ru.virtu.systems.base.BaseService;
import ru.virtu.systems.dto.Insured;
import ru.virtu.systems.mapper.InsuredRowMapper;
import ru.virtu.systems.query.SqlQuery;
import ru.virtu.systems.query.operator.LikeDecorator;
import ru.virtu.systems.sc.InsuredSC;
import ru.virtu.systems.sc.base.BaseSC;
import ru.virtu.systems.service.InsuredService;

/**
 * @author Alexey Pustohin
 */
@Service
@Transactional
public class InsuredServiceImpl extends BaseService implements InsuredService, BaseCompoundService<Insured, InsuredSC> {
    @Override
    public void fillDeletableSC(BaseSC sc, SqlQuery query) {

    }

    @Override
    public String getTableName() {
        return "nsi.insured";
    }

    @Override
    public RowMapper<Insured> getRowMapper() {
        return new InsuredRowMapper();
    }

    @Override
    @Transactional
    public Insured save(Insured insured) {
        Long id;
        if (insured.isNew()) {
            id =  jdbcTemplate.queryForObject("insert into nsi.insured(birth_date, first_name, last_name, middle_name, doc_number, doc_serial)" +
                            "values (?, ?, ?, ?, ?, ?) returning id", Long.class, insured.getBirthDate(), insured.getFirstName(), insured.getLastName(),
                    insured.getMiddleName(), insured.getDocNumber(), insured.getDocSerial());
        } else {
            id = jdbcTemplate.queryForObject("update nsi.insured set birth_date = ?, first_name = ?, last_name = ?, middle_name = ?, " +
                            "doc_number = ?, doc_serial = ? " +
                            "where id = ? returning id", Long.class, insured.getBirthDate(), insured.getFirstName(), insured.getLastName(),
                    insured.getMiddleName(), insured.getDocNumber(), insured.getDocSerial(), insured.getId());
        }
        return get(id).orElseThrow(() -> new RuntimeException("Insured has not been saved"));
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public InsuredSC newDefaultSearchCondition() {
        return new InsuredSC();
    }

    @Override
    public void fillSC(InsuredSC sc, SqlQuery query) {
        query.ilike("first_name", sc.getFirstName(), LikeDecorator.ANYBOTH);
        query.ilike("last_name", sc.getLastName(), LikeDecorator.ANYBOTH);
        query.ilike("middle_name", sc.getMiddleName(), LikeDecorator.ANYBOTH);
    }
}
