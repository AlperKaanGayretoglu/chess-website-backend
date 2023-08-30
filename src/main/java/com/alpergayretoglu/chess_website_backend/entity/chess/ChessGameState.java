package com.alpergayretoglu.chess_website_backend.entity.chess;

import com.alpergayretoglu.chess_website_backend.entity.BaseEntity;
import com.alpergayretoglu.chess_website_backend.entity.User;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessGameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChessGameState extends BaseEntity {

    @ManyToOne
    private User playerWhite;
    @ManyToOne
    private User playerBlack;

    @Builder.Default
    private ChessColor currentPlayerColor = ChessColor.WHITE;

    @Builder.Default
    private ChessGameStatus chessGameStatus = ChessGameStatus.ONGOING;

    public User getCurrentPlayer() {
        return currentPlayerColor == ChessColor.WHITE ? playerWhite : playerBlack;
    }

    public boolean hasGameEnded() {
        return chessGameStatus.isGameEnded();
    }

    public List<User> getPlayers() {
        List<User> players = new ArrayList<>();
        players.add(playerWhite);
        players.add(playerBlack);
        return Collections.unmodifiableList(players);
    }

    public void switchCurrentPlayer() {
        currentPlayerColor = currentPlayerColor == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE;
    }

}
