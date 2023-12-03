package com.abdullahturhan.service;

import com.abdullahturhan.dto.CreateTaskPayload;
import com.abdullahturhan.dto.TaskCompletedPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String FROM;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMailForCreateTaskEvent(CreateTaskPayload payload){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM);
        message.setSubject(payload.taskTitle());
        message.setText(createText(payload));
        message.setTo(payload.email());
        mailSender.send(message);
    }

    public void sendMailForTaskCompleted(TaskCompletedPayload payload){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM);
        message.setSubject(payload.title());
        message.setText(createText(payload));
        message.setTo(payload.email());
        mailSender.send(message);
    }

    private String createText(TaskCompletedPayload payload){
        String text = "";
        return text = "Task id = " +payload.taskId() +"\n  Dear, " + payload.firstName() + " " + payload.lastName()
                + " \n task completed and see your feedback = "+ payload.feedback();
    }

    private String createText(CreateTaskPayload payload){
        String text = "";
        return  text = "Task id = " + payload.taskId() + " \n Dear, " + payload.firstName()+ " " +payload.lastName() +
                                                "\n Task title = " +payload.taskTitle() + "\n" +
                                                 "description = " + payload.taskDescription();

    }
}
