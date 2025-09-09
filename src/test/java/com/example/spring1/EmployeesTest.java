package com.example.spring1;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.example.spring1.controller.Employee;
import com.example.spring1.controller.EmployeeController;
import org.junit.jupiter.api.BeforeEach;
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

  @BeforeEach
  public void setup() {
    employeeController.clear();
  }

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
        .andExpect(jsonPath("$.id").value(employee.id()))
        .andExpect(jsonPath("$.name").value(employee.name()))
        .andExpect(jsonPath("$.age").value(employee.age()))
        .andExpect(jsonPath("$.gender").value(employee.gender()))
        .andExpect(jsonPath("$.salary").value(employee.salary()));
  }

  @Test
  public void should_return_males_when_list_by_male() throws Exception {
    Employee expect = employeeController.create(new Employee(null, "John", 32, "male", 5000.0));
    employeeController.create(new Employee(null, "John", 32, "female", 5000.0));

    MockHttpServletRequestBuilder request = get("/employees?gender=male")
            .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$.[0].id").value(expect.id()))
            .andExpect(jsonPath("$.[0].name").value(expect.name()))
            .andExpect(jsonPath("$.[0].age").value(expect.age()))
            .andExpect(jsonPath("$.[0].gender").value(expect.gender()))
            .andExpect(jsonPath("$.[0].salary").value(expect.salary()));
  }

  @Test
  public void should_return_employee_list_when_list_all() throws Exception {
    Employee expect1 = employeeController.create(new Employee(null, "John", 32, "male", 5000.0));
    Employee expect2 = employeeController.create(new Employee(null, "Jane", 28, "male", 6000.0));
    MockHttpServletRequestBuilder request = get("/employees")
            .contentType(MediaType.APPLICATION_JSON);
    mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$.[0].id").value(expect1.id()))
            .andExpect(jsonPath("$.[0].name").value(expect1.name()))
            .andExpect(jsonPath("$.[0].age").value(expect1.age()))
            .andExpect(jsonPath("$.[0].gender").value(expect1.gender()))
            .andExpect(jsonPath("$.[0].salary").value(expect1.salary()))
            .andExpect(jsonPath("$.[1].id").value(expect2.id()))
            .andExpect(jsonPath("$.[1].name").value(expect2.name()))
            .andExpect(jsonPath("$.[1].age").value(expect2.age()))
            .andExpect(jsonPath("$.[1].gender").value(expect2.gender()))
            .andExpect(jsonPath("$.[1].salary").value(expect2.salary()));
  }

  @Test
    public void should_return_updated_employee_when_update_employee() throws Exception {
        Employee employee = employeeController.create(new Employee(null, "John", 32, "male", 5000.0));
        String id = "/" + employee.id();
        String requestBody = """
                {
                "name": "John",
                "age": 33,
                "gender": "Male",
                "salary": 6000.0
                }
        """;
        MockHttpServletRequestBuilder request = put("/employees" + id).contentType(MediaType.APPLICATION_JSON).content(requestBody);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.id()))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.age").value(33))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(6000.0));
            }

}
