package com.alpergayretoglu.chess_website_backend.service.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessBoard;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGameState;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.*;
import com.alpergayretoglu.chess_website_backend.exception.BusinessException;
import com.alpergayretoglu.chess_website_backend.exception.ErrorCode;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.response.chess.ChessMoveResponse;
import com.alpergayretoglu.chess_website_backend.model.response.chess.PlayedChessMoveResponse;
import com.alpergayretoglu.chess_website_backend.repository.*;
import com.alpergayretoglu.chess_website_backend.service.chess.legalMove.ChessGameLegalMoveService;
import com.alpergayretoglu.chess_website_backend.service.mapper.ChessMoveMapper;
import com.alpergayretoglu.chess_website_backend.service.mapper.PlayedChessMoveMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.alpergayretoglu.chess_website_backend.service.chess.ChessDefaultService.getDefaultLongCastlingRookCoordinate;
import static com.alpergayretoglu.chess_website_backend.service.chess.ChessDefaultService.getDefaultShortCastlingRookCoordinate;

@Service
@AllArgsConstructor
public class ChessGamePlayMoveService {

    private final ChessGameLegalMoveService chessGameLegalMoveService;

    private final ChessGameRepository chessGameRepository;
    private final ChessBoardRepository chessBoardRepository;
    private final ChessGameStateRepository chessGameStateRepository;

    private final ChessMoveRepository chessMoveRepository;
    private final TriggeredPieceMoveRepository triggeredPieceMoveRepository;
    private final PieceCaptureMoveRepository pieceCaptureMoveRepository;

    private final PlayedChessMoveMapper playedChessMoveMapper;
    private final ChessMoveMapper chessMoveMapper;

    public PlayedChessMoveResponse playMove(ChessGame chessGame, ChessMove chessMove) {
        final ChessBoardPiecesModifier chessBoardPiecesModifier = new ChessBoardPiecesModifier(chessGame.getChessBoard().getChessPieces());

        List<ChessMove> legalMovesForCurrentPlayer = chessMoveRepository.findAllByChessGame(chessGame);

        ChessBoard chessBoard = chessGame.getChessBoard();

        if (!legalMovesForCurrentPlayer.contains(chessMove)) {
            throw new BusinessException(ErrorCode.ILLEGAL_MOVE());
        }

        PlayedPieceMove toBePlayedPieceMove = chessMove.getPlayedPieceMove();
        List<TriggeredPieceMove> toBeTriggeredPieceMoves = triggeredPieceMoveRepository.findAllByPartOfChessMove(chessMove);
        List<PieceCaptureMove> toBeCapturedPieceMoves = pieceCaptureMoveRepository.findAllByPartOfChessMove(chessMove);
        PieceTransformationMove toBePieceTransformationMove = chessMove.getPieceTransformationMove();

        modifyCastlingAvailabilityIfThisMoveIsToBePlayed(
                chessGame,
                chessBoardPiecesModifier.turnIntoObserver(),
                toBePlayedPieceMove,
                toBeTriggeredPieceMoves,
                toBeCapturedPieceMoves
        );
        chessGameRepository.save(chessGame);

        ChessMoveResponse chessMoveResponse = chessMoveMapper.fromEntity(chessMove);

        chessBoardPiecesModifier.playChessMove(
                toBePlayedPieceMove,
                toBeTriggeredPieceMoves,
                toBeCapturedPieceMoves,
                toBePieceTransformationMove
        );

        ChessGameState chessGameState = chessGame.getChessGameState();
        chessGameState.switchCurrentPlayer();

        chessBoardRepository.save(chessBoard);
        chessGameStateRepository.save(chessGameState);

        chessGameLegalMoveService.calculateAndSaveLegalMovesForCurrentPlayer(chessGame, chessBoardPiecesModifier.turnIntoObserver(), chessMove);
        return playedChessMoveMapper.fromEntity(chessGame, chessMoveResponse);
    }

    // TODO: The logic for castling should be SIGNIFICANTLY improved! Since it would require a lot of logic changes, this is a temporary solution.
    private void modifyCastlingAvailabilityIfThisMoveIsToBePlayed(
            ChessGame chessGame,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            PlayedPieceMove playedPieceMove,
            List<TriggeredPieceMove> triggeredPieceMoves,
            List<PieceCaptureMove> pieceCaptureMoves
    ) {
        modifyCastlingAvailabilityIfThePieceIsToBeMovedFrom(chessGame, chessBoardPiecesObserver, playedPieceMove.getFrom());

        for (TriggeredPieceMove triggeredPieceMove : triggeredPieceMoves) {
            modifyCastlingAvailabilityIfThePieceIsToBeMovedFrom(chessGame, chessBoardPiecesObserver, triggeredPieceMove.getFrom());
        }

        for (PieceCaptureMove pieceCaptureMove : pieceCaptureMoves) {
            modifyCastlingAvailabilityIfThePieceIsToBeMovedFrom(chessGame, chessBoardPiecesObserver, pieceCaptureMove.getFrom());
        }
    }

    private void modifyCastlingAvailabilityIfThePieceIsToBeMovedFrom(ChessGame chessGame, ChessBoardPiecesObserver chessBoardPiecesObserver, ChessCoordinate fromChessCoordinate) {
        switch (chessBoardPiecesObserver.getChessPieceAt(fromChessCoordinate)) {
            case WHITE_KING:
                chessGame.setShortCastlingStillAvailableForWhite(false);
                chessGame.setLongCastlingStillAvailableForWhite(false);
                break;
            case BLACK_KING:
                chessGame.setShortCastlingStillAvailableForBlack(false);
                chessGame.setLongCastlingStillAvailableForBlack(false);
                break;
            case WHITE_ROOK:
                if (fromChessCoordinate.equals(getDefaultShortCastlingRookCoordinate(ChessColor.WHITE))) {
                    chessGame.setShortCastlingStillAvailableForWhite(false);
                }
                if (fromChessCoordinate.equals(getDefaultLongCastlingRookCoordinate(ChessColor.WHITE))) {
                    chessGame.setLongCastlingStillAvailableForWhite(false);
                }
                break;
            case BLACK_ROOK:
                if (fromChessCoordinate.equals(getDefaultShortCastlingRookCoordinate(ChessColor.BLACK))) {
                    chessGame.setShortCastlingStillAvailableForBlack(false);
                }
                if (fromChessCoordinate.equals(getDefaultLongCastlingRookCoordinate(ChessColor.BLACK))) {
                    chessGame.setLongCastlingStillAvailableForBlack(false);
                }
                break;
        }
    }


}
