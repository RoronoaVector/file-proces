package com.victor.fileproces.controlers;

import com.victor.fileproces.dto.FileEmployeeRequestDto;
import com.victor.fileproces.service.EmployeeService;
import com.victor.fileproces.service.ProcessEmployee;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EmployeeControler {

    private final ProcessEmployee service;
    private final EmployeeService employeeService;

    @PostMapping("/employees/process")
    public void processEmployee(@RequestBody FileEmployeeRequestDto request) {
        System.out.println(request.getPath());
        service.saveEmployees(request.getPath());
    }

    @GetMapping(value = "/employees", produces = "text/csv")
    public ResponseEntity getAllEmployees() {

        InputStreamResource fileInputStream = employeeService.findAllEmployees();

        String csvFileName = "people.csv";

        // setting HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
        // defining the custom Content-Type
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity(
                fileInputStream,
                headers,
                HttpStatus.OK
        );

    }

    @GetMapping(value = "/employees/enterprise/{enterprise}", produces = "text/csv")
    public ResponseEntity getAllEmployeesByEnterprise(@PathVariable String enterprise) {

        InputStreamResource fileInputStream = employeeService.findByEnterprise(enterprise);

        String csvFileName = "people.csv";

        // setting HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
        // defining the custom Content-Type
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity(
                fileInputStream,
                headers,
                HttpStatus.OK
        );

    }

}
