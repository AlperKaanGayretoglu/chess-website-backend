package com.alpergayretoglu.chess_website_backend.entity.chess;

import com.alpergayretoglu.chess_website_backend.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class PlayedPieceMove extends BaseEntity implements PieceMove {

    @OneToOne
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