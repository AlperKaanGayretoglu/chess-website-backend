package com.alpergayretoglu.chess_website_backend.model.response.chess;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
    
}
