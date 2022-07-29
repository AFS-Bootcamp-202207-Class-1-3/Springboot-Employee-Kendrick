package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.entity.response.CompanyResponse;
import com.rest.springbootemployee.entity.response.EmployeeResponse;
import com.rest.springbootemployee.mapper.CompanyMapper;
import com.rest.springbootemployee.mapper.EmployeeMapper;
import com.rest.springbootemployee.service.CompanyService;
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
    private CompanyService companyService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<CompanyResponse> getCompanies() {
        return companyMapper.toResponses(companyService.getAllCompany());
    }

    @GetMapping("/{id}")
    public CompanyResponse getCompanyById(@PathVariable Integer id) {
        return companyMapper.toResponse(companyService.findById(id));
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeResponse> getEmployeesByCompany(@PathVariable Integer id) {
        return employeeMapper.toResponses(companyService.findEmployeesById(id));
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getCompaniesByPage(Integer page, Integer pageSize) {
        return companyMapper.toResponses(companyService.getCompanysByPage(page, pageSize));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addACompany(@RequestBody Company company) {
        return companyService.addACompany(company);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable Integer id, @RequestBody Company company) {
        return companyService.update(id, company);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer id) {
        companyService.deleteCompany(id);
    }
}
