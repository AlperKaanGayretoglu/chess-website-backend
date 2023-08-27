package com.alpergayretoglu.chess_website_backend.entity.chess.movePattern;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@NoArgsConstructor
public class MovePatternIterator implements Iterator<ChessCoordinate> {

    private ListIterator<ChessCoordinate> iterator;

    public MovePatternIterator(MovePattern movePattern, ChessCoordinate startCoordinate) {
        int[][] directionChanges = movePattern.getDirectionChanges();
        int limit = movePattern.getLimit();

        List<ChessCoordinate> coordinates = new ArrayList<>();

        for (int[] direction : directionChanges) {
            int row = startCoordinate.getRow();
            int column = startCoordinate.getColumn();

            for (int i = 0; i < limit; i++) {
                row += direction[0];
                column += direction[1];

                if (!ChessCoordinate.isCoordinateValid(row, column)) {
                    break;
                }

                coordinates.add(new ChessCoordinate(row, column));
            }
        }

        this.iterator = coordinates.listIterator();
    }

    public void setStartCoordinate(ChessCoordinate startCoordinate) {

    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public ChessCoordinate next() {
        return this.iterator.next();
    }

}
