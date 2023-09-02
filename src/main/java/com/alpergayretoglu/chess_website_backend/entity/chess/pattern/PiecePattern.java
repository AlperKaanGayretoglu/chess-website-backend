package com.alpergayretoglu.chess_website_backend.entity.chess.pattern;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Data
public class PiecePattern {

    private final MovePattern movePattern;
    private final int limit;

    public static final Map<ChessPieceType, PiecePattern> chessPieceBasicMovePatternMap;
    private static final int INFINITE = Integer.MAX_VALUE;

    static {
        chessPieceBasicMovePatternMap = new HashMap<>();
        // NOTE: The Pawn has such a complex move pattern that it is calculated separately
        chessPieceBasicMovePatternMap.put(ChessPieceType.PAWN, null);
        chessPieceBasicMovePatternMap.put(ChessPieceType.KNIGHT, new PiecePattern(MovePattern.L_SHAPE, 1));
        chessPieceBasicMovePatternMap.put(ChessPieceType.BISHOP, new PiecePattern(MovePattern.DIAGONAL, INFINITE));
        chessPieceBasicMovePatternMap.put(ChessPieceType.ROOK, new PiecePattern(MovePattern.STRAIGHT, INFINITE));
        chessPieceBasicMovePatternMap.put(ChessPieceType.QUEEN, new PiecePattern(MovePattern.DIAGONAL_AND_STRAIGHT, INFINITE));
        chessPieceBasicMovePatternMap.put(ChessPieceType.KING, new PiecePattern(MovePattern.DIAGONAL_AND_STRAIGHT, 1));
    }

    public void alongEachDirectionStartingFromWhileDo(ChessCoordinate startCoordinate, Predicate<ChessCoordinate> continueCondition, Consumer<ChessCoordinate> consumer) {
        for (Iterable<ChessCoordinate> directionIterable : movePattern.getIterable(startCoordinate, limit)) {
            for (ChessCoordinate nextCoordinate : directionIterable) {
                if (!continueCondition.test(nextCoordinate)) {
                    break;
                }
                consumer.accept(nextCoordinate);
            }
        }
    }

}
