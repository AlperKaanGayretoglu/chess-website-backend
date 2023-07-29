package com.alpergayretoglu.chess_website_backend.model.response;

import com.alpergayretoglu.chess_website_backend.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageResponse {

    private String username;
    private String text;
    private String time;

    public static MessageResponse fromEntity(Message message) {
        return MessageResponse.builder()
                .username(message.getFrom().getUsername())
                .text(message.getText())
                .time(message.getCreated().toString())
                .build();
    }

}
