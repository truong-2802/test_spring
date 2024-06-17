package com.example.test.controller;

import com.example.test.entity.Employee;
import com.example.test.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping("/employee/all")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PostMapping("/employee/add")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        // Validate input data
        if (employee.getFullName() == null || employee.getFullName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Full name is required.");
        }

        if (employee.getBirthday() == null || employee.getBirthday().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Birthday is required.");
        }

        if (employee.getAddress() == null || employee.getAddress().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Address is required.");
        }

        if (employee.getPosition() == null || employee.getPosition().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Position is required.");
        }
        if (employee.getDepartment() == null || employee.getDepartment().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Department is required.");
        }

        // Save employee entity to repository
        employeeRepository.save(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body("Employee added successfully.");
    }

}
