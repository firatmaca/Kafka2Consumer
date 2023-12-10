package com.example.userservice.Controller;

import com.example.userservice.Dto.UserCreateRequest;
import com.example.userservice.Dto.UserResponse;
import com.example.userservice.Entity.User;
import com.example.userservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userservice;

    @PostMapping
    public User create(@RequestBody UserCreateRequest userCreateRequest) {
        return userservice.create(userCreateRequest);
    }

    @GetMapping("/{userId}")
    public UserResponse getUserAddress(@PathVariable Long userId) {
        return userservice.getUserById(userId);

    }
}
