package com.rest.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.entity.request.CompanyRequest;
import com.rest.springbootemployee.entity.request.EmployeeRequest;
import com.rest.springbootemployee.exception.NotFoundOneException;
import com.rest.springbootemployee.repository.CompanyRepository;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/27 20:27
 */

@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("test")
public class CompanyControllerTest {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JpaCompanyRepository jpaCompanyRepository;

    @Autowired
    private JpaEmployeeRepository jpaEmployeeRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void cleanDB() {
        companyRepository.cleanAll();
        jpaEmployeeRepository.deleteAll();
        jpaCompanyRepository.deleteAll();
    }

    @Test
    public void should_return_companies_when_getAllCompanies() throws Exception {
        Company company = jpaCompanyRepository.save(new Company(null, Collections.emptyList(), "oocl"));
        jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", company.getId(), 200));
        jpaEmployeeRepository.save(new Employee(2, "KKK", 22, "male", company.getId(), 200));

        Company tencent = jpaCompanyRepository.save(new Company(null, Collections.emptyList(), "tencent"));
        jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", tencent.getId(), 200));
        jpaEmployeeRepository.save(new Employee(2, "KKK", 22, "male", tencent.getId(), 200));

        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(company.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(company.getName()));
    }

    @Test
    public void should_create_new_company_when_perform_post_given_new_conpany() throws Exception {

        jpaCompanyRepository.save(new Company(null, Collections.emptyList(), "oocl"));

        CompanyRequest companyRequest = new CompanyRequest(1, new ArrayList<>(), "oocl");
        ObjectMapper objectMapper = new ObjectMapper();
        String newCompany = objectMapper.writeValueAsString(companyRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/companies")
                .contentType(MediaType.APPLICATION_JSON).content(newCompany))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("oocl"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees", hasSize(0)));

    }

    @Test
    public void should_return_employee_when_get_company_by_id_given_id() throws Exception {
        Company company = jpaCompanyRepository.save(new Company(null, Collections.emptyList(), "oocl"));
        Employee employee = jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", company.getId(), 200));
        jpaEmployeeRepository.save(new Employee(2, "KKK", 22, "male", company.getId(), 200));

        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}", company.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(company.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(company.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].name").value(employee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].gender").value(employee.getGender()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].age").value(employee.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].salary").value(employee.getSalary()));
    }

    @Test
    public void should_return_company_not_found_exception_when_get_company_by_id_given_not_found_id() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/companies/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundOneException))
                .andExpect(result -> assertEquals("com.rest.springbootemployee.entity.Company not found", result.getResolvedException().getMessage()));

    }

    @Test
    public void should_return_company_when_put_company_given_id_company() throws Exception {
        Company company = jpaCompanyRepository.save(new Company(null, Collections.emptyList(), "oocl"));
        Employee employee = jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", company.getId(), 200));
        jpaEmployeeRepository.save(new Employee(2, "KKK", 22, "male", company.getId(), 200));

        int id = company.getId();

        List<EmployeeRequest> employees = new ArrayList<>();
        employees.add(new EmployeeRequest("Kendrick", 22, "male", 100, company.getId()));
        employees.add(new EmployeeRequest("KKK", 22, "male", 200, company.getId()));
        CompanyRequest companyRequest = new CompanyRequest(company.getId(), employees, "oocl");


        ObjectMapper objectMapper = new ObjectMapper();
        String newCompany = objectMapper.writeValueAsString(companyRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/companies/{id}", id)
                .contentType(MediaType.APPLICATION_JSON).content(newCompany))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(company.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(company.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].name").value(employee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].gender").value(employee.getGender()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].age").value(employee.getAge()));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].salary").value(100));

    }

    @Test
    public void should_return_company_not_found_exception_when_put_not_found_id_employee() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundOneException))
                .andExpect(result -> assertEquals("com.rest.springbootemployee.entity.Company not found", result.getResolvedException().getMessage()));
    }

    @Test
    public void should_return_nothing_when_delete_company_given_id() throws Exception {

        Company company = jpaCompanyRepository.save(new Company(null, Collections.emptyList(), "oocl"));
        jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", company.getId(), 200));
        jpaEmployeeRepository.save(new Employee(2, "KKK", 22, "male", company.getId(), 200));


        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/{id}", company.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void should_return_employees_when_get_employees_by_company() throws Exception {
        Company company = jpaCompanyRepository.save(new Company(null, Collections.emptyList(), "oocl"));
        Employee employee = jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", company.getId(), 200));
        jpaEmployeeRepository.save(new Employee(2, "KKK", 22, "male", company.getId(), 200));

        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}/employees", company.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Kendrick"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("KKK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].salary").value(200));
    }

    @Test
    public void should_return_companies_by_page_when_given_page_and_page_size() throws Exception {
        Company company = jpaCompanyRepository.save(new Company(null, Collections.emptyList(), "oocl"));
        jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", company.getId(), 200));
        jpaEmployeeRepository.save(new Employee(2, "KKK", 22, "male", company.getId(), 200));

        Company tencent = jpaCompanyRepository.save(new Company(null, Collections.emptyList(), "tencent"));
        Employee employee = jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", tencent.getId(), 200));
        jpaEmployeeRepository.save(new Employee(2, "KKK", 22, "male", tencent.getId(), 200));
        int page = 1;
        int pageSize = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/companies?page={page}&pageSize={pageSize}", page, pageSize))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(tencent.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(tencent.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].name").value(employee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].gender").value(employee.getGender()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].age").value(employee.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].salary").value(employee.getSalary()));
    }
}
