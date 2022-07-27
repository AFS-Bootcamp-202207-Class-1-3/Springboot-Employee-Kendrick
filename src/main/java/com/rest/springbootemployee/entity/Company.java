package com.rest.springbootemployee.entity;

import java.util.List;

/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/26 21:19
 */

public class Company {
    private Integer id;
    private List<Employee> employees;
    private String name;

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company() {
    }

    public Company(Integer id, List<Employee> employees) {
        this.id = id;
        this.employees = employees;
    }

    public Company(Integer id, List<Employee> employees, String name) {
        this.id = id;
        this.employees = employees;
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void merge(Company company) {
        this.employees = company.getEmployees();
    }


}
