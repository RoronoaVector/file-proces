package com.victor.fileproces.client;

import com.victor.fileproces.dto.FileEmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "database-connector", url = "${client.database-connection}")
public interface DBConnectorClient {

    @GetMapping("/employees")
    List<FileEmployeeDto> getAllEmployees();

    @GetMapping("/employees/enterprise/{enterprise}")
    List<FileEmployeeDto> getAllEmployeesByEnterprise(@PathVariable String enterprise);

}
