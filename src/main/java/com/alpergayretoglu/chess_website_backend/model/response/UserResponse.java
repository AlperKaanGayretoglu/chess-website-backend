package com.alpergayretoglu.chess_website_backend.model.response;

import com.alpergayretoglu.chess_website_backend.entity.User;
import com.alpergayretoglu.chess_website_backend.model.enums.UserRole;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse extends BaseResponse {

    private String username;
    private String email;
    private UserRole userRole;

    public static UserResponse fromEntity(User user) {
        UserResponse response = UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .build();

        return setCommonValuesFromEntity(response, user);
    }

}
