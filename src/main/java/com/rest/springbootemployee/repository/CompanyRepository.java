package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/26 21:21
 */

@Repository
public class CompanyRepository {
    private List<Company> companies;

    public CompanyRepository() {
        this.companies = new ArrayList<>();
        ArrayList<Employee> employeesForCompany1 = new ArrayList<>();
        employeesForCompany1.add(new Employee(1, "Kendrick", 22, "male", 20000));
        employeesForCompany1.add(new Employee(2, "Kendrick", 22, "male", 200));

        ArrayList<Employee> employeesForCompany2 = new ArrayList<>();
        employeesForCompany1.add(new Employee(1, "Kendrick", 22, "male", 12345));
        employeesForCompany1.add(new Employee(2, "Kendrick", 22, "male", 12354));
        companies.add(new Company(employeesForCompany1));
    }
}
