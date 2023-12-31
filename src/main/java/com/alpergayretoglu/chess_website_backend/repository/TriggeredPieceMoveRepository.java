package com.alpergayretoglu.chess_website_backend.repository;

import com.alpergayretoglu.chess_website_backend.entity.chess.move.ChessMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.TriggeredPieceMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TriggeredPieceMoveRepository extends JpaRepository<TriggeredPieceMove, String> {
    List<TriggeredPieceMove> findAllByPartOfChessMove(ChessMove chessMove);

    @Transactional
    void deleteAllByPartOfChessMoveIn(List<ChessMove> chessMoves);
}
