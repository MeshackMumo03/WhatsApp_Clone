/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.chatApp;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ChatService chatService;

    @FXML
    private ListView<String> contactListView;
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;

    private Long currentChatId;

    @FXML
    public void initialize() {
        loadContacts();
        contactListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                createOrLoadChat(newValue);
            }
        });
    }

    public void loadContacts() {
        List<User> users = chatService.getAllUsers();
        contactListView.getItems().addAll(users.stream().map(User::getName).toList());
    }

    public void createOrLoadChat(String userName) {
        User user = chatService.getUserByName(userName);
        // Dummy current user ID, replace with actual logic
        Long currentUserId = 1L;
        Chat chat = chatService.createChat(currentUserId, user.getId());
        currentChatId = chat.getId();
        loadChatHistory(currentChatId);
    }

    public void loadChatHistory(Long chatId) {
        List<Message> messages = chatService.getChatHistory(chatId);
        chatArea.clear();
        for (Message message : messages) {
            chatArea.appendText(message.getTimestamp() + ": " + message.getContent() + "\n");
        }
    }

    @FXML
    public void handleSendMessage() {
        if (currentChatId != null) {
            String content = messageField.getText();
            chatService.sendMessage(currentChatId, content);
            loadChatHistory(currentChatId);
            messageField.clear();
        }
    }
}
