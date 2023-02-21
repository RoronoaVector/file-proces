package com.victor.fileproces.utils;

import com.victor.fileproces.dto.FileEmployeeDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//posible clase de utilidades para reutilizar metodos de procesamiento de ficheros,
// se podria refactorizar el codigo par utilizar esta clase
public class FileUtils {

    public static List<String> readFile(File file) throws IOException {

        BufferedReader reader;
        List<String> data = new ArrayList<>();

        reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();

        while (line != null) {
            data.add(line);
            line = reader.readLine();
        }

        reader.close();

        return data;

    }

    public static List<String> readFile(String path) throws IOException {
        return readFile(new File(path));
    }

    public static void writeFile(File file, String data) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        bw.write(data);

        bw.close();

    }

    public static void writeFile(String path, String data) throws IOException {
        writeFile(new File(path), data);
    }

    public static List<FileEmployeeDto> readCsv (File file) throws IOException {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.EXCEL.builder()
                .setSkipHeaderRecord(true)
                .setHeader("name","surname","telephone","mail","enterprise")
                .build());

        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
        List<FileEmployeeDto> employees = new ArrayList<>();
        for (CSVRecord csvRecord : csvRecords) {
            employees.add(FileEmployeeDto.builder()
                    .name(csvRecord.get("name"))
                    .surname(csvRecord.get("surname"))
                    .telephone(csvRecord.get("telephone"))
                    .mail(csvRecord.get("mail"))
                    .enterprise(csvRecord.get("enterprise"))
                    .build());
        }

        return employees;
    }

    public static List<FileEmployeeDto> readCsv (String path) throws IOException {
        return readCsv(new File(path));
    }

}
