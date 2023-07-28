package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.Chat;
import com.alpergayretoglu.chess_website_backend.entity.User;
import com.alpergayretoglu.chess_website_backend.exception.BusinessException;
import com.alpergayretoglu.chess_website_backend.exception.ErrorCode;
import com.alpergayretoglu.chess_website_backend.model.enums.UserRole;
import com.alpergayretoglu.chess_website_backend.model.request.chat.CreateChatRequest;
import com.alpergayretoglu.chess_website_backend.model.response.ChatResponse;
import com.alpergayretoglu.chess_website_backend.repository.MessageChatRepository;
import com.alpergayretoglu.chess_website_backend.repository.MessageRepository;
import com.alpergayretoglu.chess_website_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatService {
    private final SecurityService securityService;

    private final UserRepository userRepository;
    
    private final MessageChatRepository messageChatRepository;
    private final MessageRepository messageRepository;

    public ChatResponse createChat(Optional<User> authenticatedUser, CreateChatRequest createMessageChatRequest) {
        User user = securityService.assertUser(authenticatedUser);

        List<User> users = userRepository.findAllById(createMessageChatRequest.getUserIds());

        if (user.getUserRole() != UserRole.ADMIN && !users.contains(user)) {
            throw new BusinessException(ErrorCode.USER_IS_NOT_ADMIN_OR_SELF);
        }

        Chat chat = messageChatRepository.save(Chat.builder()
                .users(users)
                .build());

        return ChatResponse.fromEntity(chat);
    }

}
