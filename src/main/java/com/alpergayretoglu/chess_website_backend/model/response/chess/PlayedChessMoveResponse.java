package com.alpergayretoglu.chess_website_backend.model.response.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessMove;
import com.alpergayretoglu.chess_website_backend.repository.ChessMoveRepository;
import com.alpergayretoglu.chess_website_backend.repository.TriggeredPieceMoveRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class PlayedChessMoveResponse {

    private final ChessMoveResponse playedChessMove;

    private final String currentPlayerUsername;
    private final List<ChessMoveResponse> legalMovesForCurrentPlayer;

    public static PlayedChessMoveResponse fromEntity(ChessMoveRepository chessMoveRepository, TriggeredPieceMoveRepository triggeredPieceMoveRepository, ChessGame game, ChessMove move) {
        return PlayedChessMoveResponse.builder()
                .playedChessMove(ChessMoveResponse.fromEntity(triggeredPieceMoveRepository, move))
                .currentPlayerUsername(game.getCurrentPlayer().getUsername())
                .legalMovesForCurrentPlayer(
                        chessMoveRepository.findAllByChessGame(game)
                                .stream()
                                .map(chessMove -> ChessMoveResponse.fromEntity(triggeredPieceMoveRepository, chessMove))
                                .collect(Collectors.toList())
                )
                .build();
    }

}
