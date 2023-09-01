package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessMove;
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

    public void calculateAndSaveLegalMovesForCurrentPlayer(ChessGame chessGame, ChessBoardPiecesModifier chessBoardPiecesModifier) {
        chessMoveRepository.deleteAllByChessGame(chessGame);

        List<ChessMove> legalMoves = new ArrayList<>();

        for (ChessCoordinate chessCoordinate : chessBoardPiecesModifier.getCoordinatesOfPiecesWithColor(chessGame.getCurrentPlayerColor())) {
            legalMoves.addAll(chessPieceLegalMoveService.calculateLegalMovesForPieceAtSquare(chessBoardPiecesModifier, chessCoordinate));
        }

        legalMoves.forEach(move -> {
            move.setChessGame(chessGame);
            chessMoveRepository.save(move);
        });
        chessGameRepository.save(chessGame);
    }

}
