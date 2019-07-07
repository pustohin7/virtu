package ru.virtu.systems;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.virtu.systems.base.BaseCompoundService;
import ru.virtu.systems.base.BaseService;
import ru.virtu.systems.dto.PropertyType;
import ru.virtu.systems.mapper.PropertyTypeRowMapper;
import ru.virtu.systems.query.SqlQuery;
import ru.virtu.systems.sc.base.BaseSC;
import ru.virtu.systems.sc.base.Select2SC;
import ru.virtu.systems.service.PropertyTypeService;

/**
 * @author Alexey Pustohin
 */
@Service
public class PropertyTypeServiceImpl extends BaseService implements PropertyTypeService, BaseCompoundService<PropertyType, Select2SC> {

    @Override
    public void fillDeletableSC(BaseSC sc, SqlQuery query) {

    }

    @Override
    public String getTableName() {
        return "nsi.property_type";
    }

    @Override
    public RowMapper<PropertyType> getRowMapper() {
        return new PropertyTypeRowMapper();
    }

    @Override
    public PropertyType save(PropertyType object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Select2SC newDefaultSearchCondition() {
      return new Select2SC();
    }

    @Override
    public void fillSC(Select2SC sc, SqlQuery query) {
        fillSelect2SC(sc, query);
    }
}
