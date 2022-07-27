package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee findById(int id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getAllEmployee() {
        return employeeRepository.getAllEmployee();
    }

    public Employee update(int id, Employee toUpdateEmployee) {
        Employee employee = employeeRepository.findById(id);
        employee.merge(toUpdateEmployee);
        return employeeRepository.updateEmployee(1, employee);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.getEmployeesByGender(gender);
    }

    public List<Employee> getEmployeesByPage(int page, int pageSize) {
        return employeeRepository.getEmployeeByPage(page, pageSize);
    }

    public void deleteEmployee(Integer id) {
        employeeRepository.deleteEmployee(id);
    }

    public Employee addAEmployee(Employee employee) {
        int id = employeeRepository.generateMaxId();
        employee.setId(id);
        return employeeRepository.addAEmployee(employee);
    }
}
