package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import com.rest.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/27 20:11
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EmployeeServiceTest {

    @Spy
    private EmployeeRepository employeeRepository;

    @Spy
    private JpaEmployeeRepository jpaEmployeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void should_return_all_employees_when_find_all_given_employees() {

        Employee employee = new Employee(1, "Kendrick", 22, "male", 20000);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        given(jpaEmployeeRepository.findAll()).willReturn(employeeList);

        List<Employee> employees = employeeService.getAllEmployee();

        assertThat(employees, hasSize(1));
    }

    @Test
    public void should_return_updated_employee_when_update_given_employee() {
        int newSalary = 1999;
        Employee originEmployee = new Employee(1, "laughing", 22, "male", 80000);
        Employee toUpdateEmployee = new Employee(1, "laughing", 22, "male", newSalary);

        given(jpaEmployeeRepository.findById(1)).willReturn(Optional.of(originEmployee));

//        given(jpaEmployeeRepository.save( toUpdateEmployee)).willCallRealMethod();

        Employee updateEmployee = employeeService.update(1, toUpdateEmployee);

        verify(jpaEmployeeRepository).save(originEmployee);
        assertThat(originEmployee.getSalary(),equalTo(newSalary));

    }

    @Test
    public void should_return_employee_when_get_employee_by_id_given_id() {
        Integer id = 1;
        Employee employee = new Employee(1, "Kendrick", 22, "male", 20000);
        given(jpaEmployeeRepository.findById(id)).willReturn(Optional.of(employee));

        Employee employee2 = employeeService.findById(id);

        assertThat(employee, equalTo(employee2));
    }

    @Test
    public void should_return_employees_when_get_employee_by_gender() {
        String gender = "male";
        Employee employee = new Employee(1, "Kendrick", 22, "male", 20000);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        given(jpaEmployeeRepository.findByGender(gender)).willReturn(employeeList);

        List<Employee> employeesByGender = employeeService.getEmployeesByGender(gender);

        assertThat(employeeList, equalTo(employeesByGender));
    }

    @Test
    public void should_return_employees_when_get_employees_by_page_page_size() {
        Employee employee = new Employee(1, "Kendrick", 22, "male", 20000);
        Employee employee1 = new Employee(2, "Marcus", 22, "male", 20000);
        Employee employee2 = new Employee(3, "Jone", 22, "male", 20000);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        employeeList.add(employee1);
        employeeList.add(employee2);
        int page = 2;
        int pageSize = 1;

         Pageable pageable = PageRequest.of(page, pageSize);
        given(jpaEmployeeRepository.findAll(pageable)).willReturn(new PageImpl<>(employeeList));

        List<Employee> actualEmployees = employeeService.getEmployeesByPage(page, pageSize);

        assertThat(employeeList, equalTo(actualEmployees));
    }

    @Test
    public void should_return_nothing_when_delete_by_id_when_given_id() {
        Employee employee = new Employee(1, "Kendrick", 22, "male", 20000);

        employeeService.deleteEmployee(employee.getId());

        verify(jpaEmployeeRepository).deleteById(employee.getId());
    }

    @Test
    public void should_return_employee_when_add_given_employee() {
        Employee employee = new Employee(null, "kendirck", 22, "male", 200);

        employeeService.addAEmployee(employee);
        verify(jpaEmployeeRepository).save(employee);
    }


}
