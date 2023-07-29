package com.alpergayretoglu.chess_website_backend.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class Chat extends BaseEntity {

    // TODO: Remove fetch type eager after you find a better way
    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "chat")
    @Builder.Default
    private List<Message> messages = new ArrayList<>();

}
