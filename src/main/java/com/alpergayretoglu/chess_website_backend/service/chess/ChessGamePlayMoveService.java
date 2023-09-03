package com.alpergayretoglu.chess_website_backend.service.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessBoard;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGameState;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.ChessMove;
import com.alpergayretoglu.chess_website_backend.exception.BusinessException;
import com.alpergayretoglu.chess_website_backend.exception.ErrorCode;
import com.alpergayretoglu.chess_website_backend.model.response.chess.PlayedChessMoveResponse;
import com.alpergayretoglu.chess_website_backend.repository.*;
import com.alpergayretoglu.chess_website_backend.service.chess.legalMove.ChessGameLegalMoveService;
import com.alpergayretoglu.chess_website_backend.service.mapper.PlayedChessMoveMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChessGamePlayMoveService {

    private final ChessGameLegalMoveService chessGameLegalMoveService;

    private final ChessBoardRepository chessBoardRepository;
    private final ChessGameStateRepository chessGameStateRepository;

    private final ChessMoveRepository chessMoveRepository;
    private final TriggeredPieceMoveRepository triggeredPieceMoveRepository;
    private final PieceCaptureMoveRepository pieceCaptureMoveRepository;

    private final PlayedChessMoveMapper playedChessMoveMapper;

    public PlayedChessMoveResponse playMove(ChessGame chessGame, ChessMove chessMove) {
        final ChessBoardPiecesModifier chessBoardPiecesModifier = new ChessBoardPiecesModifier(chessGame.getChessBoard().getChessPieces());

        List<ChessMove> legalMovesForCurrentPlayer = chessMoveRepository.findAllByChessGame(chessGame);

        ChessBoard chessBoard = chessGame.getChessBoard();

        if (!legalMovesForCurrentPlayer.contains(chessMove)) {
            throw new BusinessException(ErrorCode.ILLEGAL_MOVE());
        }

        chessBoardPiecesModifier.playChessMove(
                chessMove.getPlayedPieceMove(),
                triggeredPieceMoveRepository.findAllByPartOfChessMove(chessMove),
                pieceCaptureMoveRepository.findAllByPartOfChessMove(chessMove)
        );

        ChessGameState chessGameState = chessGame.getChessGameState();
        chessGameState.switchCurrentPlayer();

        chessBoardRepository.save(chessBoard);
        chessGameStateRepository.save(chessGameState);

        chessGameLegalMoveService.calculateAndSaveLegalMovesForCurrentPlayer(chessGame, chessBoardPiecesModifier.turnIntoObserver());
        return playedChessMoveMapper.fromEntity(chessGame, chessMove);
    }
}
