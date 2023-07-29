package com.alpergayretoglu.chess_website_backend.controller;

import com.alpergayretoglu.chess_website_backend.model.request.message.MessageRequest;
import com.alpergayretoglu.chess_website_backend.model.response.MessageResponse;
import com.alpergayretoglu.chess_website_backend.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class MessageController {

    private final MessageService messageService;

    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/secured/chatting/{chatId}")
    public void send(MessageRequest messageRequest, @DestinationVariable String chatId) {
        MessageResponse message = messageService.send(messageRequest, chatId);
        simpMessagingTemplate.convertAndSend("/secured/chat_messages/" + chatId, message);
    }
}
