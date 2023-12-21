package com.academy.sirma.finalExam.controller;

import com.academy.sirma.finalExam.dto.EmployeeReferenceDto;
import com.academy.sirma.finalExam.dto.OutputReferenceDto;
import com.academy.sirma.finalExam.model.EmployeeReference;
import com.academy.sirma.finalExam.repository.BackupToCsvFile;
import com.academy.sirma.finalExam.service.EmployeeReferenceService;
import com.academy.sirma.finalExam.repository.UploadCsvFile;
import com.academy.sirma.finalExam.utility.EmployeeReferenceHelper;
import com.academy.sirma.finalExam.validate.Validate;
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
        return employeeReferenceService.saveAll(employeeReferenceList);
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

    @GetMapping("/calculateAll")
    public ResponseEntity<?> calculateAll() {
        Map<String, OutputReferenceDto> response = employeeReferenceService.CalculateSharedProjectDays();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/calculateMax")
    public ResponseEntity<?> calculateMax() {
        OutputReferenceDto response = employeeReferenceService.CalculateMaxSharedProjectDays();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteAll")
    public String deleteAllReferencesFromDB() {
        return employeeReferenceService.deleteAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReference(@RequestBody EmployeeReferenceDto empRefDto) {
        return ResponseEntity.ok(employeeReferenceService.addReference(empRefDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        String response = employeeReferenceService.deleteById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        EmployeeReferenceDto empRefDto = employeeReferenceService.getById(id);
        if(empRefDto == null) {
            return ResponseEntity.ok("No reference has been found by this id");
        } else {
            return ResponseEntity.ok(empRefDto);
        }

    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody EmployeeReferenceDto empRefDto) {
        EmployeeReferenceDto outEmpRefDto = employeeReferenceService.updateById(id, empRefDto);
        if(outEmpRefDto == null) {
            return ResponseEntity.ok("No reference has been found by this id");
        } else {
            return ResponseEntity.ok(empRefDto);
        }

    }

}
