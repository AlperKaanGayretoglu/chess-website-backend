package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.repository.ChessMoveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log
public class ChessGameLegalMoveService {

    private final ChessPieceLegalMoveService chessPieceLegalMoveService;

    private final ChessMoveRepository chessMoveRepository;


    public void calculateAndSaveLegalMovesForCurrentPlayer(ChessGame chessGame, ChessBoardPiecesModifier chessBoardPiecesModifier) {
        chessMoveRepository.deleteAllByChessGame(chessGame);

        for (ChessCoordinate chessCoordinate : chessBoardPiecesModifier.getCoordinatesOfPiecesWithColor(chessGame.getCurrentPlayerColor())) {
            chessPieceLegalMoveService.calculateAndSaveLegalMovesForPieceAtSquare(chessBoardPiecesModifier, chessGame, chessCoordinate);
        }
    }

}
