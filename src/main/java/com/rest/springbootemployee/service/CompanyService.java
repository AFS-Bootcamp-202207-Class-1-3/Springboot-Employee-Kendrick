package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NotFoundOneException;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private JpaCompanyRepository jpaCompanyRepository;

    @Autowired
    private JpaEmployeeRepository jpaEmployeeRepository;

    public Company findById(int id) {
        return jpaCompanyRepository.findById(id).orElseThrow(() -> new NotFoundOneException(Company.class.getName()));
    }

    public List<Company> getAllCompany() {
        return jpaCompanyRepository.findAll();
    }

    public Company update(int id, Company toUpdateCompany) {
        Company employee = jpaCompanyRepository.findById(id).orElseThrow(() -> new NotFoundOneException(Company.class.getName()));
        employee.merge(toUpdateCompany);
        return jpaCompanyRepository.save(employee);
    }

    public List<Company> getCompanysByPage(int page, int pageSize) {
//        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return jpaCompanyRepository.findAll(pageRequest).toList();
    }

    public void deleteCompany(Integer id) {
        jpaCompanyRepository.deleteById(id);
    }

    public Company addACompany(Company employee) {
        return jpaCompanyRepository.save(employee);
    }

    public List<Employee> findEmployeesById(Integer id) {
        return jpaEmployeeRepository.findByCompanyId(id);
    }
}
