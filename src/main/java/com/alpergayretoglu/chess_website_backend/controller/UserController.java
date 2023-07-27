package com.alpergayretoglu.chess_website_backend.controller;

import com.alpergayretoglu.chess_website_backend.model.request.auth.ResetPasswordRequest;
import com.alpergayretoglu.chess_website_backend.model.request.user.CreateUserRequest;
import com.alpergayretoglu.chess_website_backend.model.request.user.UpdateUserRequest;
import com.alpergayretoglu.chess_website_backend.model.response.UserResponse;
import com.alpergayretoglu.chess_website_backend.service.AuthenticationService;
import com.alpergayretoglu.chess_website_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @ApiPageable
    @GetMapping
    public Page<UserResponse> listUsers(@ApiIgnore Pageable pageable) {
        return userService.listUsers(authenticationService.getAuthenticatedUser(), pageable);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable String userId) {
        return userService.getUser(authenticationService.getAuthenticatedUser(), userId);
    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(authenticationService.getAuthenticatedUser(), createUserRequest);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(authenticationService.getAuthenticatedUser(), userId, updateUserRequest);
    }

    @DeleteMapping("/{userId}")
    public UserResponse deleteUser(@PathVariable String userId) {
        return userService.deleteUser(authenticationService.getAuthenticatedUser(), userId);
    }

    @PutMapping("/reset-password")
    public UserResponse resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        return userService.resetPassword(authenticationService.getAuthenticatedUser(), resetPasswordRequest);
    }

}
