package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Kendrick Chen
 * @Date: 2022/07/28/12:22 PM
 * @Mail: KENDRICK.CHEN@OOCL.COM
 */
@Repository
public interface JpaEmployeeRepository extends JpaRepository<Employee,Integer> {
    List<Employee> findByGender(String gender);
    List<Employee> findByCompanyId(Integer companyId);

}
