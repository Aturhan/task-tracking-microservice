package com.abdullahturhan.dto;

import com.abdullahturhan.model.User;
import lombok.Builder;

@Builder
public record CreateUserResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String role
) {
    public static CreateUserResponse converter(User user){
        return CreateUserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().getValue())
                .build();
    }
}
