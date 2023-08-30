package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.User;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessBoard;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGameState;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessMove;
import com.alpergayretoglu.chess_website_backend.exception.BusinessException;
import com.alpergayretoglu.chess_website_backend.exception.ErrorCode;
import com.alpergayretoglu.chess_website_backend.model.enums.UserRole;
import com.alpergayretoglu.chess_website_backend.model.request.chess.CreateChessGameRequest;
import com.alpergayretoglu.chess_website_backend.model.request.chess.PlayChessMoveRequest;
import com.alpergayretoglu.chess_website_backend.model.response.chess.ChessGameResponse;
import com.alpergayretoglu.chess_website_backend.model.response.chess.CreateChessGameResponse;
import com.alpergayretoglu.chess_website_backend.model.response.chess.PlayedChessMoveResponse;
import com.alpergayretoglu.chess_website_backend.repository.*;
import com.alpergayretoglu.chess_website_backend.service.mapper.ChessGameMapper;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Log
public class ChessGameService {

    private final SecurityService securityService;
    private final ChessGamePlayMoveService chessGamePlayMoveService;
    private final ChessGameLegalMoveService chessGameLegalMoveService;

    private final UserRepository userRepository;

    private final ChessGameRepository chessGameRepository;
    private final ChessGameStateRepository chessGameStateRepository;
    private final ChessBoardRepository chessBoardRepository;

    private final ChessMoveRepository chessMoveRepository;
    private final TriggeredPieceMoveRepository triggeredPieceMoveRepository;

    private final ChessGameMapper chessGameMapper;

    private final Random random = new Random();

    public ChessGameResponse getChessGame(Optional<User> authenticatedUser, String gameId) {
        securityService.assertUser(authenticatedUser);

        ChessGame chessGame = chessGameRepository.findById(gameId)
                .orElseThrow(() -> new BusinessException(ErrorCode.GAME_NOT_FOUND_WITH_ID(gameId)));

        return chessGameMapper.fromEntity(chessGame);
    }

    public CreateChessGameResponse createChessGame(Optional<User> authenticatedUserOptional, CreateChessGameRequest createChessGameRequest) {
        User user = securityService.assertUser(authenticatedUserOptional);

        String username = user.getUsername();
        String firstPlayerUsername = createChessGameRequest.getFirstPlayerUsername();
        String secondPlayerUsername = createChessGameRequest.getSecondPlayerUsername();

        if (!(username.equals(firstPlayerUsername) || username.equals(secondPlayerUsername) || user.getUserRole() == UserRole.ADMIN)) {
            throw new BusinessException(ErrorCode.USER_CANNOT_START_GAME_NOT_PART_OF());
        }

        if (firstPlayerUsername.equals(secondPlayerUsername)) {
            throw new BusinessException(ErrorCode.OPPONENTS_CANNOT_BE_SAME());
        }

        User firstPlayer = userRepository.findByUsername(firstPlayerUsername)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND_WITH_USERNAME(firstPlayerUsername)));
        User secondPlayer = userRepository.findByUsername(secondPlayerUsername)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND_WITH_USERNAME(secondPlayerUsername)));

        User playerWhite = random.nextBoolean() ? firstPlayer : secondPlayer;
        User playerBlack = (playerWhite == firstPlayer) ? secondPlayer : firstPlayer;

        ChessBoard chessBoard = ChessBoard.initializePieces(new ChessBoard());
        chessBoardRepository.save(chessBoard);

        ChessGameState chessGameState = ChessGameState.builder()
                .playerWhite(playerWhite)
                .playerBlack(playerBlack)
                .build();
        chessGameStateRepository.save(chessGameState);

        ChessGame chessGame = ChessGame.builder()
                .chessBoard(chessBoard)
                .chessGameState(chessGameState)
                .build();
        chessGameRepository.save(chessGame);

        chessGameLegalMoveService.calculateLegalMovesForCurrentPlayer(chessGame);

        return CreateChessGameResponse.fromEntity(chessGame);
    }

    public PlayedChessMoveResponse playMoveOnGame(PlayChessMoveRequest playChessMoveRequest, String gameId) {
        ChessGame chessGame = chessGameRepository.findById(gameId)
                .orElseThrow(() -> new BusinessException(ErrorCode.GAME_NOT_FOUND_WITH_ID(gameId)));

        String chessMoveId = playChessMoveRequest.getChessMoveId();
        ChessMove chessMove = chessMoveRepository.findByChessGameAndId(chessGame, chessMoveId).orElseThrow(
                () -> new BusinessException(ErrorCode.CHESS_MOVE_NOT_FOUND_WITH_ID(chessMoveId))
        );

        return chessGamePlayMoveService.playMove(chessGame, chessMove);
    }

}
