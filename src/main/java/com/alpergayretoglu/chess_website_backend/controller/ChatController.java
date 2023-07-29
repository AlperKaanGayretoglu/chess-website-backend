package com.alpergayretoglu.chess_website_backend.controller;

import com.alpergayretoglu.chess_website_backend.model.request.chat.CreateChatRequest;
import com.alpergayretoglu.chess_website_backend.model.response.ChatResponse;
import com.alpergayretoglu.chess_website_backend.model.response.MessageResponse;
import com.alpergayretoglu.chess_website_backend.service.AuthenticationService;
import com.alpergayretoglu.chess_website_backend.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/chatting")
@CrossOrigin
public class ChatController {

    private final AuthenticationService authenticationService;
    private final ChatService chatService;

    @GetMapping("/{chatId}/messages")
    public List<MessageResponse> getMessages(@PathVariable String chatId) {
        return chatService.getMessages(authenticationService.getAuthenticatedUser(), chatId);
    }

    @PostMapping
    public ChatResponse createChat(@Valid @RequestBody CreateChatRequest createMessageChatRequest) {
        return chatService.createChat(authenticationService.getAuthenticatedUser(), createMessageChatRequest);
    }

}
