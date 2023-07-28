package com.alpergayretoglu.chess_website_backend.model.response;

import com.alpergayretoglu.chess_website_backend.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageResponse {

    private String fromUserId;
    private String text;
    private String time;

    public static MessageResponse fromEntity(Message message) {
        return MessageResponse.builder()
                .fromUserId(message.getFrom().getId())
                .text(message.getText())
                .time(message.getCreated().toString())
                .build();
    }

}
