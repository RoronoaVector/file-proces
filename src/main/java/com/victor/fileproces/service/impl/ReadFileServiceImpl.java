package com.victor.fileproces.service.impl;

import com.victor.fileproces.dto.FileDto;
import com.victor.fileproces.producer.KafkaProducer;
import com.victor.fileproces.service.ReadFileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReadFileServiceImpl implements ReadFileService {

    private KafkaProducer producer;

    @Override
    public void readFiles(String path) {

        List<File> files = listFilesForFolder(new File(path));

        files.stream().forEach(this::processFile);

    }

    private List<File> listFilesForFolder(final File folder) {
        List<File> fileNames = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                try {
                    fileNames.add(fileEntry.getCanonicalFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileNames;
    }

    private void processFile(File file) {

        BufferedReader reader;
        FileDto fileDto = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            List<String> data = new ArrayList<>();
            while (line != null) {
                System.out.println(line);
                int index = line.indexOf(':') + 1;
                data.add(line.substring(index));
                // read next line
                line = reader.readLine();
            }

            fileDto = FileDto.builder()
                    .nameUser(data.get(0))
                    .surname(data.get(1))
                    .priority(data.get(2))
                    .description(data.get(3))
                    .build();

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        producer.send(fileDto);

        try {
            Path temp = Files.move
                    (Paths.get(file.getAbsolutePath()),
                            Paths.get(file.getParent() + "/history/" + file.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
