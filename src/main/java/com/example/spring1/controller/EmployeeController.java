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

    public void clear() {
        employees.clear();
    }

    @GetMapping
    public List<Employee> index(@RequestParam(required = false) String gender){
        if (gender == null) {
            return employees;
        }
        return employees.stream()
                .filter(employee -> Objects.equals(employee.gender(), gender))
                .toList();
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Integer id, @RequestBody Employee employee) {
        Employee existingEmployee = employees.stream()
                .filter(e -> Objects.equals(e.id(), id))
                .findFirst()
                .orElse(null);
        if (existingEmployee != null) {
            employees.remove(existingEmployee);
            Employee updatedEmployee = new Employee(id, employee.name(), employee.age(), employee.gender(), employee.salary());
            employees.add(updatedEmployee);
            return updatedEmployee;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        employees.removeIf(employee -> Objects.equals(employee.id(), id));
    }

    @GetMapping(params = {"page", "size"})
    public List<Employee> pageQuery(@RequestParam int page, @RequestParam int size) {
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, employees.size());
        if (fromIndex >= employees.size()) {
            return new ArrayList<>();
        }
        return employees.subList(fromIndex, toIndex);
    }

}
