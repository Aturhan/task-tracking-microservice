package com.abdullahturhan.dto;


import lombok.Builder;

@Builder
public record CreateUserResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String role
) {

}
