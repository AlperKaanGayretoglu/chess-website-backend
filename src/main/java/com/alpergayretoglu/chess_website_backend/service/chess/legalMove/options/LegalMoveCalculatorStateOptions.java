package com.alpergayretoglu.chess_website_backend.service.chess.legalMove.options;

import com.alpergayretoglu.chess_website_backend.entity.chess.move.ChessMove;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@Builder
public class LegalMoveCalculatorStateOptions {

    private final boolean isCurrentPlayerInCheck;

    @Nullable
    private final ChessMove lastPlayedChessMove;

    @Builder.Default
    private final boolean shouldConsiderSafetyOfTheKing = true;
}
