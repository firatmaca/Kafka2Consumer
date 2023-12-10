package com.example.userservice.Dto;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String addressText;
}
