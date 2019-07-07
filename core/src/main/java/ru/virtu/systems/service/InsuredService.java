package ru.virtu.systems.service;

import ru.virtu.systems.dto.Insured;
import ru.virtu.systems.sc.InsuredSC;
import ru.virtu.systems.service.base.CrudService;
import ru.virtu.systems.service.base.SearchService;

/**
 * @author Alexey Pustohin
 */
public interface InsuredService extends SearchService<Insured, InsuredSC>, CrudService<Insured> {
}
