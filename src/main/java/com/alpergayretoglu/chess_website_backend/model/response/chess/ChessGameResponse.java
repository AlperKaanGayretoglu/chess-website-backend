package com.alpergayretoglu.chess_website_backend.model.response.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
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
public class ChessGameResponse {

    private final String gameId;
    private final ChessBoardResponse board;

    private final String playerWhiteUsername;
    private final String playerBlackUsername;

    private final String currentPlayerUsername;

    private final List<ChessMoveResponse> legalMovesForCurrentPlayer;

    public static ChessGameResponse fromEntity(ChessMoveRepository chessMoveRepository, TriggeredPieceMoveRepository triggeredPieceMoveRepository, ChessGame game) {
        return ChessGameResponse.builder()
                .gameId(game.getId())
                .board(ChessBoardResponse.fromEntity(game.getChessBoard()))
                .playerWhiteUsername(game.getPlayerWhite().getUsername())
                .playerBlackUsername(game.getPlayerBlack().getUsername())
                .currentPlayerUsername(game.getCurrentPlayer().getUsername())
                .legalMovesForCurrentPlayer(
                        chessMoveRepository.findAllByChessGame(game)
                                .stream()
                                .map(chessMove -> ChessMoveResponse.fromEntity(triggeredPieceMoveRepository, chessMove))
                                .collect(Collectors.toList())
                ).build();

    }
}
