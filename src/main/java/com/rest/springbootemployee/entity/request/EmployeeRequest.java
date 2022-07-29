package com.rest.springbootemployee.entity.request;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Kendrick Chen
 * @Date: 2022/07/29/11:35 AM
 * @Mail: KENDRICK.CHEN@OOCL.COM
 */
public class EmployeeRequest {

    private String name;

    private Integer age;

    private String gender;

    private Integer salary;

    private Integer companyId;

    public EmployeeRequest() {
    }

    public EmployeeRequest(String name, Integer age, String gender, Integer salary, Integer companyId) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
