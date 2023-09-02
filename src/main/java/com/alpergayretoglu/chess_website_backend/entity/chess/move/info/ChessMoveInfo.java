package com.alpergayretoglu.chess_website_backend.entity.chess.move.info;

import com.alpergayretoglu.chess_website_backend.entity.chess.move.ChessMoveType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class ChessMoveInfo {

    @NonNull
    private final PlayedPieceMoveInfo playedPieceMoveInfo;

    @Builder.Default
    private final List<TriggeredPieceMoveInfo> triggeredPieceMoveInfos = new ArrayList<>();

    @Builder.Default
    private final List<PieceCaptureMoveInfo> pieceCaptureMoveInfos = new ArrayList<>();

    @NonNull
    private final ChessMoveType chessMoveType;

}
