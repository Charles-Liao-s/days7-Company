package com.example.spring1;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.example.spring1.controller.Employee;
import com.example.spring1.controller.EmployeeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;


@SpringBootTest
@AutoConfigureMockMvc
public class EmployeesTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private EmployeeController employeeController;

  @Test
  public void should_return_created_employees_when_post() throws Exception {
    //given
    String requestBody = """
                {
                "name": "John Smith",
                "age": 32,
                "gender": "Male",
                "salary": 5000.0
                }
        """;
    MockHttpServletRequestBuilder request = post("/employees").contentType(MediaType.APPLICATION_JSON).content(requestBody);
    mockMvc.perform(request)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value("John Smith"))
        .andExpect(jsonPath("$.age").value(32))
        .andExpect(jsonPath("$.gender").value("Male"))
        .andExpect(jsonPath("$.salary").value(5000.0));
  }
  @Test
  public void should_return_employees_when_get_employees_id_correct() throws Exception {
    //given
    Employee employee = employeeController.create(new Employee(2, "John Smith", 32, "Male", 5000.0));
    String id = "/"+employee.id();
    MockHttpServletRequestBuilder request = get("/employees"+id).contentType(MediaType.APPLICATION_JSON);
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.name").value("John Smith"))
        .andExpect(jsonPath("$.age").value(32))
        .andExpect(jsonPath("$.gender").value("Male"))
        .andExpect(jsonPath("$.salary").value(5000.0));
  }


}
