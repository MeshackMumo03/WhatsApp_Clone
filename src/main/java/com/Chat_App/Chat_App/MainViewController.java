/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Chat_App.Chat_App;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

/**
 *
 * @author Meshack
 */
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

public class MainViewController {
    private final ChatService chatService;
    private User currentUser;
    private WebSocketStompClient stompClient;

    @FXML
    private ListView<User> onlineUsersList;
    @FXML
    private ListView<Contact> contactList;
    @FXML
    private TextArea chatHistory;
    @FXML
    private TextField messageInput;
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField passwordInput;

    public MainViewController(ChatService chatService) {
        this.chatService = chatService;
    }

    @FXML
    public void initialize() {
        connectWebSocket();
    }

    private void connectWebSocket() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);
        stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new StringMessageConverter());

        stompClient.connect("ws://localhost:8080/chat", new StompSessionHandler() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                // Handle WebSocket connection establishment
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                // Handle WebSocket connection exceptions
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
                // Handle WebSocket transport errors
            }

            @Override
            public Type getPayloadType(StompHeaders headers) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    @FXML
    public void onLogin() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        sendWebSocketMessage("login," + username + "," + password);
        currentUser = chatService.getUser(username);
        loadContacts();
        loadOnlineUsers();
    }

    @FXML
    public void onLogout() {
        sendWebSocketMessage("logout," + currentUser.getUsername() + ",");
        updateUserStatus(currentUser.getUsername(), "Offline");
        currentUser = null;
    }

    @FXML
    public void onSendMessage() {
        String recipient = onlineUsersList.getSelectionModel().getSelectedItem().getUsername();
        String content = messageInput.getText();
        sendWebSocketMessage("message," + currentUser.getUsername() + "," + recipient + "," + content);
        messageInput.clear();
    }

    private void sendWebSocketMessage(String message) {
        stompClientexecute("/app/chat", message.getBytes());
    }

    // Other methods remain the same

    private void updateUserStatus(String username, String offline) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void loadOnlineUsers() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void loadContacts() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void stompClientexecute(String appchat, byte[] bytes) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}