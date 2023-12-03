package com.abdullahturhan.consumer;

import com.abdullahturhan.dto.CreateTaskPayload;
import com.abdullahturhan.dto.TaskCompletedPayload;
import com.abdullahturhan.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {
    private final NotificationService notificationService;

    public KafkaConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @KafkaListener(topics = "${kafka.topics.task-created.topicName1}",groupId = "${kafka.topics.consumerGroup}",
    containerFactory = "concurrentKafkaListenerContainerFactory")
    public void kafkaListener(@Payload CreateTaskPayload payload){
        try {
            log.info(String.format("event received  = %s",payload));
            notificationService.sendMailForCreateTaskEvent(payload);
        }catch (Exception e){
            log.error(String.format("error occurred while sending mail {Create task}  = %s ",e.getMessage()));
        }

    }

    @KafkaListener(topics = "${kafka.topics.task-completed.topicName2}",groupId = "${kafka.topics.consumerGroup}",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void kafkaListener(@Payload TaskCompletedPayload payload){
        try {
            log.info(String.format("event received = %s",payload));
            notificationService.sendMailForTaskCompleted(payload);
        }catch (Exception e){
            log.error(String.format("error occurred while sending mail {task completed} = %s",e.getMessage()));
        }
    }
}
