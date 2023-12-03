package com.abdullahturhan.producer;

import com.abdullahturhan.dto.CreateTaskPayload;
import com.abdullahturhan.dto.TaskCompletedPayload;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(String topic, CreateTaskPayload payload){
        kafkaTemplate.send(topic,payload);

    }

    public void publish(String topic, TaskCompletedPayload payload){
        kafkaTemplate.send(topic,payload);

    }
}
