package com.alpergayretoglu.chess_website_backend.repository;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChessGameRepository extends JpaRepository<ChessGame, String> {
}
