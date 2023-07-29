package com.alpergayretoglu.chess_website_backend.model.request.message;

import lombok.Data;

@Data
public class MessageRequest {

    private String fromUserId;
    private String text;

}
