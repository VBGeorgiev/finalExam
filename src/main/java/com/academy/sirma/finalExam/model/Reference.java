package com.academy.sirma.finalExam.model;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Reference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String index;
    private Employee firstEmployee;
    private Employee secondEmployee;
    private Long maxSharedDays;
    private Map<Integer, Long> projectContribution;

    public Reference() {
    }

    public Reference(Employee firstEmployee, Employee secondEmployee) {
        this.firstEmployee = firstEmployee;
        this.secondEmployee = secondEmployee;
        this.maxSharedDays = 0L;
        this.projectContribution = new HashMap<>();
    }

    public String getIndex() {
        return index;
    }

    public void setIndex() {
        this.index = this.firstEmployee.getEmpId() + " - " + this.secondEmployee.getEmpId();
    }

    public Employee getFirstEmployee() {
        return firstEmployee;
    }

    public void setFirstEmployee(Employee firstEmployee) {
        this.firstEmployee = firstEmployee;
    }

    public Employee getSecondEmployee() {
        return secondEmployee;
    }

    public void setSecondEmployee(Employee secondEmployee) {
        this.secondEmployee = secondEmployee;
    }

    public Long getMaxSharedDays() {
        return maxSharedDays;
    }

    public void setMaxSharedDays(Long maxSharedDays) {
        this.maxSharedDays = maxSharedDays;
    }

    public Map<Integer, Long> getProjectContribution() {
        return projectContribution;
    }

    public void setProjectContribution(Map<Integer, Long> projectContribution) {
        this.projectContribution = projectContribution;
    }
}
