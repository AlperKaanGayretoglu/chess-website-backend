package com.alpergayretoglu.chess_website_backend.entity.chess;

import com.alpergayretoglu.chess_website_backend.entity.BaseEntity;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Builder
@Getter
@NoArgsConstructor
public class ChessBoard extends BaseEntity {

    @ElementCollection(fetch = FetchType.EAGER)
    private final Map<ChessCoordinate, ChessPiece> chessPieces = new HashMap<>();

    public ChessPiece getChessPieceAt(ChessCoordinate chessCoordinate) {
        return chessPieces.get(chessCoordinate);
    }

    public ChessPiece removeChessPieceAt(ChessCoordinate chessCoordinate) {
        return chessPieces.remove(chessCoordinate);
    }

    public void putChessPieceTo(ChessPiece chessPiece, ChessCoordinate chessCoordinate) {
        chessPieces.put(chessCoordinate, chessPiece);
    }

    public void playMove(PlayedPieceMove playedPieceMove) {
        ChessPiece chessPiece = removeChessPieceAt(playedPieceMove.getFrom());
        putChessPieceTo(chessPiece, playedPieceMove.getTo());
    }

    public void playTriggeredMoves(List<TriggeredPieceMove> triggeredPieceMoves) {
        triggeredPieceMoves.forEach(triggeredPieceMove -> {
            ChessPiece chessPiece = removeChessPieceAt(triggeredPieceMove.getFrom());
            putChessPieceTo(chessPiece, triggeredPieceMove.getTo());
        });
    }

    public List<ChessCoordinate> getCoordinatesOfPiecesWithColor(ChessColor chessColor) {
        List<ChessCoordinate> chessCoordinates = new ArrayList<>();

        for (Map.Entry<ChessCoordinate, ChessPiece> entry : chessPieces.entrySet()) {
            if (entry.getValue().getChessColor() == chessColor) {
                chessCoordinates.add(entry.getKey());
            }
        }

        return chessCoordinates;
    }

}
