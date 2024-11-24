/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Clone.ChatServer;

/**
 *
 * @author Meshack
 */
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

import java.util.*;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final Map<WebSocketSession, String> userSessions = new HashMap<>(); // Session to username mapping
    private final Set<String> onlineUsers = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Placeholder: Wait for username (first message should be the username)
        session.sendMessage(new TextMessage("Welcome! Please send your username as the first message."));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        // If the session doesn't have a username yet, treat the first message as the username
        if (!userSessions.containsKey(session)) {
            userSessions.put(session, payload);
            onlineUsers.add(payload);

            // Notify all clients about the updated user list
            broadcastUserList();
        } else {
            // Handle chat messages (broadcast to specific recipient, for example)
            String sender = userSessions.get(session);
            for (WebSocketSession s : userSessions.keySet()) {
                s.sendMessage(new TextMessage(sender + ": " + payload));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        String username = userSessions.remove(session);
        if (username != null) {
            onlineUsers.remove(username);
            broadcastUserList();
        }
    }

    private void broadcastUserList() throws Exception {
        String userListMessage = "USER_LIST:" + String.join(",", onlineUsers);
        for (WebSocketSession session : userSessions.keySet()) {
            session.sendMessage(new TextMessage(userListMessage));
        }
    }
}