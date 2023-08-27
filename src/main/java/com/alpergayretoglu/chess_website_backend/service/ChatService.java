package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.User;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.exception.BusinessException;
import com.alpergayretoglu.chess_website_backend.exception.ErrorCode;
import com.alpergayretoglu.chess_website_backend.model.enums.UserRole;
import com.alpergayretoglu.chess_website_backend.model.request.chat.CreateChatRequest;
import com.alpergayretoglu.chess_website_backend.model.response.ChatResponse;
import com.alpergayretoglu.chess_website_backend.model.response.MessageResponse;
import com.alpergayretoglu.chess_website_backend.repository.ChessGameRepository;
import com.alpergayretoglu.chess_website_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatService {
    private final SecurityService securityService;

    private final UserRepository userRepository;

    private final ChessGameRepository chessGameRepository;

    public ChatResponse getChat(Optional<User> authenticatedUser, String chatId) {
        User user = securityService.assertUser(authenticatedUser);

        ChessGame chessGame = chessGameRepository.findById(chatId).orElseThrow(
                () -> new BusinessException(ErrorCode.CHAT_NOT_FOUND(chatId)));

        return ChatResponse.fromEntity(chessGame);
    }

    public List<MessageResponse> getMessages(Optional<User> authenticatedUser, String chatId) {
        User user = securityService.assertUser(authenticatedUser);

        ChessGame chessGame = chessGameRepository.findById(chatId).orElseThrow(
                () -> new BusinessException(ErrorCode.CHAT_NOT_FOUND(chatId)));

        // TODO: Thinking from a chess game perspective, a spectator should be able to see the game but not play moves
        // if (!chat.getUsers().contains(user)) {
        //     throw new BusinessException(ErrorCode.GET_MESSAGES_WITHOUT_ACCESS());
        // }

        // return chessGame.getChessMoves().stream().map(MessageResponse::fromEntity).collect(Collectors.toList());
        return null;
    }

    public ChatResponse createChat(Optional<User> authenticatedUser, CreateChatRequest createMessageChatRequest) {
        User user = securityService.assertUser(authenticatedUser);

        List<User> users = new ArrayList<>();

        for (String username : createMessageChatRequest.getUsernames()) {
            users.add(userRepository.findByUsername(username).orElseThrow(
                    () -> new BusinessException(ErrorCode.USER_NOT_FOUND_WITH_USERNAME(username))));
        }

        if (user.getUserRole() != UserRole.ADMIN && !users.contains(user)) {
            throw new BusinessException(ErrorCode.CREATE_CHAT_WITHOUT_SELF());
        }

        // ChessGame chessGame = chessGameRepository.save(ChessGame.builder()
        //         .users(users)
        //         .build());

        // TODO: fix
        // return ChatResponse.fromEntity(chessGame);
        return null;
    }

}
