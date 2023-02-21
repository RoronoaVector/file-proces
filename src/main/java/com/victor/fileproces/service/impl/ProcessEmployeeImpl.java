package com.victor.fileproces.service.impl;

import com.victor.fileproces.dto.FileEmployeeDto;
import com.victor.fileproces.producer.KafkaProducer;
import com.victor.fileproces.service.ProcessEmployee;
import com.victor.fileproces.utils.FileUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProcessEmployeeImpl implements ProcessEmployee {

    private KafkaProducer producer;

    @Override
    public void saveEmployees(String path) {
        List<FileEmployeeDto> employees = new ArrayList<>();
        try {
            employees = FileUtils.readCsv(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //comprobar que el formato del fichero es el adecuado?¿?¿

        //List<FileEmployee> employees = lines.stream().map(this::parseFile).collect(Collectors.toList());

        employees.stream().forEach(this::send);

    }

    //otra posible forma de parsear los datos del fichero
    private FileEmployeeDto parseFile(String line) {
        List<String> fields = List.of(line.split(","));
        return FileEmployeeDto.builder()
                .name(fields.get(0))
                .surname(fields.get(1))
                .telephone(fields.get(2))
                .mail(fields.get(3))
                .enterprise(fields.get(4))
                .build();
    }

    private void send(FileEmployeeDto data) {
        producer.send(data);
    }
}
