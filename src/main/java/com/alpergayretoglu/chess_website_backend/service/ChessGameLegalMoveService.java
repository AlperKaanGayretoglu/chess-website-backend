package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.*;
import com.alpergayretoglu.chess_website_backend.repository.ChessGameRepository;
import com.alpergayretoglu.chess_website_backend.repository.ChessMoveRepository;
import com.alpergayretoglu.chess_website_backend.repository.PlayedPieceMoveRepository;
import com.alpergayretoglu.chess_website_backend.repository.TriggeredPieceMoveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Log
public class ChessGameLegalMoveService {

    private final ChessPieceLegalMoveService chessPieceLegalMoveService;

    private final ChessMoveRepository chessMoveRepository;
    private final PlayedPieceMoveRepository playedPieceMoveRepository;
    private final TriggeredPieceMoveRepository triggeredPieceMoveRepository;

    private final ChessGameRepository chessGameRepository;

    public void calculateLegalMovesForCurrentPlayer(ChessGame chessGame) {
        List<ChessMove> oldLegalMoves = chessMoveRepository.findAllByChessGame(chessGame);

        oldLegalMoves.forEach(chessMove -> {
            PlayedPieceMove playedPieceMove = chessMove.getPlayedPieceMove();
            playedPieceMove.setPartOfChessMove(null);
            playedPieceMoveRepository.save(playedPieceMove);

            List<TriggeredPieceMove> triggeredPieceMoves = triggeredPieceMoveRepository.findAllByPartOfChessMove(chessMove);
            triggeredPieceMoves.forEach(pieceMove -> {
                pieceMove.setPartOfChessMove(null);
                triggeredPieceMoveRepository.save(pieceMove);
            });

            chessMove.setPlayedPieceMove(null);
            triggeredPieceMoveRepository.findAllByPartOfChessMove(chessMove).clear();
            chessMoveRepository.save(chessMove);

            chessMove.setChessGame(null);
            chessMoveRepository.delete(chessMove);

            playedPieceMoveRepository.delete(playedPieceMove);
            triggeredPieceMoveRepository.deleteAll(triggeredPieceMoves);
        });

        List<ChessMove> legalMoves = new ArrayList<>();
        ChessBoard chessBoard = chessGame.getChessBoard();

        int row = 0;
        int column = 0;
        for (ChessSquare[] chessSquares : chessBoard.getBoard()) {
            column = 0;
            for (ChessSquare chessSquare : chessSquares) {
                if (!chessSquare.isEmpty() && chessSquare.getChessPiece().getChessColor() == chessGame.getCurrentPlayerColor()) {
                    ChessCoordinate currentCoordinate = new ChessCoordinate(row, column);
                    legalMoves.addAll(chessPieceLegalMoveService.calculateLegalMovesForPieceAtSquare(chessBoard, currentCoordinate));
                }
                column++;
            }
            row++;
        }

        // TODO: Remove this temporary code
        // ------------------------------------------------------ TEMPORARY ------------------------------------------------------
        PlayedPieceMove playedPieceMove = new PlayedPieceMove(null, new ChessCoordinate(0, 0), new ChessCoordinate(0, 0));

        ChessMove chessMove = ChessMove.builder()
                .build();
        chessMoveRepository.save(chessMove);

        playedPieceMove.setPartOfChessMove(chessMove);
        playedPieceMoveRepository.save(playedPieceMove);

        legalMoves.add(chessMove);
        // ------------------------------------------------------ TEMPORARY ------------------------------------------------------

        legalMoves.forEach(move -> {
            move.setChessGame(chessGame);
            chessMoveRepository.save(move);
        });
        chessGameRepository.save(chessGame);
    }

}