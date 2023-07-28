package com.alpergayretoglu.chess_website_backend.controller;

import com.alpergayretoglu.chess_website_backend.model.request.chat.CreateChatRequest;
import com.alpergayretoglu.chess_website_backend.model.response.ChatResponse;
import com.alpergayretoglu.chess_website_backend.service.AuthenticationService;
import com.alpergayretoglu.chess_website_backend.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/chat")
@CrossOrigin
public class ChatController {

    private final AuthenticationService authenticationService;
    private final ChatService chatService;

    @PostMapping
    public ChatResponse createChat(CreateChatRequest createMessageChatRequest) {
        return chatService.createChat(authenticationService.getAuthenticatedUser(), createMessageChatRequest);
    }

}
