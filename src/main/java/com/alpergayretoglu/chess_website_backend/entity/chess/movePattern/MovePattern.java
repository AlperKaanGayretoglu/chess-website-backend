package com.alpergayretoglu.chess_website_backend.entity.chess.movePattern;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import lombok.Data;

@Data
public class MovePattern {
    private final int limit;
    private final int[][] directionChanges;

    public MovePattern(int limit, int[][] directionChanges) {
        this.limit = limit;
        this.directionChanges = directionChanges;
    }

    public MovePattern(int[][] directionChanges) {
        this.limit = Integer.MAX_VALUE;
        this.directionChanges = directionChanges;
    }

    public MovePatternIterator getIteratorStartingAt(ChessCoordinate startCoordinate) {
        return new MovePatternIterator(this, startCoordinate);
    }

}
