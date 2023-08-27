package com.alpergayretoglu.chess_website_backend.repository;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChessMoveRepository extends JpaRepository<ChessMove, String> {
    Optional<ChessMove> findByChessGameAndId(ChessGame chessGame, String id);
    
    List<ChessMove> findAllByChessGame(ChessGame chessGame);
}
