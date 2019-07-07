package ru.virtu.systems.service;

import ru.virtu.systems.dto.Calculation;

/**
 * @author Alexey Pustohin
 */
public interface CalculationService {

    Double calculatePremium(Calculation calculation);
}
