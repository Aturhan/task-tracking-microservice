package com.abdullahturhan.dto;

import com.abdullahturhan.model.Task;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TaskCompletedPayload(
        String email,
        String firstName,
        String lastName,
        String taskId,
        String title,
        String status,
        LocalDate startDate,
        LocalDate finishTime,
        String feedback
) {
    public static  TaskCompletedPayload payload(Task task, String feedback){
        return TaskCompletedPayload.builder()
                .email(task.getUser().getEmail())
                .firstName(task.getUser().getFirstName())
                .lastName(task.getUser().getLastName())
                .taskId(task.getId())
                .title(task.getTitle())
                .status(task.getStatus().getValue())
                .startDate(task.getStartDate())
                .finishTime(task.getFinishDate())
                .feedback(feedback)
                .build();
    }
}
