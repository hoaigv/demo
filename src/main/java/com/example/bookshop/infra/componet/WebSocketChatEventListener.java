//package com.example.bookshop.infra.componet;
//
//
//import com.example.bookshop.dto.WebSocketChatMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionConnectedEvent;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//import java.util.Objects;
//
//@Component
//public class WebSocketChatEventListener {
//    @Autowired
//    private SimpMessageSendingOperations messagingTemplate;
//
//    @EventListener
//    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
//        System.out.println("Connected to websocket");
//    }
//
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
//        if (username != null) {
//            WebSocketChatMessage chatMessage = new WebSocketChatMessage();
//            chatMessage.setType("leave");
//            chatMessage.setSender(username);
//
//            messagingTemplate.convertAndSend("/topic/leave", chatMessage);
//
//        }
//    }
//
//}
