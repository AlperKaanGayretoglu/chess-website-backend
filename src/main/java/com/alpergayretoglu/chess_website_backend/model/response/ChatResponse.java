package com.alpergayretoglu.chess_website_backend.model.response;

import com.alpergayretoglu.chess_website_backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
public class ChatResponse {

    private String chatId;
    private List<String> userIds;

    public static ChatResponse fromEntity(com.alpergayretoglu.chess_website_backend.entity.Chat chat) {
        return ChatResponse.builder()
                .chatId(chat.getId())
                .userIds(chat.getUsers().stream().map(User::getId).collect(Collectors.toList()))
                .build();
    }

}
