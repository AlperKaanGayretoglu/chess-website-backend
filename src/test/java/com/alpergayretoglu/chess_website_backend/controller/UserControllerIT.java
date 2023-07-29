package com.alpergayretoglu.chess_website_backend.controller;

import com.alpergayretoglu.chess_website_backend.entity.BaseEntity;
import com.alpergayretoglu.chess_website_backend.entity.User;
import com.alpergayretoglu.chess_website_backend.exception.ErrorCode;
import com.alpergayretoglu.chess_website_backend.exception.ErrorDTO;
import com.alpergayretoglu.chess_website_backend.model.request.auth.ResetPasswordRequest;
import com.alpergayretoglu.chess_website_backend.model.request.user.CreateUserRequest;
import com.alpergayretoglu.chess_website_backend.model.request.user.UpdateUserRequest;
import com.alpergayretoglu.chess_website_backend.model.response.UserResponse;
import com.alpergayretoglu.chess_website_backend.repository.UserRepository;
import com.alpergayretoglu.chess_website_backend.util.RestResponsePage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerIT extends BaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void when_listUsers_then_status_code_is_200_and_users_are_listed() {
        // When
        final ResponseEntity<RestResponsePage<UserResponse>> response = sendRequest(
                "/user?page=0&size=10",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<RestResponsePage<UserResponse>>() {
                }
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RestResponsePage<UserResponse> responsePage = response.getBody();
        assertNotNull(responsePage);

        assertEquals(
                userRepository.findAll().stream().map(BaseEntity::getId).collect(Collectors.toSet()),
                responsePage.getContent().stream().map(UserResponse::getId).collect(Collectors.toSet())
        );

    }

    @Test
    public void when_getUser_then_status_code_is_200_and_user_is_returned() {
        // Given
        String userId = "test-guest-exists";

        // When
        final ResponseEntity<UserResponse> response = sendRequest(
                "/user/" + userId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<UserResponse>() {
                }
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserResponse userResponse = response.getBody();
        assertNotNull(userResponse);

        User user = userRepository.findById(userId).orElse(null);
        assertNotNull(user);

        assert_UserEntity_matches_UserResponse(user, userResponse);
    }

    @Test
    public void given_nonexistent_userId_when_getUser_then_USER_NOT_FOUND() {
        // Given
        String userId = "nonexistent-user-id";

        // When
        final ResponseEntity<ErrorDTO> response = sendRequest(
                "/user/" + userId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ErrorDTO>() {
                }
        );

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorDTO errorDTO = response.getBody();
        assertNotNull(errorDTO);

        assertEquals(ErrorCode.USER_NOT_FOUND, errorDTO.getErrorCode());
    }

    @Test
    public void given_CreateUserRequest_when_createUser_then_status_code_is_200_and_user_is_created() {
        // Given
        CreateUserRequest createUserRequest = generateCreateUserRequest();

        // When
        final ResponseEntity<UserResponse> response = sendRequest(
                "/user",
                HttpMethod.POST,
                createUserRequest,
                new ParameterizedTypeReference<UserResponse>() {
                }
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserResponse userResponse = response.getBody();
        assertNotNull(userResponse);

        User newUser = userRepository.findById(userResponse.getId()).orElse(null);
        assertNotNull(newUser);

        assert_CreateUserRequest_matches_UserEntity(createUserRequest, newUser);
        assert_UserEntity_matches_UserResponse(newUser, response.getBody());
    }

    @Test
    public void given_CreateUserRequest_and_already_exists_email_when_createUser_then_ACCOUNT_ALREADY_EXISTS() {
        // Given
        CreateUserRequest createUserRequest = generateCreateUserRequest();
        createUserRequest.setEmail("test_guest_exists@mail.com");

        // When
        final ResponseEntity<ErrorDTO> response = sendRequest(
                "/user",
                HttpMethod.POST,
                createUserRequest,
                new ParameterizedTypeReference<ErrorDTO>() {
                }
        );

        // Then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

        ErrorDTO errorDTO = response.getBody();
        assertNotNull(errorDTO);

        assertEquals(ErrorCode.ACCOUNT_ALREADY_EXISTS, response.getBody().getErrorCode());
    }

    @Test
    public void given_UserUpdateRequest_when_updateUser_then_status_code_is_200_and_user_is_updated() {
        // Given
        String userId = "test-guest-exists";

        UpdateUserRequest updateUserRequest = generateUpdateUserRequest();

        // When
        final ResponseEntity<UserResponse> response = sendRequest(
                "/user/" + userId,
                HttpMethod.PUT,
                updateUserRequest,
                new ParameterizedTypeReference<UserResponse>() {
                }
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        User newUser = userRepository.findById(userId).orElse(null);
        assertNotNull(newUser);

        UserResponse userResponse = response.getBody();
        assertNotNull(userResponse);

        assert_UpdateUserRequest_matches_UserEntity(updateUserRequest, newUser);
        assert_UserEntity_matches_UserResponse(newUser, userResponse);
    }

    @Test
    public void given_UserUpdateRequest_and_nonexistent_userId_when_updateUser_then_USER_NOT_FOUND() {
        // Given
        String userId = "nonexistent-user-id";

        UpdateUserRequest updateUserRequest = generateUpdateUserRequest();

        // When
        final ResponseEntity<ErrorDTO> response = sendRequest(
                "/user/" + userId,
                HttpMethod.PUT,
                updateUserRequest,
                new ParameterizedTypeReference<ErrorDTO>() {
                }
        );

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorDTO errorDTO = response.getBody();
        assertNotNull(errorDTO);

        assertEquals(ErrorCode.USER_NOT_FOUND, errorDTO.getErrorCode());
    }

    @Test
    public void when_deleteUser_then_status_code_is_200_and_user_is_deleted() {
        // Given
        String userId = "test-guest-to-be-deleted";

        // When
        User oldUser = userRepository.findById(userId).orElse(null);
        assertNotNull(oldUser);

        final ResponseEntity<UserResponse> response = sendRequest(
                "/user/" + userId,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<UserResponse>() {
                }
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserResponse userResponse = response.getBody();
        assertNotNull(userResponse);

        assertFalse(userRepository.existsById(userId));

        assert_UserEntity_matches_UserResponse(oldUser, userResponse);
    }

    @Test
    public void given_nonexistent_userId_when_deleteUser_then_USER_NOT_FOUND() {
        // Given
        String userId = "nonexistent-user-id";

        // When
        final ResponseEntity<ErrorDTO> response = sendRequest(
                "/user/" + userId,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<ErrorDTO>() {
                }
        );

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorDTO errorDTO = response.getBody();
        assertNotNull(errorDTO);

        assertEquals(ErrorCode.USER_NOT_FOUND, errorDTO.getErrorCode());
    }

    @Test
    public void given_ResetPasswordRequest_when_resetPassword_then_status_code_is_200_and_password_is_reset() {
        // Given
        String userId = "test-guest-exists";

        ResetPasswordRequest resetPasswordRequest = generateResetPasswordRequest();

        // When
        final ResponseEntity<UserResponse> response = sendRequestAs(userId,
                "/user/reset-password",
                HttpMethod.PUT,
                resetPasswordRequest,
                new ParameterizedTypeReference<UserResponse>() {
                }
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserResponse userResponse = response.getBody();
        assertNotNull(userResponse);

        User newUser = userRepository.findById(userId).orElse(null);
        assertNotNull(newUser);

        assertTrue(passwordEncoder.matches(resetPasswordRequest.getNewPassword(), newUser.getPassword()));

        assert_UserEntity_matches_UserResponse(newUser, userResponse);
    }

    @Test
    public void given_ResetPasswordRequest_and_wrong_password_when_resetPassword_then_PASSWORD_MISMATCH() {
        // Given
        String userId = "test-guest-exists";

        ResetPasswordRequest resetPasswordRequest = generateResetPasswordRequest();
        resetPasswordRequest.setOldPassword("wrong-password");

        // When
        final ResponseEntity<ErrorDTO> response = sendRequestAs(userId,
                "/user/reset-password",
                HttpMethod.PUT,
                resetPasswordRequest,
                new ParameterizedTypeReference<ErrorDTO>() {
                }
        );

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        ErrorDTO errorDTO = response.getBody();
        assertNotNull(errorDTO);

        assertEquals(ErrorCode.PASSWORD_MISMATCH, errorDTO.getErrorCode());
    }


    // ---------------------------------------- GENERATE REQUESTS ----------------------------------------
    private CreateUserRequest generateCreateUserRequest() {
        return CreateUserRequest.builder()
                .username("Username Guest")
                .email("guest_created@mail.com")
                .password("12345")
                .build();
    }

    private UpdateUserRequest generateUpdateUserRequest() {
        return UpdateUserRequest.builder()
                .username("Username Guest Updated")
                .email("guest_email_updated")
                .build();
    }

    private ResetPasswordRequest generateResetPasswordRequest() {
        return ResetPasswordRequest.builder()
                .oldPassword("12345")
                .newPassword("12345_NEW")
                .build();
    }

    // ---------------------------------------- ASSERTIONS ----------------------------------------
    private void assert_CreateUserRequest_matches_UserEntity(CreateUserRequest createUserRequest, User user) {
        assertEquals(createUserRequest.getUsername(), user.getUsername());
        assertEquals(createUserRequest.getEmail(), user.getEmail());
        assertTrue(passwordEncoder.matches(createUserRequest.getPassword(), user.getPassword()));
    }

    private void assert_UpdateUserRequest_matches_UserEntity(UpdateUserRequest updateUserRequest, User user) {
        assertEquals(updateUserRequest.getUsername(), user.getUsername());
    }

    private void assert_UserEntity_matches_UserResponse(User user, UserResponse userResponse) {
        assertEquals(user.getId(), userResponse.getId());
        assertEquals(user.getUsername(), userResponse.getUsername());
        assertEquals(user.getEmail(), userResponse.getEmail());
        assertEquals(user.getUserRole(), userResponse.getUserRole());
    }

}
