package com.academy.sirma.finalExam.service;

import com.academy.sirma.finalExam.dto.EmployeeReferenceDto;
import com.academy.sirma.finalExam.model.EmployeeReference;
import com.academy.sirma.finalExam.repository.EmployeeReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeReferenceService {
    @Autowired
    private EmployeeReferenceRepository employeeReferenceRepository;

    public String saveAll(List<EmployeeReference> employeeReferences) {
        employeeReferenceRepository.saveAll(employeeReferences);
        return "Employee project references saved successfully";
    }

    public String deleteAll() {
        employeeReferenceRepository.deleteAll();
        return "Employee project references successfully deleted";
    }

    public List<Integer> getUniqueProjectId() {
        return employeeReferenceRepository.findUniqueProjectId();
    }

    public EmployeeReference[] getAllReferences() {
        List<EmployeeReference> employeeReferenceList = employeeReferenceRepository.findAll();
        int arrayLength = employeeReferenceList.size();
        return employeeReferenceList.toArray(new EmployeeReference[arrayLength]);
    }

    public Map<String, EmployeeReferenceDto> CalculateSharedProjectDays() {
        List<EmployeeReference> employeeReferenceList = employeeReferenceRepository.findAll();
        int numberOfReferences = employeeReferenceList.size();
        EmployeeReference[] empRefList = employeeReferenceList.toArray(new EmployeeReference[numberOfReferences]);
        List<Integer> projectIds = new ArrayList<>();
        long tempDays = 0;
        String tempEmpKey = "";
        Map<String, EmployeeReferenceDto> outputRefMap = new HashMap<>();
        LocalDate tempStartDate = null;
        LocalDate tempEndDate = null;
        for (int i = 0; i < numberOfReferences - 1; i++) {
            for (int j = i + 1; j <= numberOfReferences - 1; j++) {
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
                            EmployeeReferenceDto existingEmployeeReferenceDto = outputRefMap.get(tempEmpKey);
                            if (existingEmployeeReferenceDto.getProjectContribution().containsKey(empRefList[i].getProjectId())) {
                                Long currentPeriod = existingEmployeeReferenceDto
                                        .getProjectContribution()
                                        .get(empRefList[i].getProjectId());
                                Long newPeriod = currentPeriod + tempDays;
                                existingEmployeeReferenceDto
                                        .getProjectContribution()
                                        .put(empRefList[i].getProjectId(), newPeriod);
                            } else {
                                existingEmployeeReferenceDto
                                        .getProjectContribution()
                                        .put(empRefList[i].getProjectId(), tempDays);
                            }

                            Long currentMaxSharedDays = existingEmployeeReferenceDto.getMaxSharedDays();
                            existingEmployeeReferenceDto.setMaxSharedDays(currentMaxSharedDays + tempDays);
                            outputRefMap.put(tempEmpKey, existingEmployeeReferenceDto);

                        } else {
                            EmployeeReferenceDto newEmployeeReferenceDto = new EmployeeReferenceDto();
                            newEmployeeReferenceDto.setReferenceIndex(tempEmpKey);
                            newEmployeeReferenceDto.setMaxSharedDays(tempDays);
                            HashMap<Integer, Long> tempProjectContribution = new HashMap<>();
                            tempProjectContribution.put(empRefList[i].getProjectId(), tempDays);
                            newEmployeeReferenceDto.setProjectContribution(tempProjectContribution);
                            outputRefMap.put(tempEmpKey, newEmployeeReferenceDto);
                        }

                    }

                }

            }

        }

        return outputRefMap;
    }

}
