package com.abdullahturhan.dto;


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


}
