package com.example.userservice.Dto;

import lombok.Data;

import java.util.Date;
@Data
public class UserCreatedPayload {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String addressText;
    private Date createdAt;
    private Date updatedAt;
    private Boolean status;
}
