package com.alpergayretoglu.chess_website_backend.model.response.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateChessGameResponse {

    private final String gameId;

    private final String playerWhiteUsername;
    private final String playerBlackUsername;

    public static CreateChessGameResponse fromEntity(ChessGame game) {
        return CreateChessGameResponse.builder()
                .gameId(game.getId())
                .playerWhiteUsername(game.getPlayerWhite().getUsername())
                .playerBlackUsername(game.getPlayerBlack().getUsername())
                .build();

    }

}