package com.rest.springbootemployee.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/26 21:19
 */
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "companyId")
    private List<Employee> employees=new ArrayList<>();
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
        this.name=company.getName();

    }


}
