package com.alpergayretoglu.chess_website_backend.service.mapper;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.model.response.chess.ChessMoveResponse;
import com.alpergayretoglu.chess_website_backend.model.response.chess.PlayedChessMoveResponse;
import com.alpergayretoglu.chess_website_backend.repository.ChessMoveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayedChessMoveMapper {
    private final ChessMoveRepository chessMoveRepository;

    private final ChessMoveMapper chessMoveMapper;

    public PlayedChessMoveResponse fromEntity(ChessGame game, ChessMoveResponse moveResponse) {
        return PlayedChessMoveResponse.builder()
                .playedChessMove(moveResponse)
                .currentPlayerUsername(game.getCurrentPlayer().getUsername())
                .legalMovesForCurrentPlayer(
                        chessMoveRepository.findAllByChessGame(game)
                                .stream()
                                .map(chessMoveMapper::fromEntity)
                                .collect(Collectors.toList())
                )
                .build();
    }

}
