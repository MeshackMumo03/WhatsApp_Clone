package com.Clone.ChatClient;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatController {
    private String username;
    private final ChatClient client = new ChatClient();

    @FXML private Circle profilePic;
    @FXML private TextField searchField;
    @FXML private ListView<String> contactListView;
    @FXML private VBox welcomeScreen;
    @FXML private VBox chatView;
    @FXML private Circle contactProfilePic;
    @FXML private Label contactNameLabel;
    @FXML private Label contactStatusLabel;
    @FXML private TextArea chatArea;
    @FXML private TextField messageField;
    @FXML private Button sendButton;

    private String activeContact;

    // Map to store chats for each contact
    private final Map<String, List<String>> chatHistory = new HashMap<>();

    @FXML
    public void initialize() {
        setupListeners();
    }

    public void setUsername(String username) {
        this.username = username;
        client.setController(this);

        // Use server discovery to get the IP
        String serverIp = getServerIP();
        String serverAddress = "ws://" + serverIp + ":8080/ws";
        System.out.println("Connecting to server at: " + serverAddress);
        client.connectToServer(serverAddress);

        // Send username to the server
        client.sendUsername(username);
    }

    public void updateContactList(List<String> users) {
        List<String> modifiableUsers = new ArrayList<>(users);
        modifiableUsers.remove(username);
        contactListView.getItems().setAll(modifiableUsers);
    }

    private void setupListeners() {
        sendButton.setOnAction(event -> sendMessage());
        messageField.setOnAction(event -> sendMessage());
        contactListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showChatView(newSelection);
            }
        });
    }

    private void showChatView(String contactName) {
        activeContact = contactName;
        welcomeScreen.setVisible(false);
        chatView.setVisible(true);
        contactNameLabel.setText(contactName);
        contactStatusLabel.setText("Online");

        // Display previous chat history
        chatArea.clear();
        List<String> messages = chatHistory.getOrDefault(contactName, new ArrayList<>());
        for (String message : messages) {
            chatArea.appendText(message + "\n");
        }
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty() && activeContact != null) {
            String formattedMessage = "You: " + message;

            // Store message in chat history
            chatHistory.computeIfAbsent(activeContact, k -> new ArrayList<>()).add(formattedMessage);

            // Send message to the server
            client.sendMessage(message);

            // Display message in the chat area
            chatArea.appendText(formattedMessage + "\n");
            messageField.clear();
        }
    }

    public void receiveMessage(String message) {
        // Extract sender and content
        String[] parts = message.split(": ", 2);
        if (parts.length == 2) {
            String sender = parts[0].split(" ")[0]; // Extract the sender
            String content = parts[1]; // Extract the message content

            String formattedMessage = sender + ": " + content;

            // Store the received message in chat history
            chatHistory.computeIfAbsent(sender, k -> new ArrayList<>()).add(formattedMessage);

            // Display message if the sender is the active contact
            if (sender.equals(activeContact)) {
                chatArea.appendText(formattedMessage + "\n");
            }
        }
    }

    private String getServerIP() {
        try (DatagramSocket socket = new DatagramSocket(8888)) {
            socket.setSoTimeout(5000); // Timeout after 5 seconds
            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            System.out.println("Listening for server broadcast...");
            socket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength());
            if (message.startsWith("ServerIP:")) {
                return message.substring(9); // Extract IP from message
            }
        } catch (Exception e) {
            System.err.println("Server discovery failed: " + e.getMessage());
        }
        return "127.0.0.1"; // Fallback to localhost if discovery fails
    }
}
