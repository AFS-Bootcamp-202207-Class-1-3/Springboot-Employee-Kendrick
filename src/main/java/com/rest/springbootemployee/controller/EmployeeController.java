package com.rest.springbootemployee.controller;


import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.entity.request.EmployeeRequest;
import com.rest.springbootemployee.entity.response.EmployeeResponse;
import com.rest.springbootemployee.mapper.EmployeeMapper;
import com.rest.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<EmployeeResponse> getEmployees() {
        return employeeMapper.toResponses(employeeService.getAllEmployee());
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Integer id) {
        return employeeMapper.toResponse(employeeService.findById(id));
    }

    @GetMapping(params = {"gender"})
    public List<EmployeeResponse> getEmployeesByGender(String gender) {
        return employeeMapper.toResponses(employeeService.getEmployeesByGender(gender));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse addAEmployee(@RequestBody EmployeeRequest employeeRequest) {

        Employee employee = employeeMapper.toEntity(employeeRequest);
        employeeService.addAEmployee(employee);
        return employeeMapper.toResponse(employee);

    }

    @GetMapping(params = {"page", "pageSize"})
    public List<EmployeeResponse> getEmployeesByPage(Integer page, Integer pageSize) {
        return employeeMapper.toResponses(employeeService.getEmployeesByPage(page, pageSize));
    }

    @PutMapping("/{id}")
    public EmployeeResponse updateEmployee(@PathVariable Integer id, @RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEntity(employeeRequest);
        return employeeMapper.toResponse(employeeService.update(id, employee));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
    }
}
