package ru.virtu.systems.dto;

import ru.virtu.systems.dto.base.BaseEnity;

import java.time.LocalDate;

/**
 * @author Alexey Pustohin
 */
public class Insured extends BaseEnity {
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthDate;
    private Long docNumber;
    private Long docSerial;
    private String summaryFio;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Long getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(Long docNumber) {
        this.docNumber = docNumber;
    }

    public Long getDocSerial() {
        return docSerial;
    }

    public void setDocSerial(Long docSerial) {
        this.docSerial = docSerial;
    }

    public String getSummaryFio() {
        return summaryFio;
    }

    public void setSummaryFio(String summaryFio) {
        this.summaryFio = summaryFio;
    }
}
