package com.alpergayretoglu.chess_website_backend.service.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.*;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.ChessMoveInfo;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.PieceCaptureMoveInfo;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.PlayedPieceMoveInfo;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.TriggeredPieceMoveInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Getter
public class ChessMoveRegisterer {
    private final List<ChessMove> legalMoves = new ArrayList<>();
    private final List<PlayedPieceMove> playedPieceMoves = new ArrayList<>();
    private final List<TriggeredPieceMove> triggeredPieceMoves = new ArrayList<>();
    private final List<PieceCaptureMove> pieceCaptureMoves = new ArrayList<>();

    private final ChessGame chessGame;

    public void registerNewChessMove(ChessMoveInfo chessMoveInfo) {
        ChessMove chessMove = ChessMove.builder()
                .chessGame(chessGame)
                .chessMoveType(chessMoveInfo.getChessMoveType())
                .build();
        legalMoves.add(chessMove);

        registerNewPlayedPieceMove(chessMove, chessMoveInfo.getPlayedPieceMoveInfo());
        registerNewTriggeredPieceMoves(chessMove, chessMoveInfo.getTriggeredPieceMoveInfos());
        registerNewPieceCaptureMoves(chessMove, chessMoveInfo.getPieceCaptureMoveInfos());
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

}
