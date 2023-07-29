package com.alpergayretoglu.chess_website_backend.model.request.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateChatRequest {

    private List<String> userIds;

}
