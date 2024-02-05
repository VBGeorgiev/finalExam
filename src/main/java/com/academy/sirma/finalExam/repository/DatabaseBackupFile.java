package com.academy.sirma.finalExam.repository;

import com.academy.sirma.finalExam.model.EmployeeReference;
import com.academy.sirma.finalExam.utility.Constants;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DatabaseBackupFile {
    public String writeDatabase(EmployeeReference[] employeeRef) {
        try (PrintWriter out = new PrintWriter(new FileWriter(Constants.backupFileName))) {
            for (EmployeeReference empRef:employeeRef) {
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
