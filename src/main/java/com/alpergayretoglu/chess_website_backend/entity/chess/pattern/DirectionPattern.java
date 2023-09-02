package com.alpergayretoglu.chess_website_backend.entity.chess.pattern;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import lombok.AllArgsConstructor;

import java.util.Iterator;

@AllArgsConstructor
public class DirectionPattern {
    @AllArgsConstructor
    public static class DirectionChange {
        private final int rowChange;
        private final int columnChange;
    }

    private final DirectionChange directionChange;

    public static final DirectionPattern RIGHT_FORWARD_DIAGONAL = new DirectionPattern(new DirectionChange(-1, 1));
    public static final DirectionPattern RIGHT_BACKWARD_DIAGONAL = new DirectionPattern(new DirectionChange(1, 1));
    public static final DirectionPattern LEFT_FORWARD_DIAGONAL = new DirectionPattern(new DirectionChange(-1, -1));
    public static final DirectionPattern LEFT_BACKWARD_DIAGONAL = new DirectionPattern(new DirectionChange(1, -1));

    public static final DirectionPattern RIGHT = new DirectionPattern(new DirectionChange(0, 1));
    public static final DirectionPattern LEFT = new DirectionPattern(new DirectionChange(0, -1));
    public static final DirectionPattern FORWARD = new DirectionPattern(new DirectionChange(-1, 0));
    public static final DirectionPattern BACKWARD = new DirectionPattern(new DirectionChange(1, 0));

    public static final DirectionPattern FORWARD_RIGHT_L_SHAPE = new DirectionPattern(new DirectionChange(-2, 1));
    public static final DirectionPattern FORWARD_LEFT_L_SHAPE = new DirectionPattern(new DirectionChange(-2, -1));
    public static final DirectionPattern BACKWARD_RIGHT_L_SHAPE = new DirectionPattern(new DirectionChange(2, 1));
    public static final DirectionPattern BACKWARD_LEFT_L_SHAPE = new DirectionPattern(new DirectionChange(2, -1));
    public static final DirectionPattern RIGHT_FORWARD_L_SHAPE = new DirectionPattern(new DirectionChange(-1, 2));
    public static final DirectionPattern RIGHT_BACKWARD_L_SHAPE = new DirectionPattern(new DirectionChange(1, 2));
    public static final DirectionPattern LEFT_FORWARD_L_SHAPE = new DirectionPattern(new DirectionChange(-1, -2));
    public static final DirectionPattern LEFT_BACKWARD_L_SHAPE = new DirectionPattern(new DirectionChange(1, -2));

    /**
     * NOTE: Start coordinate will never be iterated over.
     */
    public Iterable<ChessCoordinate> getIterable(ChessCoordinate startCoordinate, int limit) {
        return () -> new Iterator<ChessCoordinate>() {
            private ChessCoordinate currentCoordinate = startCoordinate;
            private int count = 0;

            @Override
            public boolean hasNext() {
                if (count >= limit) {
                    return false;
                }

                int newRow = currentCoordinate.getRow() + directionChange.rowChange;
                int newColumn = currentCoordinate.getColumn() + directionChange.columnChange;
                return ChessCoordinate.isCoordinateValid(newRow, newColumn);
            }

            @Override
            public ChessCoordinate next() {
                if (!hasNext()) {
                    throw new IllegalStateException("No more coordinates");
                }

                count++;

                return currentCoordinate = new ChessCoordinate(
                        currentCoordinate.getRow() + directionChange.rowChange,
                        currentCoordinate.getColumn() + directionChange.columnChange
                );
            }
        };
    }
}