package ru.virtu.systems.dto;

import ru.virtu.systems.dto.base.BaseEnity;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author Alexey Pustohin
 */
public class Insured extends BaseEnity {
    private String firstName;
    private String lastName;
    private String middleName;
    private Date birthDate;
    private Integer docNumber;
    private Integer docSerial;
    private String insuredFio;
    private String documentFull;

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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(Integer docNumber) {
        this.docNumber = docNumber;
    }

    public Integer getDocSerial() {
        return docSerial;
    }

    public void setDocSerial(Integer docSerial) {
        this.docSerial = docSerial;
    }

    public String getInsuredFio() {
        return insuredFio;
    }

    public void setInsuredFio(String summaryFio) {
        this.insuredFio = summaryFio;
    }

    public String getDocumentFull() {
        return documentFull;
    }

    public void setDocumentFull(String documentFull) {
        this.documentFull = documentFull;
    }
}
