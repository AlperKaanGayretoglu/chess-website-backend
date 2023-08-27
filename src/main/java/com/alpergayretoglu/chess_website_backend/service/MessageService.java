package com.alpergayretoglu.chess_website_backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageService {
    // TODO: fix
    // private final UserRepository userRepository;
//
    // private final ChessGameRepository chessGameRepository;
    // private final ChessMoveRepository chessMoveRepository;
//
    // public MessageResponse send(MessageRequest messageRequest, String chatId) {
    //     // TODO: Temporarily permits all but should be changed to authenticated (meaning apply the authentication service and authenticatedUser optional)
    //     // User user = securityService.assertSelf(authenticatedUserOptional, messageRequest.getFromUserId());
//
    //     User user = userRepository.findById(messageRequest.getFromUserId()).orElseThrow(
    //             () -> new BusinessException(ErrorCode.USER_NOT_FOUND())
    //     );
//
    //     ChessGame chessGame = chessGameRepository.findById(chatId).orElseThrow(
    //             () -> new BusinessException(ErrorCode.CHAT_NOT_FOUND())
    //     );
//
    //     if (!chessGame.getPlayers().contains(user)) {
    //         throw new BusinessException(ErrorCode.USER_NOT_IN_CHAT());
    //     }
//
    //     ChessMove chessMove = chessMoveRepository.save(ChessMove.builder()
    //             //.from(user)
    //             //.text(messageRequest.getText())
    //             //.chessGame(chessGame)
    //             .build());
//
    //     chessGameRepository.save(chessGame);
//
    //     return MessageResponse.fromEntity(chessMove);
    // }

}
