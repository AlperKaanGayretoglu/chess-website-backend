package com.alpergayretoglu.chess_website_backend.service.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.*;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.*;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Getter
public class ChessMoveRegisterer {
    private final List<ChessMove> legalMoves = new ArrayList<>();
    private final List<PlayedPieceMove> playedPieceMoves = new ArrayList<>();
    private final List<TriggeredPieceMove> triggeredPieceMoves = new ArrayList<>();
    private final List<PieceCaptureMove> pieceCaptureMoves = new ArrayList<>();
    private final List<PieceTransformationMove> pieceTransformationMoves = new ArrayList<>();

    private final ChessGame chessGame;

    private Predicate<ChessMoveInfo> filter;

    public ChessMoveRegisterer(ChessGame chessGame) {
        this.chessGame = chessGame;
        this.filter = chessMoveInfo -> true;
    }

    public void addFilter(Predicate<ChessMoveInfo> filter) {
        this.filter = filter;
    }

    public void removeFilter() {
        this.filter = chessMoveInfo -> true;
    }

    public void registerNewChessMove(ChessMoveInfo chessMoveInfo) {
        if (!filter.test(chessMoveInfo)) {
            return;
        }

        ChessMove chessMove = ChessMove.builder()
                .chessGame(chessGame)
                .chessMoveType(chessMoveInfo.getChessMoveType())
                .build();
        legalMoves.add(chessMove);

        registerNewPlayedPieceMove(chessMove, chessMoveInfo.getPlayedPieceMoveInfo());
        registerNewTriggeredPieceMoves(chessMove, chessMoveInfo.getTriggeredPieceMoveInfos());
        registerNewPieceCaptureMoves(chessMove, chessMoveInfo.getPieceCaptureMoveInfos());
        registerNewPieceTransformationMove(chessMove, chessMoveInfo.getPieceTransformationMoveInfo());
    }

    public void registerNewNormalPieceMove(ChessCoordinate playedPieceFrom, ChessCoordinate playedPieceTo) {
        registerNewChessMove(ChessMoveInfo.builder()
                .playedPieceMoveInfo(new PlayedPieceMoveInfo(playedPieceFrom, playedPieceTo))
                .chessMoveType(ChessMoveType.NORMAL_PIECE_MOVEMENT)
                .build()
        );
    }

    public void registerNewNormalCaptureMove(ChessCoordinate playedPieceFrom, ChessCoordinate capturedPieceAt) {
        registerNewChessMove(ChessMoveInfo.builder()
                .playedPieceMoveInfo(new PlayedPieceMoveInfo(playedPieceFrom, capturedPieceAt))
                .pieceCaptureMoveInfos(Collections.singletonList(new PieceCaptureMoveInfo(capturedPieceAt)))
                .chessMoveType(ChessMoveType.NORMAL_PIECE_CAPTURE)
                .build()
        );
    }

    public void registerNewPawnPromotionMoveForAllValidPieces(ChessCoordinate playedPieceFrom, ChessCoordinate playedPieceTo, ChessColor chessColor) {
        for (ChessPiece chessPiece : ChessPiece.getAllPiecesWithColor(chessColor)) {
            if (chessPiece.getChessPieceType() == ChessPieceType.PAWN || chessPiece.getChessPieceType() == ChessPieceType.KING) {
                continue;
            }
            
            registerNewChessMove(ChessMoveInfo.builder()
                    .playedPieceMoveInfo(new PlayedPieceMoveInfo(playedPieceFrom, playedPieceTo))
                    .pieceTransformationMoveInfo(new PieceTransformationMoveInfo(playedPieceTo, chessPiece))
                    .chessMoveType(ChessMoveType.PAWN_PROMOTION)
                    .build()
            );
        }
    }

    public void registerNewPawnPromotionMoveForAllValidTypesWithPieceCapture(ChessCoordinate playedPieceFrom, ChessCoordinate capturedPieceAt, ChessColor chessColor) {
        for (ChessPiece chessPiece : ChessPiece.getAllPiecesWithColor(chessColor)) {
            if (chessPiece.getChessPieceType() == ChessPieceType.PAWN || chessPiece.getChessPieceType() == ChessPieceType.KING) {
                continue;
            }

            registerNewChessMove(ChessMoveInfo.builder()
                    .playedPieceMoveInfo(new PlayedPieceMoveInfo(playedPieceFrom, capturedPieceAt))
                    .pieceCaptureMoveInfos(Collections.singletonList(new PieceCaptureMoveInfo(capturedPieceAt)))
                    .pieceTransformationMoveInfo(new PieceTransformationMoveInfo(capturedPieceAt, chessPiece))
                    .chessMoveType(ChessMoveType.PAWN_PROMOTION_WITH_CAPTURE)
                    .build()
            );
        }
    }


    private void registerNewPlayedPieceMove(ChessMove chessMove, PlayedPieceMoveInfo playedPieceMoveInfo) {
        PlayedPieceMove playedPieceMove = new PlayedPieceMove(chessMove, playedPieceMoveInfo.getPlayedPieceFrom(), playedPieceMoveInfo.getPlayedPieceTo());
        playedPieceMoves.add(playedPieceMove);
    }

    private void registerNewTriggeredPieceMoves(ChessMove chessMove, List<TriggeredPieceMoveInfo> triggeredPieceMoveInfos) {
        for (TriggeredPieceMoveInfo triggeredPieceMoveInfo : triggeredPieceMoveInfos) {
            TriggeredPieceMove triggeredPieceMove = new TriggeredPieceMove(chessMove, triggeredPieceMoveInfo.getTriggeredPieceFrom(), triggeredPieceMoveInfo.getTriggeredPieceTo());
            triggeredPieceMoves.add(triggeredPieceMove);
        }
    }

    private void registerNewPieceCaptureMoves(ChessMove chessMove, List<PieceCaptureMoveInfo> pieceCaptureMoveInfos) {
        for (PieceCaptureMoveInfo pieceCaptureMoveInfo : pieceCaptureMoveInfos) {
            PieceCaptureMove pieceCaptureMove = new PieceCaptureMove(chessMove, pieceCaptureMoveInfo.getPieceCaptureFrom());
            pieceCaptureMoves.add(pieceCaptureMove);
        }
    }

    private void registerNewPieceTransformationMove(ChessMove chessMove, PieceTransformationMoveInfo pieceTransformationMoveInfo) {
        if (pieceTransformationMoveInfo == null) {
            return;
        }
        PieceTransformationMove pieceTransformationMove = new PieceTransformationMove(chessMove, pieceTransformationMoveInfo.getAt(), pieceTransformationMoveInfo.getTransformTo());
        pieceTransformationMoves.add(pieceTransformationMove);
    }

}
