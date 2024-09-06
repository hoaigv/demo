//package com.example.bookshop.controller.web;
//
//import com.example.bookshop.dto.WebSocketChatMessage;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Objects;
//
//public class WebSocketChatController {
//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/demo_websocket")
//    public WebSocketChatMessage sendMessage(@Payload WebSocketChatMessage message) {
//        return message;
//    }
//
//    @MessageMapping("/chat.newUser")
//    @SendTo("/topic/demo_websocket")
//    public WebSocketChatMessage newUser(@Payload WebSocketChatMessage message
//      , SimpMessageHeaderAccessor headerAccessor
//    ) {
//            Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", message.getSender());
//            return message;
//    }
//
//
//}
