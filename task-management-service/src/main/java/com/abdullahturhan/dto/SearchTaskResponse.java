package com.abdullahturhan.dto;

import com.abdullahturhan.model.Task;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record SearchTaskResponse(
        String id,
        String title,
        String label,
        String userEmail,
        LocalDate startDate,
        LocalDate finishDate
) {
   public static List<SearchTaskResponse> converter(List<Task> task){
        return task.stream().map(model -> SearchTaskResponse.builder()
                .id(model.getId())
                .title(model.getTitle())
                .label(model.getLabel())
                .userEmail(model.getUser().getEmail())
                .startDate(model.getStartDate())
                .finishDate(model.getFinishDate())
                .build()).collect(Collectors.toList());
    }
}
