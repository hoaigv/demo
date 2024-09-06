package com.example.bookshop.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class WebSocketChatMessage {
    String type;
    String content;
    String sender;
}
