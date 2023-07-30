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
import com.alpergayretoglu.chess_website_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageService {

    private final UserRepository userRepository;

    private final MessageChatRepository messageChatRepository;
    private final MessageRepository messageRepository;

    public MessageResponse send(MessageRequest messageRequest, String chatId) {
        // TODO: Temporarily permits all but should be changed to authenticated (meaning apply the authentication service and authenticatedUser optional)
        // User user = securityService.assertSelf(authenticatedUserOptional, messageRequest.getFromUserId());

        User user = userRepository.findById(messageRequest.getFromUserId()).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_NOT_FOUND())
        );

        Chat chat = messageChatRepository.findById(chatId).orElseThrow(
                () -> new BusinessException(ErrorCode.CHAT_NOT_FOUND())
        );

        if (!chat.getUsers().contains(user)) {
            throw new BusinessException(ErrorCode.USER_NOT_IN_CHAT());
        }

        Message message = messageRepository.save(Message.builder()
                .from(user)
                .text(messageRequest.getText())
                .chat(chat)
                .build());

        messageChatRepository.save(chat);

        return MessageResponse.fromEntity(message);
    }

}
