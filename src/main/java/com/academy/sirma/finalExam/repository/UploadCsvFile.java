package com.academy.sirma.finalExam.repository;

import com.academy.sirma.finalExam.model.EmployeeReference;
import com.academy.sirma.finalExam.utility.Constants;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UploadCsvFile {
    public List<EmployeeReference> readEmployeeReferenceList() {
        String line;
        List<EmployeeReference> employeeReferenceList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.uploadFileName))) {
            while ((line = br.readLine()) != null) {
                String[] item = line.split(Constants.commaSeparated);
                EmployeeReference tempEmployeeReference = new EmployeeReference();
                int empId = Integer.parseInt(item[0]);
                tempEmployeeReference.setEmpId(empId);
                int projectId = Integer.parseInt(item[1]);
                tempEmployeeReference.setProjectId(projectId);
//                Date to be improved
                if(!item[2].equals("null")) {
                    tempEmployeeReference.setDateFrom(LocalDate.parse(item[2], formatter));
                } else {
                    tempEmployeeReference.setDateTo(null);
                }

                if(!item[3].equals("NULL")) {
                    tempEmployeeReference.setDateTo(LocalDate.parse(item[3], formatter));
                } else {
                    tempEmployeeReference.setDateTo(null);
                }

                employeeReferenceList.add(tempEmployeeReference);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return employeeReferenceList;
    }

}
