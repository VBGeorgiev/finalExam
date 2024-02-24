package com.academy.sirma.finalExam.repository;

import com.academy.sirma.finalExam.model.Employee;
import com.academy.sirma.finalExam.utility.Constants;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DatabaseBackupFile {
    public String writeDatabase(Employee[] employeeRef) {
        try (PrintWriter out = new PrintWriter(new FileWriter(Constants.backupFileName))) {
            for (Employee empRef:employeeRef) {
                out.println(empRef.getEmpId() + ", " + empRef.getProjectId() + ", "
                + empRef.getDateFrom() + ", " + empRef.getDateTo());
            }
            return "Backup database successful";

        } catch (IOException e) {
            System.out.println(e);
            return e.toString();
        }

    }
}
