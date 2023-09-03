package com.alpergayretoglu.chess_website_backend.controller;

import com.alpergayretoglu.chess_website_backend.model.request.chess.PlayChessMoveRequest;
import com.alpergayretoglu.chess_website_backend.model.response.chess.PlayedChessMoveResponse;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessGameService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class ChessGameSocketController {

    private final ChessGameService chessGameService;

    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/secured/gaming/{gameId}")
    public void send(PlayChessMoveRequest playChessMoveRequest, @DestinationVariable String gameId) {
        PlayedChessMoveResponse playedChessMoveResponse = chessGameService.playMoveOnGame(playChessMoveRequest, gameId);
        simpMessagingTemplate.convertAndSend("/secured/moves/" + gameId, playedChessMoveResponse);
    }

}
