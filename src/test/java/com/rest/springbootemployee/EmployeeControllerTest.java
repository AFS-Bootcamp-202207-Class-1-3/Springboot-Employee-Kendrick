package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
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

    //  生成一个空壳公司防止外键出错
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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(employee.getSalary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(employee.getAge()));
    }

    @Test
    public void should_create_new_employee_when_perform_post_given_new_employee() throws Exception {
        String newEmployee = "{\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"Kendraxxxxick\",\n" +
                "                \"age\": 12,\n" +
                "                \"gender\": \"male\",\n" +
                "                \"salary\": 30000\n" +
                "            }";

        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON).content(newEmployee))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Kendraxxxxick"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(30000));
    }

    @Test
    public void should_return_employee_when_getEmployeeByID_given_id() throws Exception {
        Employee employee = jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", COMPANY_ID, 20000));

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Kendrick"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(20000))
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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(20000))
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

        int id = 1;
        String employee = "{\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"Kendraxxxxick\",\n" +
                "                \"age\": 22,\n" +
                "                \"gender\": \"male\",\n" +
                "                \"salary\": 9999\n" +
                "            }";

        mockMvc.perform(MockMvcRequestBuilders.put("/employees/" + employeeReturn.getId())
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Kendraxxxxick"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(9999));

    }

    @Test
    public void should_return_employee_not_found_exception_when_put_not_found_id_employee() throws Exception {
        employeeRepository.addAEmployee(new Employee(1, "Kendraxxxxick", 22, "male", COMPANY_ID, 20000));

        int id = 2;
        String employee = "{\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"Kendraxxxxick\",\n" +
                "                \"age\": 12,\n" +
                "                \"gender\": \"male\",\n" +
                "                \"salary\": 9999\n" +
                "            }";

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", id))
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

