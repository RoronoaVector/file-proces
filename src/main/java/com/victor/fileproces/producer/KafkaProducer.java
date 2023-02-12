package com.victor.fileproces.producer;

import com.victor.fileproces.dto.FileDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    @Value(value = "${kafka.topic.destination}")
    private String topic;

    private final KafkaTemplate<String, FileDto> template;

    public void send(FileDto data) {
        template.send(topic, data);//quizas falle por que en la config de kafka esta puesto que el template sea
        // string string y no sepa convertir el objeto a json
    }
}
