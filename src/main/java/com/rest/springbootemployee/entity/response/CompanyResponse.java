package com.rest.springbootemployee.entity.response;

import com.rest.springbootemployee.entity.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/29 20:49
 */
public class CompanyResponse {
    private Integer id;

    private List<Employee> employees = new ArrayList<>();

    private String name;

    public CompanyResponse(Integer id, List<Employee> employees, String name) {
        this.id = id;
        this.employees = employees;
        this.name = name;
    }

    public CompanyResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
