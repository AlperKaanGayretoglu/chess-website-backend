package com.alpergayretoglu.chess_website_backend.entity.chess;

import com.alpergayretoglu.chess_website_backend.entity.BaseEntity;
import com.alpergayretoglu.chess_website_backend.entity.User;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ChessGame extends BaseEntity {

    @OneToOne
    private ChessGameState chessGameState;

    @OneToOne
    private ChessBoard chessBoard;

    public boolean hasGameEnded() {
        return chessGameState.hasGameEnded();
    }

    public boolean isCurrentPlayerInCheck() {
        return chessGameState.isCurrentPlayerInCheck();
    }

    public boolean isWhiteInCheck() {
        return chessGameState.isWhiteInCheck();
    }

    public boolean isBlackInCheck() {
        return chessGameState.isBlackInCheck();
    }

    public User getCurrentPlayer() {
        return chessGameState.getCurrentPlayer();
    }

    public ChessColor getCurrentPlayerColor() {
        return chessGameState.getCurrentPlayerColor();
    }

    public User getPlayerWhite() {
        return chessGameState.getPlayerWhite();
    }

    public User getPlayerBlack() {
        return chessGameState.getPlayerBlack();
    }

}
