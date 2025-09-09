package com.example.spring1.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private List<Employee> employees = new ArrayList<>();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) {
        Employee newEmployee = new Employee(employees.size() + 1, employee.name(), employee.age(), employee.gender(), employee.salary());
        employees.add(newEmployee);
        return newEmployee;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee get(@PathVariable Integer id) {
        return employees.stream()
                .filter(employee -> Objects.equals(employee.id(), id))
                .findFirst()
                .orElse(null);
    }

    public void clear(){
        employees.clear();
    }

    @GetMapping
    public List<Employee> index(@RequestParam(required = false) String gender){
        return employees.stream()
                .filter(employee -> Objects.equals(employee.gender(), gender))
                .toList();
    }
}
