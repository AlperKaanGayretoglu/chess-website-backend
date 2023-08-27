package com.alpergayretoglu.chess_website_backend.model.response;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessMove;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageResponse {

    private String username;
    private String text;
    private String time;

    public static MessageResponse fromEntity(ChessMove chessMove) {
        // TODO: fix
        //return MessageResponse.builder()
        //        .username(chessMove.getFrom().getUsername())
        //        .text(chessMove.getText())
        //        .time(chessMove.getCreated().toString())
        //        .build();
        return null;
    }

}
