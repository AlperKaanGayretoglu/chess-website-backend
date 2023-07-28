package com.alpergayretoglu.chess_website_backend.model.request.chat;

import lombok.Data;

import java.util.List;

@Data
public class CreateChatRequest {

    private List<String> userIds;

}
