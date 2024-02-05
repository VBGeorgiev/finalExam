package com.academy.sirma.finalExam.controller;

import com.academy.sirma.finalExam.dto.EmployeeReferenceDto;
import com.academy.sirma.finalExam.dto.OutputReferenceDto;
import com.academy.sirma.finalExam.model.EmployeeReference;
import com.academy.sirma.finalExam.repository.DatabaseBackupFile;
import com.academy.sirma.finalExam.service.EmployeeReferenceService;
import com.academy.sirma.finalExam.repository.CsvDataFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/v1/")
public class EmployeeReferenceController {
    @Autowired
    private EmployeeReferenceService employeeReferenceService;

    @PostMapping("/import")
    public String uploadEmployeeReferenceFile(){
        List<EmployeeReference> employeeReferenceList = new CsvDataFile().readEmployeeReferenceList();
        return employeeReferenceService.saveAll(employeeReferenceList);
    }

    @PutMapping("/backup")
    public ResponseEntity<?> backupDatabase() {
        EmployeeReference[] empRef = employeeReferenceService.getAllReferences();
        String response = new DatabaseBackupFile().writeDatabase(empRef);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getProjectIds")
    public String getUniqueProjectIds() {
        return employeeReferenceService.getUniqueProjectId().toString();
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllSharedProjectDays() {
        Map<String, OutputReferenceDto> response = employeeReferenceService.getAllSharedProjectDays();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getMax")
    public ResponseEntity<?> getMaxSharedProjectDays() {
        OutputReferenceDto response = employeeReferenceService.getMaxSharedProjectDays();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteAll")
    public String deleteAllReferencesFromDatabase() {
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
            return ResponseEntity.ok("Update ignored: No reference has been found by this id or already exist");
        } else {
            return ResponseEntity.ok(empRefDto);
        }

    }

}
