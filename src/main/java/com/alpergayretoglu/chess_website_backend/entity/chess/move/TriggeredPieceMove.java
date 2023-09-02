package com.alpergayretoglu.chess_website_backend.entity.chess.move;

import com.alpergayretoglu.chess_website_backend.entity.BaseEntity;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class TriggeredPieceMove extends BaseEntity implements PieceMove {

    @ManyToOne
    private ChessMove partOfChessMove;

    @AttributeOverrides({
            @AttributeOverride(name = "row", column = @Column(name = "from_row")),
            @AttributeOverride(name = "column", column = @Column(name = "from_column"))
    })
    private ChessCoordinate from;
    @AttributeOverrides({
            @AttributeOverride(name = "row", column = @Column(name = "to_row")),
            @AttributeOverride(name = "column", column = @Column(name = "to_column"))
    })
    private ChessCoordinate to;

}
