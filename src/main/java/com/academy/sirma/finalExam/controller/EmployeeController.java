package com.academy.sirma.finalExam.controller;

import com.academy.sirma.finalExam.dto.EmployeeDto;
import com.academy.sirma.finalExam.dto.ReferenceDto;
import com.academy.sirma.finalExam.model.Employee;
import com.academy.sirma.finalExam.repository.DatabaseBackupFile;
import com.academy.sirma.finalExam.service.EmployeeService;
import com.academy.sirma.finalExam.repository.CsvDataFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/import")
    public String uploadEmployeeReferenceFile(){
        List<Employee> employeeList = new CsvDataFile().readEmployeeReferenceList();
        return employeeService.saveAll(employeeList);
    }

    @PutMapping("/backup")
    public ResponseEntity<?> backupDatabase() {
        Employee[] empRef = employeeService.getAllReferences();
        String response = new DatabaseBackupFile().writeDatabase(empRef);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getProjectIds")
    public String getUniqueProjectIds() {
        return employeeService.getUniqueProjectId().toString();
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllSharedProjectDays() {
        Map<String, ReferenceDto> response = employeeService.getAllSharedProjectDays();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getMax")
    public ResponseEntity<?> getMaxSharedProjectDays() {
        ReferenceDto response = employeeService.getMaxSharedProjectDays();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteAll")
    public String deleteAllReferencesFromDatabase() {
        return employeeService.deleteAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReference(@RequestBody EmployeeDto empRefDto) {
        return ResponseEntity.ok(employeeService.addReference(empRefDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        String response = employeeService.deleteById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        EmployeeDto empRefDto = employeeService.getById(id);
        if(empRefDto == null) {
            return ResponseEntity.ok("No reference has been found by this id");
        } else {
            return ResponseEntity.ok(empRefDto);
        }

    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody EmployeeDto empRefDto) {
        EmployeeDto outEmpRefDto = employeeService.updateById(id, empRefDto);
        if(outEmpRefDto == null) {
            return ResponseEntity.ok("Update ignored: No reference has been found by this id or already exist");
        } else {
            return ResponseEntity.ok(empRefDto);
        }

    }

}
