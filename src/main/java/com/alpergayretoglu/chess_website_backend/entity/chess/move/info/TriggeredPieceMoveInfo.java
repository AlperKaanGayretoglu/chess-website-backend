package com.alpergayretoglu.chess_website_backend.entity.chess.move.info;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import lombok.Data;

@Data
public class TriggeredPieceMoveInfo {
    private final ChessCoordinate triggeredPieceFrom;
    private final ChessCoordinate triggeredPieceTo;
}
