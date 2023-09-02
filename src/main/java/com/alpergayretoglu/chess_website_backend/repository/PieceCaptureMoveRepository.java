package com.alpergayretoglu.chess_website_backend.repository;

import com.alpergayretoglu.chess_website_backend.entity.chess.move.ChessMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.PieceCaptureMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PieceCaptureMoveRepository extends JpaRepository<PieceCaptureMove, String> {
    List<PieceCaptureMove> findAllByPartOfChessMove(ChessMove chessMove);

    @Transactional
    void deleteAllByPartOfChessMoveIn(List<ChessMove> chessMoves);
}
