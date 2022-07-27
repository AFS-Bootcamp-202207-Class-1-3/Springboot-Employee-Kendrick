package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

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
public class EmployeeServiceTest {

    //    @Mock
    @Spy
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void should_return_all_employees_when_find_all_given_employees() {

        Employee employee = new Employee(1, "Kendrick", 22, "male", 20000);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        given(employeeRepository.getAllEmployee()).willReturn(employeeList);

        List<Employee> employees = employeeService.getAllEmployee();

        assertThat(employees, hasSize(1));
    }

    @Test
    public void should_return_updated_employee_when_update_given_employee() {
        int newSalary = 1999;
        Employee originEmployee = new Employee(1, "laughing", 22, "male", 80000);
        Employee toUpdateEmployee = new Employee(1, "laughing", 22, "male", newSalary);

        given(employeeRepository.findById(1)).willReturn(originEmployee);

        given(employeeRepository.updateEmployee(1, toUpdateEmployee)).willCallRealMethod();

        Employee updateEmployee = employeeService.update(1, toUpdateEmployee);

        verify(employeeRepository).updateEmployee(1, originEmployee);

    }

    @Test
    public void should_return_employee_when_get_employee_by_id_given_id() {
        int id = 1;
        Employee employee = new Employee(1, "Kendrick", 22, "male", 20000);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        given(employeeRepository.findById(id)).willReturn(employee);

        Employee employee2 = employeeService.findById(id);

        assertThat(employee, equalTo(employee2));
    }

    @Test
    public void should_return_employees_when_get_employee_by_gender() {
        String gender = "male";
        Employee employee = new Employee(1, "Kendrick", 22, "male", 20000);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        given(employeeRepository.getEmployeesByGender(gender)).willReturn(employeeList);

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

        given(employeeRepository.getEmployeeByPage(2, 1)).willReturn(employeeList);

        List<Employee> actualEmployees = employeeService.getEmployeesByPage(page, pageSize);

        assertThat(employeeList, equalTo(actualEmployees));
    }

    @Test
    public void should_return_nothing_when_delete_by_id_when_given_id(){
        int id = 1;
        Employee employee = new Employee(1, "Kendrick", 22, "male", 20000);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        employeeService.deleteEmployee(id);

        verify(employeeRepository).deleteEmployee(1);

    }
}
