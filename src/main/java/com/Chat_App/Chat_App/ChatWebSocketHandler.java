package com.Chat_App.Chat_App;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        
        // Create the message to send
        String formattedMessage = String.format("message,%s,%s,%s",
            chatMessage.getSender(),
            chatMessage.getRecipient(),
            chatMessage.getContent()
        );

        // Send to recipient if online
        WebSocketSession recipientSession = sessions.get(chatMessage.getRecipient());
        if (recipientSession != null && recipientSession.isOpen()) {
            recipientSession.sendMessage(new TextMessage(formattedMessage));
        }
        
        // Send back to sender for confirmation
        session.sendMessage(new TextMessage(formattedMessage));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Extract username from session attributes or headers
        String username = extractUsername(session);
        sessions.put(username, session);
        
        // Broadcast user online status
        broadcastUserStatus(username, "ONLINE");
    }

    private String extractUsername(WebSocketSession session) {
        // You might want to implement your own logic to extract username
        return session.getId(); // temporary implementation
    }

    private void broadcastUserStatus(String username, String status) {
        String statusMessage = String.format("status,%s,%s", username, status);
        sessions.values().forEach(session -> {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(statusMessage));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}