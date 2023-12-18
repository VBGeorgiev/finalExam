package com.academy.sirma.finalExam.dto;

import jakarta.persistence.Transient;
import java.util.HashMap;

public class EmployeeReferenceDto {
    private String referenceIndex;
    private Long maxSharedDays;
    private HashMap<Integer, Long> projectContribution;

    public EmployeeReferenceDto() {
    }

    public EmployeeReferenceDto(String referenceIndex, HashMap<Integer, Long> projectContribution) {
        this.referenceIndex = referenceIndex;
        this.projectContribution = projectContribution;
    }

    public String getReferenceIndex() {
        return referenceIndex;
    }

    public void setReferenceIndex(String referenceIndex) {
        this.referenceIndex = referenceIndex;
    }

    public HashMap<Integer, Long> getProjectContribution() {
        return projectContribution;
    }

    public void setProjectContribution(HashMap<Integer, Long> projectContribution) {
        this.projectContribution = projectContribution;
    }

    public Long getMaxSharedDays() {
        return maxSharedDays;
    }

    public void setMaxSharedDays(Long maxSharedDays) {
        this.maxSharedDays = maxSharedDays;
    }
}
