package com.Clone.ChatClient;

import jakarta.websocket.*;
import java.net.URI;
import java.util.Arrays;

@ClientEndpoint
public class ChatClient {
    private Session session;
    private ChatController controller;

    public void connectToServer(String uri) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to server");
    }

    @OnMessage
    public void onMessage(String message) {
        if (message.startsWith("USER_LIST:")) {
            String[] users = message.substring(10).split(",");
            javafx.application.Platform.runLater(() -> controller.updateContactList(Arrays.asList(users)));
        } else {
            javafx.application.Platform.runLater(() -> controller.receiveMessage(message));
        }
    }

    public void sendUsername(String username) {
        try {
            if (session != null && session.isOpen()) {
                session.getBasicRemote().sendText(username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            if (session != null && session.isOpen()) {
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        this.session = null;
        System.out.println("Disconnected from server: " + reason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket error occurred: " + throwable.getMessage());
        throwable.printStackTrace();
    }

    public void setController(ChatController controller) {
        this.controller = controller;
    }
}
