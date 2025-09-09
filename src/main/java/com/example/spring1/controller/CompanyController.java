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

    @GetMapping
    public List<Company> index() {
        return new ArrayList<>(companies); // 返回副本以避免并发修改异常
    }

    @PutMapping("/{id}")
    public Company update(@PathVariable Integer id, @RequestBody Company company) {
        Company existingCompany = companies.stream()
                .filter(c -> Objects.equals(c.id(), id))
                .findFirst()
                .orElse(null);
        if (existingCompany != null) {
            companies.remove(existingCompany);
            Company updatedCompany = new Company(id, company.name());
            companies.add(updatedCompany);
            return updatedCompany;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        companies.removeIf(company -> Objects.equals(company.id(), id));
    }

    @GetMapping(params = {"page", "size"})
    public List<Company> pageQuery(@RequestParam int page, @RequestParam int size) {
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, companies.size());
        if (fromIndex >= companies.size()) {
            return new ArrayList<>();
        }
        return companies.subList(fromIndex, toIndex);
    }

    public void clear() {
        companies.clear();
    }


}
