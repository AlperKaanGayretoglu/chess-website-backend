package com.alpergayretoglu.chess_website_backend.controller;

import com.alpergayretoglu.chess_website_backend.model.request.chess.CreateChessGameRequest;
import com.alpergayretoglu.chess_website_backend.model.response.chess.ChessGameResponse;
import com.alpergayretoglu.chess_website_backend.model.response.chess.CreateChessGameResponse;
import com.alpergayretoglu.chess_website_backend.service.AuthenticationService;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessGameService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/game")
@CrossOrigin
public class ChessGameController {

    private final ChessGameService chessGameService;

    private final AuthenticationService authenticationService;

    @GetMapping("/{gameId}")
    public ChessGameResponse getChessGame(@PathVariable String gameId) {
        return chessGameService.getChessGame(authenticationService.getAuthenticatedUser(), gameId);
    }

    @PostMapping
    public CreateChessGameResponse createChessGame(@Valid @RequestBody CreateChessGameRequest createChessGameRequest) {
        return chessGameService.createChessGame(authenticationService.getAuthenticatedUser(), createChessGameRequest);
    }

}
