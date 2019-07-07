package ru.virtu.systems.service;

import ru.virtu.systems.dto.Contract;
import ru.virtu.systems.sc.base.BaseSC;
import ru.virtu.systems.service.base.CrudService;
import ru.virtu.systems.service.base.SearchService;

/**
 * @author Alexey Pustohin
 */
public interface ContractService extends SearchService<Contract, BaseSC>, CrudService<Contract> {
}
