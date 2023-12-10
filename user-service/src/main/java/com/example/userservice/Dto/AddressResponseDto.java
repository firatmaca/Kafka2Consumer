package com.example.userservice.Dto;

import lombok.Data;

@Data
public class AddressResponseDto {
    private Long id;
    private Long userId;
    private String addressText;
}
