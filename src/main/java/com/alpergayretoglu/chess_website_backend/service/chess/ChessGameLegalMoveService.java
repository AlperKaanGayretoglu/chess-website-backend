package com.alpergayretoglu.chess_website_backend.service.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.ChessMove;
import com.alpergayretoglu.chess_website_backend.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log
public class ChessGameLegalMoveService {

    private final ChessPieceLegalMoveService chessPieceLegalMoveService;

    private final ChessMoveRepository chessMoveRepository;

    private final PlayedPieceMoveRepository playedPieceMoveRepository;
    private final TriggeredPieceMoveRepository triggeredPieceMoveRepository;
    private final PieceCaptureMoveRepository pieceCaptureMoveRepository;

    private final ChessGameRepository chessGameRepository;


    public void calculateAndSaveLegalMovesForCurrentPlayer(ChessGame chessGame, ChessBoardPiecesObserver chessBoardPiecesObserver) {
        List<ChessMove> chessMoves = chessMoveRepository.findAllByChessGame(chessGame);
        triggeredPieceMoveRepository.deleteAllByPartOfChessMoveIn(chessMoves);
        pieceCaptureMoveRepository.deleteAllByPartOfChessMoveIn(chessMoves);
        chessMoveRepository.deleteAll(chessMoves);

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
        triggeredPieceMoveRepository.saveAll(chessMoveRegisterer.getTriggeredPieceMoves());
        pieceCaptureMoveRepository.saveAll(chessMoveRegisterer.getPieceCaptureMoves());

        chessGameRepository.save(chessGame);
    }

}
