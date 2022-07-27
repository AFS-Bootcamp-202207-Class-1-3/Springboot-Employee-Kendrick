package com.rest.springbootcompany;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/27 21:02
 */

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    //    @Mock
    @Spy
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    public void should_return_all_companys_when_find_all_given_companys() {

        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Kendrick", 22, "male", 20000));
        List<Company> companyList = new ArrayList<>();
        Company company = new Company(1, employees, "oocl");

        companyList.add(company);

        given(companyRepository.getAllCompanies()).willReturn(companyList);

        List<Company> companys = companyService.getAllCompany();

        assertThat(companys, hasSize(1));
    }

//    @Test
//    public void should_return_updated_company_when_update_given_company() {
//        int newSalary = 1999;
//        Company originCompany = new Company(1, "laughing", 22, "male", 80000);
//        Company toUpdateCompany = new Company(1, "laughing", 22, "male", newSalary);
//
//        given(companyRepository.findById(1)).willReturn(originCompany);
//
//        given(companyRepository.updateCompany(1, toUpdateCompany)).willCallRealMethod();
//
//        Company updateCompany = companyService.update(1, toUpdateCompany);
//
//        verify(companyRepository).updateCompany(1, originCompany);
//
//    }
//
//    @Test
//    public void should_return_company_when_get_company_by_id_given_id() {
//        int id = 1;
//        Company company = new Company(1, "Kendrick", 22, "male", 20000);
//        List<Company> companyList = new ArrayList<>();
//        companyList.add(company);
//
//        given(companyRepository.findById(id)).willReturn(company);
//
//        Company company2 = companyService.findById(id);
//
//        assertThat(company, equalTo(company2));
//    }
//
//    @Test
//    public void should_return_companys_when_get_company_by_gender() {
//        String gender = "male";
//        Company company = new Company(1, "Kendrick", 22, "male", 20000);
//        List<Company> companyList = new ArrayList<>();
//        companyList.add(company);
//
//        given(companyRepository.getCompanysByGender(gender)).willReturn(companyList);
//
//        List<Company> companysByGender = companyService.getCompanysByGender(gender);
//
//        assertThat(companyList, equalTo(companysByGender));
//    }
//
//    @Test
//    public void should_return_companys_when_get_companys_by_page_page_size() {
//        Company company = new Company(1, "Kendrick", 22, "male", 20000);
//        Company company1 = new Company(2, "Marcus", 22, "male", 20000);
//        Company company2 = new Company(3, "Jone", 22, "male", 20000);
//        List<Company> companyList = new ArrayList<>();
//        companyList.add(company);
//        companyList.add(company1);
//        companyList.add(company2);
//        int page = 2;
//        int pageSize = 1;
//
//        given(companyRepository.getCompanyByPage(2, 1)).willReturn(companyList);
//
//        List<Company> actualCompanys = companyService.getCompanysByPage(page, pageSize);
//
//        assertThat(companyList, equalTo(actualCompanys));
//    }
//
//    @Test
//    public void should_return_nothing_when_delete_by_id_when_given_id() {
//        int id = 1;
//        Company company = new Company(1, "Kendrick", 22, "male", 20000);
//        List<Company> companyList = new ArrayList<>();
//        companyList.add(company);
//
//        companyService.deleteCompany(id);
//
//        verify(companyRepository).deleteCompany(1);
//    }
//
//    @Test
//    public void should_return_company_when_add_given_company() {
//        Company company = new Company(null, "kendirck", 22, "male", 200);
//        given(companyRepository.generateMaxId()).willReturn(2);
//
//        companyService.addACompany(company);
//        verify(companyRepository).addACompany(company);
//    }


}

