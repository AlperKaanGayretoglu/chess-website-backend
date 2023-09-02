package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.PlayedPieceMove;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class ChessMoveRegisterer {
    private final List<ChessMove> legalMoves = new ArrayList<>();
    private final List<PlayedPieceMove> playedPieceMoves = new ArrayList<>();

    private final ChessGame chessGame;

    public void registerNewChessMove(ChessCoordinate playedPieceFrom, ChessCoordinate playedPieceTo) {
        ChessMove chessMove = ChessMove.builder().chessGame(chessGame).build();
        legalMoves.add(chessMove);

        PlayedPieceMove playedPieceMove = new PlayedPieceMove(chessMove, playedPieceFrom, playedPieceTo);
        playedPieceMoves.add(playedPieceMove);
    }
}
