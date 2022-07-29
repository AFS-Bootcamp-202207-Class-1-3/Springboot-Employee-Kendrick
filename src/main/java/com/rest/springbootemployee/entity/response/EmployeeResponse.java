package com.rest.springbootemployee.entity.response;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Kendrick Chen
 * @Date: 2022/07/29/11:38 AM
 * @Mail: KENDRICK.CHEN@OOCL.COM
 */
public class EmployeeResponse {
    private Integer id;

    private String name;

    private Integer age;

    private String gender;

    public EmployeeResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
