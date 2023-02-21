package com.victor.fileproces.service.impl;

import com.victor.fileproces.client.DBConnectorClient;
import com.victor.fileproces.dto.FileEmployeeDto;
import com.victor.fileproces.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final DBConnectorClient client;

    @Override
    public InputStreamResource findAllEmployees() {
        return createInputStreamResource(client.getAllEmployees());
    }

    @Override
    public InputStreamResource findByEnterprise(String enterprise) {
        return createInputStreamResource(client.getAllEmployeesByEnterprise(enterprise));
    }

    private InputStreamResource createInputStreamResource(List<FileEmployeeDto> employeeDtos) {
        // replace this with your header (if required)
        String[] csvHeader = {
                "name", "surname", "telephone", "mail", "enterprise"
        };

        // replace this with your data retrieving logic
        List<List<String>> csvBody = new ArrayList<>();
        employeeDtos.stream().forEach(fileEmployeeDto -> {
            csvBody.add(Arrays.asList(fileEmployeeDto.getName(),fileEmployeeDto.getSurname(),
                    fileEmployeeDto.getTelephone(),fileEmployeeDto.getMail(),fileEmployeeDto.getEnterprise()));
        });

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

        return new InputStreamResource(byteArrayOutputStream);
    }
}
