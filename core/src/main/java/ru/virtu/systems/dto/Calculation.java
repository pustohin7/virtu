package ru.virtu.systems.dto;

import ru.virtu.systems.dto.base.DateInterval;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Alexey Pustohin
 */
public class Calculation implements Serializable{
    private BigDecimal sumInsured;
    private DateInterval dateInterval;

    public BigDecimal getSumInsured() {
        return sumInsured;
    }

    public void setSumInsured(BigDecimal sumInsured) {
        this.sumInsured = sumInsured;
    }

    public DateInterval getDateInterval() {
        return dateInterval;
    }

    public void setDateInterval(DateInterval dateInterval) {
        this.dateInterval = dateInterval;
    }
}
