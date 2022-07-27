package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
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

import static org.hamcrest.Matchers.hasSize;


/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/27 20:27
 */

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void cleanDB() {
        companyRepository.cleanAll();
    }

    @Test
    public void should_return_companies_when_getAllCompanies() throws Exception {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Kendrick", 22, "male", 20000));
        companyRepository.addACompany(new Company(1,employees,"OOCL"));

        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("OOCL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].name").value("Kendrick"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].salary").value(20000));
    }

    @Test
    public void should_create_new_company_when_perform_post_given_new_conpany() throws Exception {
        String newCompany="{\n" +
                "        \"id\": 1,\n" +
                "        \"employees\": [\n" +
                "            {\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"Kendrick\",\n" +
                "                \"age\": 22,\n" +
                "                \"gender\": \"male\",\n" +
                "                \"salary\": 20000\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"Kendrick\",\n" +
                "                \"age\": 22,\n" +
                "                \"gender\": \"male\",\n" +
                "                \"salary\": 200\n" +
                "            }\n" +
                "        ],\n" +
                "        \"name\": \"oocl\"\n" +
                "    }";

        mockMvc.perform(MockMvcRequestBuilders.post("/companies")
                .contentType(MediaType.APPLICATION_JSON).content(newCompany))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("oocl"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].name").value("Kendrick"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].salary").value(20000));
    }
//
//    @Test
//    public void should_return_employee_when_getCompany()ByID_given_id() throws Exception {
//        companyRepository.addACompany(new Company(1, "Kendrick", 22, "male", 20000));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/employees/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Kendrick"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(20000))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(22));
//    }
//
//    @Test
//    public void should_return_employeeNotFoundException_when_getCompany()ByID_given_not_found_id() throws Exception {
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/employees/1"))
//                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundOneException))
//                .andExpect(result -> assertEquals("employee not found", result.getResolvedException().getMessage()));
//
//    }
//
//    @Test
//    public void should_return_employee_when_put_employee_given_id_employee() throws Exception {
//        companyRepository.addACompany(new Company(1, "Kendraxxxxick", 22, "male", 20000));
//
//        int id=1;
//        String employee="{\n" +
//                "                \"id\": 1,\n" +
//                "                \"name\": \"Kendraxxxxick\",\n" +
//                "                \"age\": 12,\n" +
//                "                \"gender\": \"male\",\n" +
//                "                \"salary\": 9999\n" +
//                "            }";
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/employees/"+id)
//                .contentType(MediaType.APPLICATION_JSON).content(employee))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Kendraxxxxick"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(12))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(9999));
//
//    }
//
//    @Test
//    public void should_return_employee_not_found_exception_when_put_not_found_id_employee() throws Exception {
//        companyRepository.addACompany(new Company(1, "Kendraxxxxick", 22, "male", 20000));
//
//        int id=2;
//        String employee="{\n" +
//                "                \"id\": 1,\n" +
//                "                \"name\": \"Kendraxxxxick\",\n" +
//                "                \"age\": 12,\n" +
//                "                \"gender\": \"male\",\n" +
//                "                \"salary\": 9999\n" +
//                "            }";
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}",id))
//                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundOneException))
//                .andExpect(result -> assertEquals("employee not found", result.getResolvedException().getMessage()));
//    }
//
//    @Test
//    public void should_return_nothing_when_delete_employee_given_id() throws Exception {
//
//        int id=1;
//        companyRepository.addACompany(new Company(1, "Kendraxxxxick", 22, "male", 20000));
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}",id))
//                .andExpect(MockMvcResultMatchers.status().isNoContent());
//    }
}
