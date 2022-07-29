package com.rest.springbootemployee.entity.request;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/29 20:47
 */

public class CompanyRequest {
    private Integer id;

    private List<EmployeeRequest> employees = new ArrayList<>();

    private String name;

    public CompanyRequest(Integer id, List<EmployeeRequest> employees, String name) {
        this.id = id;
        this.employees = employees;
        this.name = name;
    }

    public CompanyRequest() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<EmployeeRequest> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeRequest> employees) {
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
