package com.alpergayretoglu.chess_website_backend.entity.chess;

import com.alpergayretoglu.chess_website_backend.entity.BaseEntity;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.HashMap;
import java.util.Map;

@Entity
@Builder
@Getter
@NoArgsConstructor
public class ChessBoard extends BaseEntity {

    @ElementCollection(fetch = FetchType.EAGER)
    private final Map<ChessCoordinate, ChessPiece> chessPieces = new HashMap<>();

}
