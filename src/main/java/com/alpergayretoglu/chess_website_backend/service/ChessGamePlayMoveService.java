package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.*;
import com.alpergayretoglu.chess_website_backend.exception.BusinessException;
import com.alpergayretoglu.chess_website_backend.exception.ErrorCode;
import com.alpergayretoglu.chess_website_backend.model.response.chess.PlayedChessMoveResponse;
import com.alpergayretoglu.chess_website_backend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChessGamePlayMoveService {

    private final ChessGameLegalMoveService chessGameLegalMoveService;

    private final ChessGameRepository chessGameRepository;
    private final ChessBoardRepository chessBoardRepository;
    private final ChessGameStateRepository chessGameStateRepository;

    private final ChessMoveRepository chessMoveRepository;
    private final TriggeredPieceMoveRepository triggeredPieceMoveRepository;

    public PlayedChessMoveResponse playMove(ChessGame chessGame, ChessMove chessMove) {
        List<ChessMove> legalMovesForCurrentPlayer = chessMoveRepository.findAllByChessGame(chessGame);
        ChessBoard chessBoard = chessGame.getChessBoard();

        if (!legalMovesForCurrentPlayer.contains(chessMove)) {
            throw new BusinessException(ErrorCode.ILLEGAL_MOVE());
        }

        PlayedPieceMove playedPieceMove = chessMove.getPlayedPieceMove();
        chessBoard.playMove(playedPieceMove);
        chessBoardRepository.save(chessBoard);

        List<TriggeredPieceMove> triggeredPieceMoves = triggeredPieceMoveRepository.findAllByPartOfChessMove(chessMove);
        chessBoard.playTriggeredMoves(triggeredPieceMoves);
        chessBoardRepository.save(chessBoard);

        ChessGameState chessGameState = chessGame.getChessGameState();
        chessGameState.switchCurrentPlayer();
        chessGameStateRepository.save(chessGameState);
        chessGameRepository.save(chessGame);

        chessGameLegalMoveService.calculateLegalMovesForCurrentPlayer(chessGame);

        chessGameRepository.save(chessGame);
        return PlayedChessMoveResponse.fromEntity(chessMoveRepository, triggeredPieceMoveRepository, chessGame, chessMove);
    }
}
