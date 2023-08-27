package com.alpergayretoglu.chess_website_backend.repository;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.TriggeredPieceMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TriggeredPieceMoveRepository extends JpaRepository<TriggeredPieceMove, String> {
    List<TriggeredPieceMove> findAllByPartOfChessMove(ChessMove chessMove);
}
