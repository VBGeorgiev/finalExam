package com.academy.sirma.finalExam.controller;

import com.academy.sirma.finalExam.dto.EmployeeReferenceDto;
import com.academy.sirma.finalExam.model.EmployeeReference;
import com.academy.sirma.finalExam.repository.BackupToCsvFile;
import com.academy.sirma.finalExam.service.EmployeeReferenceService;
import com.academy.sirma.finalExam.repository.UploadCsvFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class EmployeeReferenceController {
    @Autowired
    private EmployeeReferenceService employeeReferenceService;

    @PostMapping("/import")
    public String uploadEmployeeReferenceFile(){
        List<EmployeeReference> employeeReferenceList = new UploadCsvFile().readEmployeeReferenceList();
        employeeReferenceService.saveAll(employeeReferenceList);
        return "File successfully uploaded";
    }

    @PutMapping("/backup")
    public ResponseEntity<?> backupDatabase() {
        EmployeeReference[] empRef = employeeReferenceService.getAllReferences();
        String response = new BackupToCsvFile().writeDatabase(empRef);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/findProjectIds")
    public String findProjectIds() {
        return employeeReferenceService.getUniqueProjectId().toString();
    }

    @GetMapping("/calculate")
    public ResponseEntity<?> calculateAll() {
        Map<String, EmployeeReferenceDto> response = employeeReferenceService.CalculateSharedProjectDays();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteAll")
    public String deleteAllReferencesFromDB() {
        return employeeReferenceService.deleteAll();
    }
}
