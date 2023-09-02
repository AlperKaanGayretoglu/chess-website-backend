package com.alpergayretoglu.chess_website_backend.service.mapper;

import com.alpergayretoglu.chess_website_backend.entity.chess.move.ChessMove;
import com.alpergayretoglu.chess_website_backend.model.response.chess.ChessMoveResponse;
import com.alpergayretoglu.chess_website_backend.model.response.chess.PieceMoveResponse;
import com.alpergayretoglu.chess_website_backend.repository.TriggeredPieceMoveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChessMoveMapper {

    private final TriggeredPieceMoveRepository triggeredPieceMoveRepository;

    public ChessMoveResponse fromEntity(ChessMove chessMove) {
        return ChessMoveResponse.builder()
                .id(chessMove.getId())
                .playedPieceMove(PieceMoveResponse.fromEntity(chessMove.getPlayedPieceMove()))
                .triggeredPieceMoves(triggeredPieceMoveRepository.findAllByPartOfChessMove(chessMove).stream().map(PieceMoveResponse::fromEntity).collect(Collectors.toList()))
                .build();
    }
}
