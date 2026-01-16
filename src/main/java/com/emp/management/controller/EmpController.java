package com.emp.management.controller;

import com.emp.management.entity.Employee;
import com.emp.management.exceptions.ResourceNotFoundException;
import com.emp.management.repository.EmpRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
public class EmpController {
    private EmpRepository empRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return empRepository.findAll();

    }


    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return empRepository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employees = empRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id:" + id));
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        Employee employees = empRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id:" + id));
        employees.setFirstName(employee.getFirstName());
        employees.setLastName(employee.getLastName());
        employees.setSalary(employee.getSalary());
        empRepository.save(employees);
        return ResponseEntity.ok(employees);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable int id) {
        Employee employees = empRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id:" + id));
        empRepository.delete(employees);
        Map<String,Boolean>response=new HashMap<String,Boolean>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
