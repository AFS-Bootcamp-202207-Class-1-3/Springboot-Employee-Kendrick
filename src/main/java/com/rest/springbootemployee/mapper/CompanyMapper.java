package com.rest.springbootemployee.mapper;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.request.CompanyRequest;
import com.rest.springbootemployee.entity.response.CompanyResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/29 20:51
 */
@Component
public class CompanyMapper {
    public Company toEntity(CompanyRequest companyRequest){
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest, company);
        return company;
    }

    public CompanyResponse toResponse(Company company){
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company ,companyResponse);
        return companyResponse;
    }

    public List<CompanyResponse> toResponses(List<Company> companies){
        List<CompanyResponse> responses = new ArrayList<>();
        for (Company company : companies) {
            responses.add(toResponse(company));
        }
        return responses;
    }
}
