package com.abdullahturhan.dto;



import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Builder
@Data
public class CreateUserRequest{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}
