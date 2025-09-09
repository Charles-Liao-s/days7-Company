package com.example.spring1;

import com.example.spring1.controller.CompanyController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CompanyTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompanyController companyController;

    @BeforeEach
    public void setup() {
        companyController.clear();
    }

    @Test
    public void should_return_created_company_when_post() throws Exception {
        String requestBody = """
                {
                "name": "Spring"
                }
                """;
        MockHttpServletRequestBuilder request = post("/companies").contentType(MediaType.APPLICATION_JSON).content(requestBody);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Spring"));
    }

    @Test
    public void should_return_company_when_get_company_id_correct() throws Exception {
        Company company = companyController.create(new Company(null, "Spring"));
        String id = "/" + company.id();
        MockHttpServletRequestBuilder request = get("/companies" + id).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(company.id()))
                .andExpect(jsonPath("$.name").value("Spring"));
    }

    @Test
    public void should_return_company_list_when_list_all() throws Exception {
        Company expect1 = companyController.create(new Company(null, "Spring"));
        Company expect2 = companyController.create(new Company(null, "Java"));

        MockHttpServletRequestBuilder request = get("/companies").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[0].id").value(expect1.id()))
                .andExpect(jsonPath("$.[0].name").value(expect1.name()))
                .andExpect(jsonPath("$.[1].id").value(expect2.id()))
                .andExpect(jsonPath("$.[1].name").value(expect2.name()));
    }

}
