package com.academy.sirma.finalExam.utility;

import com.academy.sirma.finalExam.dto.EmployeeReferenceDto;
import com.academy.sirma.finalExam.model.EmployeeReference;

public class EmployeeReferenceHelper {
    public static EmployeeReference empRefDTOToEmpRef(EmployeeReferenceDto empRefDto) {
        EmployeeReference empRef = new EmployeeReference();
        empRef.setEmpId(empRefDto.getEmpId());
        empRef.setProjectId(empRefDto.getProjectId());
        empRef.setDateFrom(empRefDto.getDateFrom());
        empRef.setDateTo(empRefDto.getDateTo());
        return empRef;
    }

    public static EmployeeReferenceDto empRefTOEmpRefDto(EmployeeReference empRef) {
        EmployeeReferenceDto empRefDto = new EmployeeReferenceDto();
        empRefDto.setEmpId(empRef.getEmpId());
        empRefDto.setProjectId(empRef.getProjectId());
        empRefDto.setDateFrom(empRef.getDateFrom());
        empRefDto.setDateTo(empRef.getDateTo());
        return empRefDto;
    }
}
