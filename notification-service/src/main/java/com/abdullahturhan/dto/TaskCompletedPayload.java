package com.abdullahturhan.dto;


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

}
