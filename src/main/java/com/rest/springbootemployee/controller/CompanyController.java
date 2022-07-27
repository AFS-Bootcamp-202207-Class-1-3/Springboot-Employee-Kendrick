package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/26 21:18
 */

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping
    public List<Company> getCompanies() {
        return companyRepository.getAllCompanies();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Integer id) {
        return companyRepository.findById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesByCompany(@PathVariable Integer id) {
        return companyRepository.getEmployees(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompaniesByPage(Integer page, Integer pageSize) {
        return companyRepository.getCompaniesByPage(page, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addACompany(@RequestBody Company company) {
        return companyRepository.addACompany(company);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable Integer id, @RequestBody Company company) {
        return companyRepository.updateCompany(id, company);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer id) {
        companyRepository.deleteEmployee(id);
    }
}
