package com.academy.sirma.finalExam.repository;

import com.academy.sirma.finalExam.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value="SELECT DISTINCT project_id FROM employee_project_reference", nativeQuery = true)
    public List<Integer> findUniqueProjectId();

}
