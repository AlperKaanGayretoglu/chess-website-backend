package com.alpergayretoglu.chess_website_backend.service.chess.legalMove.options;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
@Builder
public class LegalMoveCalculatorOptions {

    @Delegate
    private final LegalMoveCalculatorStateOptions legalMoveCalculatorStateOptions;

    @Delegate
    private final MoveCalculatorRequiredOptions moveCalculatorRequiredOptions;

    private final ChessCoordinate forPieceAtCoordinate;

}
