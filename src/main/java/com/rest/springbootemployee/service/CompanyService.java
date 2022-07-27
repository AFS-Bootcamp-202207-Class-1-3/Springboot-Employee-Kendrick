package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/27 23:16
 */
@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company findById(int id) {
        return companyRepository.findById(id);
    }

    public List<Company> getAllCompany() {
        return companyRepository.getAllCompanies();
    }

    public Company update(int id, Company toUpdateCompany) {
        Company employee = companyRepository.findById(id);
        employee.merge(toUpdateCompany);
        return companyRepository.updateCompany(1, employee);
    }

    public List<Company> getCompanysByPage(int page, int pageSize) {
        return companyRepository.getCompaniesByPage(page, pageSize);
    }

    public void deleteCompany(Integer id) {
        companyRepository.deleteEmployee(id);
    }

    public Company addACompany(Company employee) {
        int id = companyRepository.generateMaxId();
        employee.setId(id);
        return companyRepository.addACompany(employee);
    }
}
