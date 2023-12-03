package com.abdullahturhan.dto;

import com.abdullahturhan.model.Role;
import com.abdullahturhan.model.User;
import lombok.Builder;

@Builder
public record CreateUserRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        Role role
) {

}
