package com.alpergayretoglu.chess_website_backend.model.response.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessMove;
import com.alpergayretoglu.chess_website_backend.model.response.EntityResponse;
import com.alpergayretoglu.chess_website_backend.repository.TriggeredPieceMoveRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class ChessMoveResponse extends EntityResponse {
    private PieceMoveResponse playedPieceMove;
    private List<PieceMoveResponse> triggeredPieceMoves;

    public static ChessMoveResponse fromEntity(TriggeredPieceMoveRepository triggeredPieceMoveRepository, ChessMove chessMove) {
        ChessMoveResponse response = ChessMoveResponse.builder()
                .playedPieceMove(PieceMoveResponse.fromEntity(chessMove.getPlayedPieceMove()))
                .triggeredPieceMoves(triggeredPieceMoveRepository.findAllByPartOfChessMove(chessMove).stream().map(PieceMoveResponse::fromEntity).collect(Collectors.toList()))
                .build();

        return setCommonValuesFromEntity(response, chessMove);
    }
}
