package com.alpergayretoglu.chess_website_backend.entity;

import lombok.*;

import javax.persistence.Entity;
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

    @ManyToMany
    @Builder.Default
    private List<User> users = new ArrayList<>();

    @OneToMany
    @Builder.Default
    private List<Message> messages = new ArrayList<>();

    public void addMessage(Message message) {
        this.messages.add(message);
    }

}
