package com.alpergayretoglu.chess_website_backend.entity.chess.move;

import com.alpergayretoglu.chess_website_backend.entity.BaseEntity;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class PieceTransformationMove extends BaseEntity {

    @OneToOne
    private ChessMove partOfChessMove;

    @AttributeOverrides({
            @AttributeOverride(name = "row", column = @Column(name = "at_row")),
            @AttributeOverride(name = "column", column = @Column(name = "at_column"))
    })
    private ChessCoordinate at;

    private ChessPiece transformTo;

}