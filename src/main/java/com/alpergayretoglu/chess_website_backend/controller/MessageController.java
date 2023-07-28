package com.alpergayretoglu.chess_website_backend.controller;

import com.alpergayretoglu.chess_website_backend.model.request.message.MessageRequest;
import com.alpergayretoglu.chess_website_backend.model.response.MessageResponse;
import com.alpergayretoglu.chess_website_backend.service.AuthenticationService;
import com.alpergayretoglu.chess_website_backend.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/message")
@CrossOrigin
public class MessageController {

    private final AuthenticationService authenticationService;

    private final MessageService messageService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageResponse send(MessageRequest messageRequest) throws Exception {
        return messageService.send(authenticationService.getAuthenticatedUser(), messageRequest);
    }

}
