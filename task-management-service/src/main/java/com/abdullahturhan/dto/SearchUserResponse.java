package com.abdullahturhan.dto;

import com.abdullahturhan.model.User;
import lombok.Builder;

@Builder
public record SearchUserResponse(
        String id,
        String firstName,
        String lastName,
        String role,
        String email
) {
   public static SearchUserResponse converter(User user){
        return SearchUserResponse.builder()
                .id(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
