package com.example.userservice.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserResponse extends  BaseResponseDto{
    private String firstName;
    private String lastName;
    private String email;
    private AddressResponseDto address;
}
