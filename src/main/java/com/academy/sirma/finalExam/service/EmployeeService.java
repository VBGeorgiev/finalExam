package com.academy.sirma.finalExam.service;

import com.academy.sirma.finalExam.dto.EmployeeDto;
import com.academy.sirma.finalExam.dto.ReferenceDto;
import com.academy.sirma.finalExam.model.Employee;
import com.academy.sirma.finalExam.repository.EmployeeRepository;
import com.academy.sirma.finalExam.utility.NullDate;
import com.academy.sirma.finalExam.utility.EmployeeReferenceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public String saveAll(List<Employee> employees) {
        if(employees.isEmpty()) {
            return "No valid references to be imported";
        }

        try {
            employeeRepository.saveAll(employees);
            return "Employee project references saved successfully";
        } catch (DataIntegrityViolationException e) {
            System.out.println("One or more references already exist in DB: " + e);
            return "One or more references already exist in DB";
        }

    }

    public String deleteAll() {
        employeeRepository.deleteAll();
        return "Employee project references successfully deleted";
    }

    public List<Integer> getUniqueProjectId() {
        return employeeRepository.findUniqueProjectId();
    }

    public Employee[] getAllReferences() {
        List<Employee> employeeList = employeeRepository.findAll();
        int arrayLength = employeeList.size();
        return employeeList.toArray(new Employee[arrayLength]);
    }

    public ReferenceDto getMaxSharedProjectDays() {
        Map<String, ReferenceDto> outRefMap = getAllSharedProjectDays();
        long maxSharedDays = 0;
        String maxKey = "";
        for(Map.Entry<String, ReferenceDto> tempDto : outRefMap.entrySet()) {
            if(tempDto.getValue().getMaxSharedDays() > maxSharedDays) {
                maxSharedDays = tempDto.getValue().getMaxSharedDays();
                maxKey = tempDto.getKey();
            };

        }

        return outRefMap.get(maxKey);
    }

    public Map<String, ReferenceDto> getAllSharedProjectDays() {
        List<Employee> employeeList = employeeRepository.findAll();
        int numberOfReferences = employeeList.size();
        Employee[] empRefList = employeeList.toArray(new Employee[numberOfReferences]);
//        List<Integer> projectIds = new ArrayList<>();
        long tempDays = 0;
        String tempEmpKey = "";
        Map<String, ReferenceDto> outputRefMap = new HashMap<>();
        LocalDate tempStartDate = null;
        LocalDate tempEndDate = null;
        for (int i = 0; i < numberOfReferences - 1; i++) {
            for (int j = i + 1; j <= numberOfReferences - 1; j++) {
                empRefList[i].setDateTo(NullDate.convertToCurrentDate(empRefList[i].getDateTo()));
                empRefList[j].setDateTo(NullDate.convertToCurrentDate(empRefList[j].getDateTo()));
                boolean isSameProject = empRefList[i].getProjectId() == empRefList[j].getProjectId();
                boolean isNotSameEmployee = empRefList[i].getEmpId() != empRefList[j].getEmpId();
                boolean isStartDate2BeforeEndDate1 = empRefList[j].getDateFrom().isBefore(empRefList[i].getDateTo());
                boolean isEndDate2AfterStartDate1 = empRefList[j].getDateTo().isAfter(empRefList[i].getDateFrom());
                boolean isStartDate2AfterStartDate1 = empRefList[j].getDateFrom().compareTo(empRefList[i].getDateFrom()) > 0;
                boolean isEndDate1BeforeEndDate2 = empRefList[i].getDateTo().compareTo(empRefList[j].getDateTo()) < 0;
                if (isSameProject && isNotSameEmployee) {
                    if (isStartDate2BeforeEndDate1 && isEndDate2AfterStartDate1) {
                        if (isStartDate2AfterStartDate1) {
                            tempStartDate = empRefList[j].getDateFrom();
                        } else {
                            tempStartDate = empRefList[i].getDateFrom();
                        }

                        if (isEndDate1BeforeEndDate2) {
                            tempEndDate = empRefList[i].getDateTo();
                        } else {
                            tempEndDate = empRefList[j].getDateTo();
                        }

                        tempDays = tempStartDate.until(tempEndDate, ChronoUnit.DAYS) + 1;
                        if (empRefList[i].getEmpId() < empRefList[j].getEmpId()) {
                            tempEmpKey = empRefList[i].getEmpId() + ", " + empRefList[j].getEmpId();
                        } else {
                            tempEmpKey = empRefList[j].getEmpId() + ", " + empRefList[i].getEmpId();
                        }

                        if (outputRefMap.containsKey(tempEmpKey)) {
                            ReferenceDto existingReferenceDto = outputRefMap.get(tempEmpKey);
                            if (existingReferenceDto.getProjectContribution().containsKey(empRefList[i].getProjectId())) {
                                Long currentPeriod = existingReferenceDto
                                        .getProjectContribution()
                                        .get(empRefList[i].getProjectId());
                                Long newPeriod = currentPeriod + tempDays;
                                existingReferenceDto
                                        .getProjectContribution()
                                        .put(empRefList[i].getProjectId(), newPeriod);
                            } else {
                                existingReferenceDto
                                        .getProjectContribution()
                                        .put(empRefList[i].getProjectId(), tempDays);
                            }

                            Long currentMaxSharedDays = existingReferenceDto.getMaxSharedDays();
                            existingReferenceDto.setMaxSharedDays(currentMaxSharedDays + tempDays);
                            outputRefMap.put(tempEmpKey, existingReferenceDto);

                        } else {
                            ReferenceDto newReferenceDto = new ReferenceDto();
                            newReferenceDto.setReferenceIndex(tempEmpKey);
                            newReferenceDto.setMaxSharedDays(tempDays);
                            HashMap<Integer, Long> tempProjectContribution = new HashMap<>();
                            tempProjectContribution.put(empRefList[i].getProjectId(), tempDays);
                            newReferenceDto.setProjectContribution(tempProjectContribution);
                            outputRefMap.put(tempEmpKey, newReferenceDto);
                        }

                    }

                }

            }

        }

        return outputRefMap;
    }

    public String addReference(EmployeeDto empRefDto) {
        Employee empRef = new Employee();
        empRef = EmployeeReferenceHelper.empRefDtoToEmpRef(empRefDto);
        try {
            employeeRepository.save(empRef);
            return "Employee reference successfully added";
        } catch(DataIntegrityViolationException e) {
            System.out.println("The reference already exist: " + e);
            return "Employee reference ignored: already exist";
        }

    }

    public String deleteById(Long refId) {
        if(employeeRepository.existsById(refId)) {
            employeeRepository.deleteById(refId);
            return "Reference id " + refId + " deleted successfully";
        }

        return "Reference " + refId + " not found";
    }

    public EmployeeDto getById(Long refId) {
        Employee empRef = employeeRepository.findById(refId).orElse(null);
        EmployeeDto empRefDto = new EmployeeDto();
        if(empRef != null) {
            empRefDto = EmployeeReferenceHelper.empRefToEmpRefDto(empRef);
        } else {
            empRefDto = null;
        }
        return empRefDto;
    }

    public EmployeeDto updateById(Long refId, EmployeeDto empRefDto) {
            Employee empRef = employeeRepository.findById(refId).orElse(null);
            if (empRef != null) {
                empRef.setEmpId(empRefDto.getEmpId());
                empRef.setProjectId(empRefDto.getProjectId());
                empRef.setDateFrom(empRefDto.getDateFrom());
                empRef.setDateTo(empRefDto.getDateTo());
                try {
                    employeeRepository.save(empRef);
                    return empRefDto;
                } catch(DataIntegrityViolationException e) {
                    System.out.println("Updated reference already exist in db: " + e);
                    return null;
                }

            }

        return null;

    }

}
