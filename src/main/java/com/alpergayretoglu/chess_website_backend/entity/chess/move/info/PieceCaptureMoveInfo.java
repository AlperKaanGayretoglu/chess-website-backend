package com.alpergayretoglu.chess_website_backend.entity.chess.move.info;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import lombok.Data;

@Data
public class PieceCaptureMoveInfo {
    private final ChessCoordinate pieceCaptureFrom;
}
