package com.academy.sirma.finalExam.repository;

import com.academy.sirma.finalExam.model.Employee;
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
    public List<Employee> readEmployeeReferenceList() {
        String line;
        List<Employee> employeeList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.uploadFileName))) {
            while ((line = br.readLine()) != null) {
                if(line.isEmpty()) {
                    System.out.println("This reference, (" + line + "), will be ignored");
                    continue;
                }
                String[] item = line.split(Constants.commaSeparated);
                int empId = 0;
                int projectId = 0;
                Employee tempEmployee = new Employee();
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

                tempEmployee.setEmpId(empId);
                tempEmployee.setProjectId(projectId);
                tempEmployee.setDateFrom(parseDates[0]);
                tempEmployee.setDateTo(parseDates[1]);


                boolean isNotDublicate = true;
                for(int i = 0; i < employeeList.size(); i++) {
                    if(employeeList.get(i).isSame(tempEmployee)) {
                        isNotDublicate = false;
                        break;
                    }

                }

                if(isNotDublicate) {
                    employeeList.add(tempEmployee);
                } else {
                    System.out.println("This duplicated reference, (" + line + "), will be ignored");
                }

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return employeeList;
    }

}
