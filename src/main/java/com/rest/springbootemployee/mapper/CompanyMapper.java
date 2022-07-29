package com.rest.springbootemployee.mapper;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.entity.request.CompanyRequest;
import com.rest.springbootemployee.entity.request.EmployeeRequest;
import com.rest.springbootemployee.entity.response.CompanyResponse;
import com.rest.springbootemployee.entity.response.EmployeeResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/29 20:51
 */
@Component
public class CompanyMapper {
    @Autowired
    private EmployeeMapper employeeMapper;

    public Company toEntity(CompanyRequest companyRequest) {
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest, company);
        company.setEmployees(getEntities(companyRequest));
        return company;
    }

    public CompanyResponse toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company, companyResponse);
        List<EmployeeResponse> employeeResponses = employeeMapper.toResponses(company.getEmployees());
        companyResponse.setEmployees(employeeResponses);
        return companyResponse;
    }

    public List<CompanyResponse> toResponses(List<Company> companies) {
        List<CompanyResponse> responses = new ArrayList<>();
        for (Company company : companies) {
            CompanyResponse companyResponse = toResponse(company);
            List<EmployeeResponse> employeeResponses = employeeMapper.toResponses(company.getEmployees());
            companyResponse.setEmployees(employeeResponses);
            responses.add(companyResponse);
        }
        return responses;
    }

    public List<Employee> getEntities(CompanyRequest companyRequest) {
        List<Employee> employees = new ArrayList<>();

        List<EmployeeRequest> employeeRequests = companyRequest.getEmployees();
        for (EmployeeRequest employeeRequest : employeeRequests) {
            employees.add(employeeMapper.toEntity(employeeRequest));
        }

        return employees;
    }


}
