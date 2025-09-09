package com.example.spring1.controller;

import com.example.spring1.Company;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/companies")
public class CompanyController {
    List<Company> companies = new ArrayList<>();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company create(@RequestBody Company company) {
        Company newCompany = new Company(companies.size() + 1, company.name());
        companies.add(newCompany);
        return newCompany;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company get(@PathVariable Integer id) {
        return companies.stream()
                .filter(company -> Objects.equals(company.id(), id))
                .findFirst()
                .orElse(null);
    }

    public void clear() {
        companies.clear();
    }


}
