package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NotFoundOneException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        employeesForCompany2.add(new Employee(1, "Kendrick", 22, "male", 12345));
        employeesForCompany2.add(new Employee(2, "Kendrick", 22, "male", 12354));
        companies.add(new Company(1, employeesForCompany1));
        companies.add(new Company(2, employeesForCompany2));
    }

    public List<Company> getAllCompanies() {
        return companies;
    }

    public Company findById(Integer id) {
        return companies.stream().filter(employee -> employee.getId() == id).findFirst().orElseThrow(() -> new NotFoundOneException(Company.class.getName()));
    }


    public List<Employee> getEmployees(Integer id) {
        return companies.stream().map(company -> company.getEmployees())
                .findFirst().orElseThrow(() -> new NotFoundOneException(Company.class.getName()));
    }

    public List<Company> getCompaniesByPage(Integer page, Integer pageSize) {
        return companies.stream()
                .skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());

    }

    public Company addACompany(Company company) {
        company.setId(generateMaxId());
        companies.add(company);
        return company;
    }


    private Integer generateMaxId() {
        return companies.stream().mapToInteger(employee -> employee.getId()).max().orElse(0) + 1;
    }

    public void updateCompany(Integer id, Company company) {
        Company companyForUpdate = findById(id);
        companyForUpdate.merge(company);
    }
}
