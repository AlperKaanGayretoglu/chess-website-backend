package com.alpergayretoglu.chess_website_backend.entity.chess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

/**
 * USAGE: When you look from white POV, the top-left corner should be the (0,0) point.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class ChessCoordinate {
    @Size(min = 0, max = 7)
    private int row;
    @Size(min = 0, max = 7)
    @Column(name = "col")
    private int column;

    public static boolean isCoordinateValid(int row, int column) {
        return row >= 0 && row <= 7 && column >= 0 && column <= 7;
    }

    @Override
    public String toString() {
        return "row=" + row + ",column=" + column;
    }

}
