package com.rest.springbootemployee.entity.response;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/29 20:49
 */
public class CompanyResponse {
    private Integer id;

    private List<EmployeeResponse> employees = new ArrayList<>();

    private String name;

    public CompanyResponse(Integer id, List<EmployeeResponse> employees, String name) {
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

    public List<EmployeeResponse> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeResponse> employees) {
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
