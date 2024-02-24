package com.academy.sirma.finalExam.utility;

import com.academy.sirma.finalExam.dto.EmployeeDto;
import com.academy.sirma.finalExam.model.Employee;

public class EmployeeReferenceHelper {
    public static Employee empRefDtoToEmpRef(EmployeeDto empRefDto) {
        Employee empRef = new Employee();
        empRef.setEmpId(empRefDto.getEmpId());
        empRef.setProjectId(empRefDto.getProjectId());
        empRef.setDateFrom(empRefDto.getDateFrom());
        empRef.setDateTo(empRefDto.getDateTo());
        return empRef;
    }

    public static EmployeeDto empRefToEmpRefDto(Employee empRef) {
        EmployeeDto empRefDto = new EmployeeDto();
        empRefDto.setEmpId(empRef.getEmpId());
        empRefDto.setProjectId(empRef.getProjectId());
        empRefDto.setDateFrom(empRef.getDateFrom());
        empRefDto.setDateTo(empRef.getDateTo());
        return empRefDto;
    }
}
