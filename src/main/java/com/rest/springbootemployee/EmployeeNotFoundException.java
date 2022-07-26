package com.rest.springbootemployee;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException() {
        super("employee not found");
    }
}
