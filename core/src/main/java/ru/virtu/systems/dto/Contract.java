package ru.virtu.systems.dto;

import ru.virtu.systems.dto.base.BaseEnity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Alexey Pustohin
 */
public class Contract extends BaseEnity {
    private String contractNo;
    private Insured insured;
    private BigDecimal premium;
    private LocalDate createDate;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String validaty;
    private Calculation calculation;


    /**
     * Номер договора
     */
    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    /**
     * страхователь
     */
    public Insured getInsured() {
        return insured;
    }

    public void setInsured(Insured insured) {
        this.insured = insured;
    }

    /**
     * Премия
     */
    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    /**
     * Дата заключения
     */
    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    /**
     * Дата с
     */
    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * Дата по
     */
    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getValidaty() {
        return validaty;
    }

    public void setValidaty(String validaty) {
        this.validaty = validaty;
    }

    public Calculation getCalculation() {
        return calculation;
    }

    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }
}
