package com.alpergayretoglu.chess_website_backend.model.enums;

import com.alpergayretoglu.chess_website_backend.entity.chess.movePattern.MovePattern;
import lombok.Getter;

@Getter
public enum ChessPieceType {
    PAWN(1, new MovePattern(1, new int[][]{
            {0, 1}
    })),
    KNIGHT(3, new MovePattern(1, new int[][]{
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}
    })),
    BISHOP(3, new MovePattern(new int[][]{
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    })),
    ROOK(5, new MovePattern(new int[][]{
            {1, 0}, {0, 1}, {-1, 0}, {0, -1}
    })),
    QUEEN(9, new MovePattern(new int[][]{
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {1, 0}, {0, 1}, {-1, 0}, {0, -1}
    })),
    KING(100, new MovePattern(1, new int[][]{
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {1, 0}, {0, 1}, {-1, 0}, {0, -1}
    }));

    private final int point;

    private final MovePattern movePattern;

    ChessPieceType(int point, MovePattern movePattern) {
        this.point = point;
        this.movePattern = movePattern;
    }


}
