package com.alpergayretoglu.chess_website_backend.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class Message extends BaseEntity {

    @ManyToOne
    private User from;

    private String text;

}
