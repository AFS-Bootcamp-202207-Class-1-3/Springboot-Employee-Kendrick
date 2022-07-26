package com.rest.springbootemployee.entity;

import java.util.List;

/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/26 21:19
 */

public class Company {
    private int id;
    private List<Employee> employees;

    public Company(int id, List<Employee> employees) {
        this.id = id;
        this.employees = employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public int getId() {
        return id;
    }
}
