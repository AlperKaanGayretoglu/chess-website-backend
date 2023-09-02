package com.alpergayretoglu.chess_website_backend.entity.chess.pattern;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class MovePattern {

    private final List<DirectionPattern> directionPatterns;

    public static final MovePattern DIAGONAL = new MovePattern(Arrays.asList(
            DirectionPattern.RIGHT_FORWARD_DIAGONAL,
            DirectionPattern.RIGHT_BACKWARD_DIAGONAL,
            DirectionPattern.LEFT_FORWARD_DIAGONAL,
            DirectionPattern.LEFT_BACKWARD_DIAGONAL
    ));

    public static final MovePattern STRAIGHT = new MovePattern(Arrays.asList(
            DirectionPattern.RIGHT,
            DirectionPattern.LEFT,
            DirectionPattern.FORWARD,
            DirectionPattern.BACKWARD
    ));

    public static final MovePattern DIAGONAL_AND_STRAIGHT = new MovePattern(Arrays.asList(
            DirectionPattern.RIGHT_FORWARD_DIAGONAL,
            DirectionPattern.RIGHT_BACKWARD_DIAGONAL,
            DirectionPattern.LEFT_FORWARD_DIAGONAL,
            DirectionPattern.LEFT_BACKWARD_DIAGONAL,
            DirectionPattern.RIGHT,
            DirectionPattern.LEFT,
            DirectionPattern.FORWARD,
            DirectionPattern.BACKWARD
    ));

    public static final MovePattern L_SHAPE = new MovePattern(Arrays.asList(
            DirectionPattern.FORWARD_RIGHT_L_SHAPE,
            DirectionPattern.FORWARD_LEFT_L_SHAPE,
            DirectionPattern.BACKWARD_RIGHT_L_SHAPE,
            DirectionPattern.BACKWARD_LEFT_L_SHAPE,
            DirectionPattern.RIGHT_FORWARD_L_SHAPE,
            DirectionPattern.RIGHT_BACKWARD_L_SHAPE,
            DirectionPattern.LEFT_FORWARD_L_SHAPE,
            DirectionPattern.LEFT_BACKWARD_L_SHAPE
    ));

    /**
     * NOTE: Start coordinate will never be iterated over.
     */
    public Iterable<Iterable<ChessCoordinate>> getIterable(ChessCoordinate startCoordinate, @Size(min = 0) int limit) {
        return () -> directionPatterns.stream()
                .map(directionPattern -> directionPattern.getIterable(startCoordinate, limit))
                .iterator();
    }

}
