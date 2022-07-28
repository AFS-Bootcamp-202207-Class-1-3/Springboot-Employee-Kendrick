package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/28 19:35
 */
@Repository
public interface JpaCompanyRepository extends JpaRepository<Company, Integer> {
}
