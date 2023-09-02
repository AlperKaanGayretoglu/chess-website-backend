package com.alpergayretoglu.chess_website_backend.entity.chess.move.info;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import lombok.Data;

@Data
public class PlayedPieceMoveInfo {
    private final ChessCoordinate playedPieceFrom;
    private final ChessCoordinate playedPieceTo;
}
