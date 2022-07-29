package com.rest.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.entity.EmployeeRequest;
import com.rest.springbootemployee.exception.NotFoundOneException;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/27 20:13
 */

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JpaEmployeeRepository jpaEmployeeRepository;

    @Autowired
    private JpaCompanyRepository jpaCompanyRepository;

    //  ����һ���տǹ�˾��ֹ�������
    private Company prepareCompany;

    private Integer COMPANY_ID;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void cleanDB() {
        employeeRepository.cleanAll();
        jpaEmployeeRepository.deleteAll();

        Company company = new Company();
        prepareCompany = jpaCompanyRepository.save(company);
        COMPANY_ID = prepareCompany.getId();
    }

    @Test
    public void should_return_employees_when_getAllEmployees() throws Exception {
        Employee employee = jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", COMPANY_ID, 20000));
//        employeeRepository.addAEmployee(new Employee(1, "Kendrick", 22, "male", 20000));


        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value(employee.getGender()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(employee.getAge()));
    }

    @Test
    public void should_create_new_employee_when_perform_post_given_new_employee() throws Exception {
        EmployeeRequest employeeRequest = new EmployeeRequest("Kendraxxxxick",12,"male",200,1);
        ObjectMapper objectMapper=new ObjectMapper();
        String employee = objectMapper.writeValueAsString(employeeRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Kendraxxxxick"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(12));
    }

    @Test
    public void should_return_employee_when_getEmployeeByID_given_id() throws Exception {
        Employee employee = jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", COMPANY_ID, 20000));

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Kendrick"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(22));
    }

    @Test
    public void should_return_employees_when_getEmployeeByID_given_gender() throws Exception {
        jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", COMPANY_ID, 20000));
        String gender = "male";
        mockMvc.perform(MockMvcRequestBuilders.get("/employees?gender={gender}", gender))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Kendrick"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(22));
    }

    @Test
    public void should_return_employeeNotFoundException_when_getEmployeeByID_given_not_found_id() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundOneException))
                .andExpect(result -> assertEquals("com.rest.springbootemployee.entity.Employee not found", result.getResolvedException().getMessage()));

    }

    @Test
    public void should_return_employee_when_put_employee_given_id_employee() throws Exception {
        Employee employeeReturn = jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", COMPANY_ID, 20000));

        EmployeeRequest employeeRequest = new EmployeeRequest("Kendraxxxxick",12,"male",200,1);
        ObjectMapper objectMapper=new ObjectMapper();
        String employee = objectMapper.writeValueAsString(employeeRequest);


        mockMvc.perform(MockMvcRequestBuilders.put("/employees/" + employeeReturn.getId())
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Kendraxxxxick"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(12));
    }

    @Test
    public void should_return_employee_not_found_exception_when_put_not_found_id_employee() throws Exception {
//        Employee employeeReturn = jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", COMPANY_ID, 20000));

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundOneException))
                .andExpect(result -> assertEquals("com.rest.springbootemployee.entity.Employee not found", result.getResolvedException().getMessage()));
    }

    @Test
    public void should_return_nothing_when_delete_employee_given_id() throws Exception {

        int id = 1;
        Employee employee = jpaEmployeeRepository.save(new Employee(1, "Kendraxxxxick", 22, "male", COMPANY_ID, 20000));

        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}

