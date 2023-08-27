package com.alpergayretoglu.chess_website_backend.entity.chess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ChessCoordinate {
    @Size(min = 0, max = 7)
    private int row;
    @Size(min = 0, max = 7)
    private int column;

    public static boolean isCoordinateValid(int row, int column) {
        return row >= 0 && row <= 7 && column >= 0 && column <= 7;
    }

}
