package com.alpergayretoglu.chess_website_backend.entity.chess.move;

import com.alpergayretoglu.chess_website_backend.entity.BaseEntity;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
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

    @OneToMany(mappedBy = "partOfChessMove")
    @Builder.Default
    private List<PieceCaptureMove> pieceCaptureMoves = new ArrayList<>();

    @OneToOne(mappedBy = "partOfChessMove", cascade = CascadeType.ALL)
    private PieceTransformationMove pieceTransformationMove;

    private ChessMoveType chessMoveType;

}
