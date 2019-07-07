package ru.virtu.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.virtu.systems.dto.Calculation;
import ru.virtu.systems.dto.PropertyType;
import ru.virtu.systems.service.CalculationService;

import java.math.BigDecimal;


/**
 * @author Alexey Pustohin
 */
@Service
public class CalculatuinServiceImpl implements CalculationService {
    private static final Logger logger = LoggerFactory.getLogger(CalculationService.class);
    @Override
    public Double calculatePremium(Calculation calculation) {
        if (calculation.getSumInsured() <= 0) {
            throw new IllegalArgumentException("Страховая сумма должна быть положительным числом и больше нуля");
        }
        Double cofPropertyType = getPropertyTypeKof(calculation.getPropertyType());
        Double constructionYearKof = getConstructionYearKof(calculation.getConstructionYear());
        Double squareKof = getSquareKof(calculation.getSquare());
        long diff = Math.round((calculation.getDateInterval().getTo().getTime() - calculation.getDateInterval().getFrom().getTime()) / (double) 86400000);
        try {
            double premium = (calculation.getSumInsured() / (double) diff) * cofPropertyType * constructionYearKof * squareKof;
            return new BigDecimal(premium).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (ArithmeticException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private Double getPropertyTypeKof(PropertyType propertyType) {
        switch (propertyType.getCode()) {
            case "apartment":
                return 1.7;
            case "house":
                return 1.5d;
            case "room":
                return 1.3d;
                default:
                    throw new IllegalArgumentException("Тип недвижимости не определен");
        }
    }

    private Double getConstructionYearKof(Integer constructionYear) {
        if (constructionYear < 2000) {
            return 1.3d;
        }

        if (constructionYear >= 2000 && constructionYear <= 2014) {
            return 1.6d;
        }
        if (constructionYear == 2015) {
            return 2d;
        }
        throw new IllegalArgumentException("Год постройки не попал в рамки расчета коэффициента");

    }

    private Double getSquareKof(Double square) {
        if (square < 50) {
            return 1.2d;
        }

        if (square >= 50d && square <= 100d) {
            return 1.5d;
        }

        return 2d;
    }
}
