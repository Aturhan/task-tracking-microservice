package com.abdullahturhan.dto;


import lombok.Builder;

@Builder
public record SearchUserResponse(
        String id,
        String firstName,
        String lastName,
        String role,
        String email
) {

}
