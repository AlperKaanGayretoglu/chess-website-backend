package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.Chat;
import com.alpergayretoglu.chess_website_backend.entity.Message;
import com.alpergayretoglu.chess_website_backend.entity.User;
import com.alpergayretoglu.chess_website_backend.exception.BusinessException;
import com.alpergayretoglu.chess_website_backend.exception.ErrorCode;
import com.alpergayretoglu.chess_website_backend.model.request.message.MessageRequest;
import com.alpergayretoglu.chess_website_backend.model.response.MessageResponse;
import com.alpergayretoglu.chess_website_backend.repository.MessageChatRepository;
import com.alpergayretoglu.chess_website_backend.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {

    private final SecurityService securityService;

    private final MessageChatRepository messageChatRepository;
    private final MessageRepository messageRepository;

    public MessageResponse send(Optional<User> authenticatedUserOptional, MessageRequest messageRequest) {
        User user = securityService.assertSelf(authenticatedUserOptional, messageRequest.getFromUserId());

        Chat chat = messageChatRepository.findById(messageRequest.getChatId()).orElseThrow(
                () -> new BusinessException(ErrorCode.CHAT_NOT_FOUND)
        );

        Message message = messageRepository.save(Message.builder()
                .from(user)
                .text(messageRequest.getText())
                .build());

        chat.addMessage(message);
        messageChatRepository.save(chat);

        return MessageResponse.fromEntity(message);
    }

}
