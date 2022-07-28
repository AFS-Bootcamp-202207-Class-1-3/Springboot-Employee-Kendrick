package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NotFoundOneException;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JpaEmployeeRepository jpaEmployeeRepository;

    public Employee findById(int id) {
        return jpaEmployeeRepository.findById(id).orElseThrow(() -> new NotFoundOneException(Employee.class.getName()));
    }

    public List<Employee> getAllEmployee() {
        return jpaEmployeeRepository.findAll();
    }

    public Employee update(int id, Employee toUpdateEmployee) {
        Employee employee = jpaEmployeeRepository.findById(id).get();
        employee.merge(toUpdateEmployee);
        return jpaEmployeeRepository.save(employee);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return jpaEmployeeRepository.findByGender(gender);
    }

    public List<Employee> getEmployeesByPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return jpaEmployeeRepository.findAll(pageable).toList();
    }

    public void deleteEmployee(Integer id) {
        jpaEmployeeRepository.deleteById(id);
    }


    public Employee addAEmployee(Employee employee) {
        return jpaEmployeeRepository.save(employee);
    }
}
