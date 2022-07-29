package com.rest.springbootemployee.mapper;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.entity.request.EmployeeRequest;
import com.rest.springbootemployee.entity.response.EmployeeResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Kendrick Chen
 * @Date: 2022/07/29/11:34 AM
 * @Mail: KENDRICK.CHEN@OOCL.COM
 */

@Component
public class EmployeeMapper {
    public Employee toEntity(EmployeeRequest employeeRequest){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest, employee);
        return employee;
    }

    public EmployeeResponse toResponse(Employee employee){
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee ,employeeResponse);
        return employeeResponse;
    }

    public List<EmployeeResponse> toResponses(List<Employee> employees){
        List<EmployeeResponse> responses = new ArrayList<>();
        for (Employee employee : employees) {
            responses.add(toResponse(employee));
        }
        return responses;
    }
}
