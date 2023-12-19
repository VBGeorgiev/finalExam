package com.academy.sirma.finalExam.service;

import com.academy.sirma.finalExam.dto.EmployeeReferenceDto;
import com.academy.sirma.finalExam.dto.OutputReferenceDto;
import com.academy.sirma.finalExam.model.EmployeeReference;
import com.academy.sirma.finalExam.repository.EmployeeReferenceRepository;
import com.academy.sirma.finalExam.utility.Convert;
import com.academy.sirma.finalExam.utility.EmployeeReferenceHelper;
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

    public Map<String, OutputReferenceDto> CalculateSharedProjectDays() {
        List<EmployeeReference> employeeReferenceList = employeeReferenceRepository.findAll();
        int numberOfReferences = employeeReferenceList.size();
        EmployeeReference[] empRefList = employeeReferenceList.toArray(new EmployeeReference[numberOfReferences]);
        List<Integer> projectIds = new ArrayList<>();
        long tempDays = 0;
        String tempEmpKey = "";
        Map<String, OutputReferenceDto> outputRefMap = new HashMap<>();
        LocalDate tempStartDate = null;
        LocalDate tempEndDate = null;
        for (int i = 0; i < numberOfReferences - 1; i++) {
            for (int j = i + 1; j <= numberOfReferences - 1; j++) {
                empRefList[i].setDateTo(Convert.NullDateToCurrentDate(empRefList[i].getDateTo()));
                empRefList[j].setDateTo(Convert.NullDateToCurrentDate(empRefList[j].getDateTo()));
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
                            OutputReferenceDto existingOutputReferenceDto = outputRefMap.get(tempEmpKey);
                            if (existingOutputReferenceDto.getProjectContribution().containsKey(empRefList[i].getProjectId())) {
                                Long currentPeriod = existingOutputReferenceDto
                                        .getProjectContribution()
                                        .get(empRefList[i].getProjectId());
                                Long newPeriod = currentPeriod + tempDays;
                                existingOutputReferenceDto
                                        .getProjectContribution()
                                        .put(empRefList[i].getProjectId(), newPeriod);
                            } else {
                                existingOutputReferenceDto
                                        .getProjectContribution()
                                        .put(empRefList[i].getProjectId(), tempDays);
                            }

                            Long currentMaxSharedDays = existingOutputReferenceDto.getMaxSharedDays();
                            existingOutputReferenceDto.setMaxSharedDays(currentMaxSharedDays + tempDays);
                            outputRefMap.put(tempEmpKey, existingOutputReferenceDto);

                        } else {
                            OutputReferenceDto newOutputReferenceDto = new OutputReferenceDto();
                            newOutputReferenceDto.setReferenceIndex(tempEmpKey);
                            newOutputReferenceDto.setMaxSharedDays(tempDays);
                            HashMap<Integer, Long> tempProjectContribution = new HashMap<>();
                            tempProjectContribution.put(empRefList[i].getProjectId(), tempDays);
                            newOutputReferenceDto.setProjectContribution(tempProjectContribution);
                            outputRefMap.put(tempEmpKey, newOutputReferenceDto);
                        }

                    }

                }

            }

        }

        return outputRefMap;
    }

    public String addReference(EmployeeReference employeeReference) {
        employeeReferenceRepository.save(employeeReference);
        return "Employee reference successfully added";
    }

    public String deleteById(Long refId) {
        if(employeeReferenceRepository.existsById(refId)) {
            employeeReferenceRepository.deleteById(refId);
            return "Reference id " + refId + " deleted successfully";
        }

        return "Reference " + refId + " not found";
    }

    public EmployeeReferenceDto getById(Long refId) {
        EmployeeReference empRef = employeeReferenceRepository.findById(refId).orElse(null);
        EmployeeReferenceDto empRefDto = new EmployeeReferenceDto();
        if(empRef != null) {
            empRefDto = EmployeeReferenceHelper.empRefTOEmpRefDto(empRef);
        } else {
            empRefDto = null;
        }
        return empRefDto;
    }

}
