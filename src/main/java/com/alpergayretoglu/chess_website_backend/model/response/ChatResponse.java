package com.alpergayretoglu.chess_website_backend.model.response;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ChatResponse {

    private String chatId;
    private List<String> userIds;

    public static ChatResponse fromEntity(ChessGame chessGame) {
        // TODO: fix
        // return ChatResponse.builder()
        //         .chatId(chessGame.getId())
        //         .userIds(chessGame.getUsers().stream().map(User::getId).collect(Collectors.toList()))
        //         .build();
        return null;
    }

}
