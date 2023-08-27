package com.alpergayretoglu.chess_website_backend.entity.chess;

import com.alpergayretoglu.chess_website_backend.entity.BaseEntity;
import com.alpergayretoglu.chess_website_backend.entity.chess.chessPiece.*;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Builder
@Getter
public class ChessBoard extends BaseEntity {

    private final ChessSquare[][] board = new ChessSquare[8][8];

    public ChessBoard() {
        for (int row = 0; row < 8; row++) {
            ChessColor chessColor = row % 2 == 0 ? ChessColor.WHITE : ChessColor.BLACK;

            board[row] = new ChessSquare[8];
            for (int column = 0; column < 8; column++) {
                board[row][column] = new ChessSquare(chessColor);
                chessColor = chessColor.getOppositeColor();
            }
        }
    }

    public static ChessBoard initializePieces(ChessBoard chessBoard) {
        for (int column = 0; column < chessBoard.board[1].length; column++) {
            chessBoard.board[1][column].setChessPiece(new Pawn(ChessColor.WHITE));
            chessBoard.board[6][column].setChessPiece(new Pawn(ChessColor.BLACK));
        }

        chessBoard.board[0][0].setChessPiece(new Rook(ChessColor.WHITE));
        chessBoard.board[0][1].setChessPiece(new Knight(ChessColor.WHITE));
        chessBoard.board[0][2].setChessPiece(new Bishop(ChessColor.WHITE));
        chessBoard.board[0][3].setChessPiece(new Queen(ChessColor.WHITE));
        chessBoard.board[0][4].setChessPiece(new King(ChessColor.WHITE));
        chessBoard.board[0][5].setChessPiece(new Bishop(ChessColor.WHITE));
        chessBoard.board[0][6].setChessPiece(new Knight(ChessColor.WHITE));
        chessBoard.board[0][7].setChessPiece(new Rook(ChessColor.WHITE));

        chessBoard.board[7][0].setChessPiece(new Rook(ChessColor.BLACK));
        chessBoard.board[7][1].setChessPiece(new Knight(ChessColor.BLACK));
        chessBoard.board[7][2].setChessPiece(new Bishop(ChessColor.BLACK));
        chessBoard.board[7][3].setChessPiece(new Queen(ChessColor.BLACK));
        chessBoard.board[7][4].setChessPiece(new King(ChessColor.BLACK));
        chessBoard.board[7][5].setChessPiece(new Bishop(ChessColor.BLACK));
        chessBoard.board[7][6].setChessPiece(new Knight(ChessColor.BLACK));
        chessBoard.board[7][7].setChessPiece(new Rook(ChessColor.BLACK));
        return chessBoard;
    }

    public ChessPiece getChessPieceAt(ChessCoordinate chessCoordinate) {
        return board[chessCoordinate.getRow()][chessCoordinate.getColumn()].getChessPiece();
    }

    public ChessPiece removeChessPieceAt(ChessCoordinate chessCoordinate) {
        return board[chessCoordinate.getRow()][chessCoordinate.getColumn()].removeChessPiece();
    }

    public void putChessPieceTo(ChessPiece chessPiece, ChessCoordinate chessCoordinate) {
        board[chessCoordinate.getRow()][chessCoordinate.getColumn()].setChessPiece(chessPiece);
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

}
