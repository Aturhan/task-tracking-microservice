package com.abdullahturhan.dto;

import com.abdullahturhan.model.Task;
import lombok.Builder;

@Builder
public record CreateTaskPayload(
       String userId,
       String firstName,
       String lastName,
       String email,
       String taskId,
       String taskTitle,
       String taskLabel,
       String taskDescription
) {
    public static CreateTaskPayload converter(Task task){
        return CreateTaskPayload.builder()
                .userId(task.getUser().getUserId())
                .firstName(task.getUser().getFirstName())
                .lastName(task.getUser().getLastName())
                .email(task.getUser().getEmail())
                .taskId(task.getId())
                .taskTitle(task.getTitle())
                .taskLabel(task.getLabel())
                .taskDescription(task.getDescription())
                .build();
    }
}
