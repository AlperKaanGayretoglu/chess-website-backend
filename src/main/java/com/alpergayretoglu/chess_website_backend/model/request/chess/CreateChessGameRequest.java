package com.alpergayretoglu.chess_website_backend.model.request.chess;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateChessGameRequest {

    private String firstPlayerUsername;
    private String secondPlayerUsername;

}
