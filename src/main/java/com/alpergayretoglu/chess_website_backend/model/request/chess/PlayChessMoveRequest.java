package com.alpergayretoglu.chess_website_backend.model.request.chess;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PlayChessMoveRequest {

    private final String username;
    private final String chessMoveId;

}
