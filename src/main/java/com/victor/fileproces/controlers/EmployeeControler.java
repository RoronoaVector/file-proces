package com.victor.fileproces.controlers;

import com.victor.fileproces.dto.FileEmployeeRequestDto;
import com.victor.fileproces.service.ProcessEmployee;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeControler {

    private final ProcessEmployee service;

    @PostMapping("/process")
    public void processEmployee(@RequestBody FileEmployeeRequestDto request) {
        System.out.println(request.getPath());
        service.saveEmployees(request.getPath());
    }

    @GetMapping(value = "/exportCSV", produces = "text/csv")
    public ResponseEntity<Resource> exportCSV() {
        // replace this with your header (if required)
        String[] csvHeader = {
                "name", "surname", "age"
        };

        // replace this with your data retrieving logic
        List<List<String>> csvBody = new ArrayList<>();
        csvBody.add(Arrays.asList("Patricia", "Williams", "25"));
        csvBody.add(Arrays.asList("John", "Smith", "44"));
        csvBody.add(Arrays.asList("Douglas", "Brown", "31"));

        ByteArrayInputStream byteArrayOutputStream;

        // closing resources by using a try with resources
        // https://www.baeldung.com/java-try-with-resources
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                // defining the CSV printer
                CSVPrinter csvPrinter = new CSVPrinter(
                        new PrintWriter(out),
                        // withHeader is optional
                        CSVFormat.DEFAULT.withHeader(csvHeader)
                );
        ) {
            // populating the CSV content
            for (List<String> record : csvBody)
                csvPrinter.printRecord(record);

            // writing the underlying stream
            csvPrinter.flush();

            byteArrayOutputStream = new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        InputStreamResource fileInputStream = new InputStreamResource(byteArrayOutputStream);

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
