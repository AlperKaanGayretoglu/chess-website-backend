package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.Chat;
import com.alpergayretoglu.chess_website_backend.entity.User;
import com.alpergayretoglu.chess_website_backend.exception.BusinessException;
import com.alpergayretoglu.chess_website_backend.exception.ErrorCode;
import com.alpergayretoglu.chess_website_backend.model.enums.UserRole;
import com.alpergayretoglu.chess_website_backend.model.request.chat.CreateChatRequest;
import com.alpergayretoglu.chess_website_backend.model.response.ChatResponse;
import com.alpergayretoglu.chess_website_backend.model.response.MessageResponse;
import com.alpergayretoglu.chess_website_backend.repository.MessageChatRepository;
import com.alpergayretoglu.chess_website_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatService {
    private final SecurityService securityService;

    private final UserRepository userRepository;

    private final MessageChatRepository messageChatRepository;

    public List<MessageResponse> getMessages(Optional<User> authenticatedUser, String chatId) {
        User user = securityService.assertUser(authenticatedUser);

        Chat chat = messageChatRepository.findById(chatId).orElseThrow(
                () -> new BusinessException(new ErrorCode(HttpStatus.NOT_FOUND, "Chat not found with id: " + chatId)));

        // TODO: Thinking from a chess game perspective, a spectator should be able to see the game but not play moves
        // if (!chat.getUsers().contains(user)) {
        //     throw new BusinessException(ErrorCode.GET_MESSAGES_WITHOUT_ACCESS);
        // }

        return chat.getMessages().stream().map(MessageResponse::fromEntity).collect(Collectors.toList());
    }

    public ChatResponse createChat(Optional<User> authenticatedUser, CreateChatRequest createMessageChatRequest) {
        User user = securityService.assertUser(authenticatedUser);

        List<User> users = new ArrayList<>();

        for (String userId : createMessageChatRequest.getUserIds()) {
            users.add(userRepository.findById(userId).orElseThrow(
                    () -> new BusinessException(new ErrorCode(HttpStatus.NOT_FOUND, "User not found with id: " + userId))));
        }

        if (user.getUserRole() != UserRole.ADMIN && !users.contains(user)) {
            throw new BusinessException(ErrorCode.CREATE_CHAT_WITHOUT_SELF);
        }

        Chat chat = messageChatRepository.save(Chat.builder()
                .users(users)
                .build());

        return ChatResponse.fromEntity(chat);
    }

}
