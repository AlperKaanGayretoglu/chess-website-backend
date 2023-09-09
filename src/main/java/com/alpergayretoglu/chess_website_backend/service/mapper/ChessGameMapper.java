package com.alpergayretoglu.chess_website_backend.service.mapper;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.model.response.chess.ChessBoardResponse;
import com.alpergayretoglu.chess_website_backend.model.response.chess.ChessGameResponse;
import com.alpergayretoglu.chess_website_backend.repository.ChessMoveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChessGameMapper {
    private final ChessMoveRepository chessMoveRepository;

    private final ChessMoveMapper chessMoveMapper;

    public ChessGameResponse fromEntity(ChessGame game) {
        return ChessGameResponse.builder()
                .gameId(game.getId())
                .board(ChessBoardResponse.fromEntity(game.getChessBoard()))
                .playerWhiteUsername(game.getPlayerWhite().getUsername())
                .playerBlackUsername(game.getPlayerBlack().getUsername())
                .currentPlayerUsername(game.getCurrentPlayer().getUsername())
                .legalMovesForCurrentPlayer(
                        chessMoveRepository.findAllByChessGame(game)
                                .stream()
                                .map(chessMoveMapper::fromEntity)
                                .collect(Collectors.toList())
                )
                .isWhiteInCheck(game.isWhiteInCheck())
                .isBlackInCheck(game.isBlackInCheck())
                .hasGameEnded(game.hasGameEnded())
                .chessGameStatus(game.getChessGameState().getChessGameStatus())
                .build();

    }
}
