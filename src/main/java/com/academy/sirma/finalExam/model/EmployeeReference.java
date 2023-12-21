package com.academy.sirma.finalExam.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
@Entity
@Table(name="employee_project_reference")
public class EmployeeReference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private int empId;
    @NotNull
    private int projectId;
    @NotNull
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public EmployeeReference() {
    }

    public EmployeeReference(int empId, int projectId, LocalDate dateFrom, LocalDate dateTo) {
        this.empId = empId;
        this.projectId = projectId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public boolean isSame(EmployeeReference empRef) {
        if(this.empId != empRef.getEmpId() ||
        this.projectId != empRef.getProjectId() ||
        !empRef.getDateFrom().isEqual(this.dateFrom)) {
            return false;
        }

        if(empRef.getDateTo() != null && this.dateTo != null) {
            if(!empRef.getDateTo().isEqual(this.dateTo)) {
                return false;
            }

        } else if (empRef.getDateTo() != null || this.dateTo != null) {
            return false;
        }

        return true;
    }

}
