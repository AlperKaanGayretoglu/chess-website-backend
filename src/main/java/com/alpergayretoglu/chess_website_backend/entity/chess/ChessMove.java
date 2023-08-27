package com.alpergayretoglu.chess_website_backend.entity.chess;

import com.alpergayretoglu.chess_website_backend.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class ChessMove extends BaseEntity {

    @ManyToOne
    private ChessGame chessGame;

    @OneToOne(mappedBy = "partOfChessMove", cascade = CascadeType.ALL)
    private PlayedPieceMove playedPieceMove;

    @OneToMany(mappedBy = "partOfChessMove")
    @Builder.Default
    private List<TriggeredPieceMove> triggeredPieceMoves = new ArrayList<>();

}
