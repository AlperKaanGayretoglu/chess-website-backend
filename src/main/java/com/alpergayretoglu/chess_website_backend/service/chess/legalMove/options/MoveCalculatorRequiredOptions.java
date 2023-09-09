package com.alpergayretoglu.chess_website_backend.service.chess.legalMove.options;

import com.alpergayretoglu.chess_website_backend.service.chess.ChessBoardPiecesObserver;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessMoveRegisterer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoveCalculatorRequiredOptions {
    private final ChessBoardPiecesObserver chessBoardPiecesObserver;

    private final ChessMoveRegisterer chessMoveRegisterer;
}
