package com.alpergayretoglu.chess_website_backend.service.chess.legalMove;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessBoardPiecesObserver;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class PlayerInCheckService {
    public static boolean isPlayerWithColorInCheck(ChessColor playerColor, ChessBoardPiecesObserver chessBoardPiecesObserver) {
        List<ChessCoordinate> enemyPieceCoordinates = chessBoardPiecesObserver.getCoordinatesOfPiecesWithColor(playerColor.getOppositeColor());

        // TODO: Make the logic the find the king more efficient (for example, store the king's coordinate in the ChessGame entity for both players)
        ChessCoordinate kingCoordinate = chessBoardPiecesObserver.getCoordinatesOfPiecesWithColor(playerColor)
                .stream()
                .filter(chessCoordinate -> chessBoardPiecesObserver
                        .getChessPieceAt(chessCoordinate)
                        .getChessPieceType().equals(ChessPieceType.KING))
                .findFirst().orElseThrow(() -> new RuntimeException("King not found"));

        for (ChessCoordinate enemyPieceCoordinate : enemyPieceCoordinates) {
            if (ChessPieceLegalMoveService.isThisALegalMoveForPiece(
                    false,
                    chessBoardPiecesObserver,
                    enemyPieceCoordinate,
                    kingCoordinate,
                    null
            )) {
                return true;
            }
        }

        return false;
    }

}
