package com.alpergayretoglu.chess_website_backend.controller;

import com.alpergayretoglu.chess_website_backend.model.request.message.MessageRequest;
import com.alpergayretoglu.chess_website_backend.model.response.MessageResponse;
import com.alpergayretoglu.chess_website_backend.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/secured/chatting")
    @SendTo("/secured/chat_messages")
    public MessageResponse send(MessageRequest messageRequest) {
        return messageService.send(messageRequest);
    }

}
