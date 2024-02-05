package com.academy.sirma.finalExam.repository;

import com.academy.sirma.finalExam.model.EmployeeReference;
import com.academy.sirma.finalExam.utility.Constants;
import com.academy.sirma.finalExam.validate.Validate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvDataFile {
    public List<EmployeeReference> readEmployeeReferenceList() {
        String line;
        List<EmployeeReference> employeeReferenceList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.uploadFileName))) {
            while ((line = br.readLine()) != null) {
                if(line.isEmpty()) {
                    System.out.println("This reference, (" + line + "), will be ignored");
                    continue;
                }
                String[] item = line.split(Constants.commaSeparated);
                int empId = 0;
                int projectId = 0;
                EmployeeReference tempEmployeeReference = new EmployeeReference();
                if(Validate.isNumber(item[0])) {
                    empId = Integer.parseInt(item[0]);
                } else {
                    System.out.println("This reference, (" + line + "), will be ignored");
                    continue;
                }

                if(Validate.isNumber(item[1])) {
                    projectId = Integer.parseInt(item[1]);
                } else {
                    System.out.println("This reference, (" + line + "), will be ignored");
                    continue;
                }

                String[] strDates = new String[2];
                strDates[0] = item[2];
                strDates[1] = item[3];
                LocalDate[] parseDates = Validate.getDates(strDates);
                if(parseDates[2] == null) {
                    System.out.println("This reference, (" + line + "), will be ignored");
                    continue;
                }

                tempEmployeeReference.setEmpId(empId);
                tempEmployeeReference.setProjectId(projectId);
                tempEmployeeReference.setDateFrom(parseDates[0]);
                tempEmployeeReference.setDateTo(parseDates[1]);


                boolean isNotDublicate = true;
                for(int i = 0; i < employeeReferenceList.size(); i++) {
                    if(employeeReferenceList.get(i).isSame(tempEmployeeReference)) {
                        isNotDublicate = false;
                        break;
                    }

                }

                if(isNotDublicate) {
                    employeeReferenceList.add(tempEmployeeReference);
                } else {
                    System.out.println("This duplicated reference, (" + line + "), will be ignored");
                }

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return employeeReferenceList;
    }

}
