package com.alpergayretoglu.chess_website_backend.controller;

import com.alpergayretoglu.chess_website_backend.entity.User;
import com.alpergayretoglu.chess_website_backend.exception.ErrorCode;
import com.alpergayretoglu.chess_website_backend.exception.ErrorDTO;
import com.alpergayretoglu.chess_website_backend.model.request.auth.LoginRequest;
import com.alpergayretoglu.chess_website_backend.model.request.auth.RegisterRequest;
import com.alpergayretoglu.chess_website_backend.model.response.LoginResponse;
import com.alpergayretoglu.chess_website_backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationControllerIT extends BaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void given_RegisterRequest_when_register_then_status_code_is_200_and_user_is_registered() {
        // Given
        RegisterRequest registerRequest = generateRegisterRequest();

        // When
        final ResponseEntity<Void> response = sendRequest(
                "/auth/register",
                HttpMethod.POST,
                registerRequest,
                new ParameterizedTypeReference<Void>() {
                }
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        User user = userRepository.findByEmail(registerRequest.getEmail()).orElse(null);
        assertNotNull(user);

        assert_RegisterRequest_matches_UserEntity(registerRequest, user);
    }

    @Test
    public void given_RegisterRequest_and_already_existent_email_when_register_then_ACCOUNT_ALREADY_EXISTS() {
        // Given
        RegisterRequest registerRequest = generateRegisterRequest();
        registerRequest.setEmail("test_guest_exists@mail.com");

        // When
        final ResponseEntity<ErrorDTO> response = sendRequest(
                "/auth/register",
                HttpMethod.POST,
                registerRequest,
                new ParameterizedTypeReference<ErrorDTO>() {
                }
        );

        // Then
        ErrorDTO errorDTO = response.getBody();
        assertNotNull(errorDTO);

        assertEquals(ErrorCode.ACCOUNT_ALREADY_EXISTS, errorDTO.getErrorCode());
    }

    @Test
    public void given_LoginRequest_when_login_then_status_code_is_200_and_token_is_returned() {
        // Given
        LoginRequest loginRequest = generateLoginRequest();

        // When
        final ResponseEntity<LoginResponse> response = sendRequest(
                "/auth/login",
                HttpMethod.POST,
                loginRequest,
                new ParameterizedTypeReference<LoginResponse>() {
                }
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        LoginResponse loginResponse = response.getBody();
        assertNotNull(loginResponse);

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);
        assertNotNull(user);

        assert_LoginRequest_matches_UserEntity(loginRequest, user);
        assert_UserEntity_matches_LoginResponse(user, loginResponse);
    }

    @Test
    public void given_LoginRequest_and_nonexistent_email_when_login_then_USER_NOT_FOUND() {
        // Given
        LoginRequest loginRequest = generateLoginRequest();
        loginRequest.setEmail("nonexistent@mail.com");

        // When
        final ResponseEntity<ErrorDTO> response = sendRequest(
                "/auth/login",
                HttpMethod.POST,
                loginRequest,
                new ParameterizedTypeReference<ErrorDTO>() {
                }
        );

        // Then
        ErrorDTO errorDTO = response.getBody();
        assertNotNull(errorDTO);

        assertEquals(ErrorCode.USER_NOT_FOUND, errorDTO.getErrorCode());
    }

    @Test
    public void given_LoginRequest_and_wrong_password_when_login_then_PASSWORD_MISMATCH() {
        // Given
        LoginRequest loginRequest = generateLoginRequest();
        loginRequest.setPassword("wrong_password");

        // When
        final ResponseEntity<ErrorDTO> response = sendRequest(
                "/auth/login",
                HttpMethod.POST,
                loginRequest,
                new ParameterizedTypeReference<ErrorDTO>() {
                }
        );

        // Then
        ErrorDTO errorDTO = response.getBody();
        assertNotNull(errorDTO);

        assertEquals(ErrorCode.PASSWORD_MISMATCH, errorDTO.getErrorCode());
    }


    // ---------------------------------------- GENERATE REQUESTS ----------------------------------------
    private RegisterRequest generateRegisterRequest() {
        return RegisterRequest.builder()
                .username("new_registered_user_username")
                .email("new_registered_user@mail.com")
                .password("12345")
                .build();
    }

    private LoginRequest generateLoginRequest() {
        return LoginRequest.builder()
                .email("test_guest_exists@mail.com")
                .password("12345")
                .build();
    }

    // ---------------------------------------- ASSERTIONS ----------------------------------------
    private void assert_RegisterRequest_matches_UserEntity(RegisterRequest registerRequest, User user) {
        assertEquals(registerRequest.getUsername(), user.getUsername());
        assertEquals(registerRequest.getEmail(), user.getEmail());
        assertTrue(passwordEncoder.matches(registerRequest.getPassword(), user.getPassword()));
    }

    private void assert_LoginRequest_matches_UserEntity(LoginRequest loginRequest, User user) {
        assertEquals(loginRequest.getEmail(), user.getEmail());
        assertTrue(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()));
    }

    private void assert_UserEntity_matches_LoginResponse(User user, LoginResponse loginResponse) {
        assertEquals(user.getId(), loginResponse.getId());
        assertEquals(user.getUserRole(), loginResponse.getUserRole());
    }

}
