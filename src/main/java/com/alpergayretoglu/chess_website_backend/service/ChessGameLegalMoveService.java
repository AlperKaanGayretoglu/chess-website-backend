package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.repository.ChessGameRepository;
import com.alpergayretoglu.chess_website_backend.repository.ChessMoveRepository;
import com.alpergayretoglu.chess_website_backend.repository.PlayedPieceMoveRepository;
import com.alpergayretoglu.chess_website_backend.repository.TriggeredPieceMoveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log
public class ChessGameLegalMoveService {

    private final ChessPieceLegalMoveService chessPieceLegalMoveService;

    private final ChessMoveRepository chessMoveRepository;

    private final PlayedPieceMoveRepository playedPieceMoveRepository;
    private final TriggeredPieceMoveRepository triggeredPieceMoveRepository;

    private final ChessGameRepository chessGameRepository;


    public void calculateAndSaveLegalMovesForCurrentPlayer(ChessGame chessGame, ChessBoardPiecesObserver chessBoardPiecesObserver) {
        chessMoveRepository.deleteAllByChessGame(chessGame);

        ChessMoveRegisterer chessMoveRegisterer = new ChessMoveRegisterer(chessGame);

        for (ChessCoordinate chessCoordinate : chessBoardPiecesObserver.getCoordinatesOfPiecesWithColor(chessGame.getCurrentPlayerColor())) {
            chessPieceLegalMoveService.calculateLegalMovesForPieceAtSquare(
                    chessBoardPiecesObserver,
                    chessCoordinate,
                    chessMoveRegisterer
            );
        }

        chessMoveRepository.saveAll(chessMoveRegisterer.getLegalMoves());
        playedPieceMoveRepository.saveAll(chessMoveRegisterer.getPlayedPieceMoves());

        chessGameRepository.save(chessGame);
    }

}
