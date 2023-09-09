package com.alpergayretoglu.chess_website_backend.model.response.chess;

import com.alpergayretoglu.chess_website_backend.model.enums.ChessGameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PlayedChessMoveResponse {

    private final ChessMoveResponse playedChessMove;

    private final String currentPlayerUsername;
    private final String whitePlayerUsername;
    private final String blackPlayerUsername;

    private final List<ChessMoveResponse> legalMovesForCurrentPlayer;

    private final boolean isCurrentPlayerInCheck;

    private final boolean hasGameEnded;
    private final ChessGameStatus chessGameStatus;

}
