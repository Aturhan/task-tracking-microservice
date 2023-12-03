package com.abdullahturhan.dto;

import com.abdullahturhan.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Builder
public record CreateTaskRequest(
        String title,
        String label,
        String description,
        LocalDate startDate,
        LocalDate finishDate,
        String email

) {
}
