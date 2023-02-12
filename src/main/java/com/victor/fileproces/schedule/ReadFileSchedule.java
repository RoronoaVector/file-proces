package com.victor.fileproces.schedule;

import com.victor.fileproces.service.ReadFileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadFileSchedule {

    private final ReadFileService service;

    @Value(value = "${files.path}")
    private String path;

    @Scheduled(fixedDelay = 10000)//10 segundus
    public void scheduleFixedDelayTask() {
        System.out.println("Llamada schedule");
        service.readFiles(path);//mover el path al fichero de config
    }
}
