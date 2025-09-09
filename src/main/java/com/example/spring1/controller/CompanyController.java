package com.example.spring1.controller;

import com.example.spring1.Company;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/company")
public class CompanyController {
    List<Company> companies = new ArrayList<>();

    public void clear() {
        companies.clear();
    }
}
