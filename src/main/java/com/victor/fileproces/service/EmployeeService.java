package com.victor.fileproces.service;

import org.springframework.core.io.InputStreamResource;


public interface EmployeeService {

    InputStreamResource findAllEmployees();

    InputStreamResource findByEnterprise(String enterprise);

}
