package com.victor.fileproces.producer;

import com.victor.fileproces.dto.FileDto;
import com.victor.fileproces.dto.FileEmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    @Value(value = "${kafka.topic.destination.files}")
    private String topicFile;

    @Value(value = "${kafka.topic.destination.employees}")
    private String topicEmployee;

    @Autowired
    @Qualifier("FileTemplate")
    private KafkaTemplate<String, FileDto> templateFile;

    @Autowired
    @Qualifier("EmployeeTemplate")
    private KafkaTemplate<String, FileEmployeeDto> templateEmployee;

    public void send(FileDto data) {
        templateFile.send(topicFile, data);
    }

    public void send(FileEmployeeDto data) {
        templateEmployee.send(topicEmployee, data);
    }
//TODO https://stackoverflow.com/questions/38549657/is-it-possible-to-add-qualifiers-in-requiredargsconstructoronconstructor

}
