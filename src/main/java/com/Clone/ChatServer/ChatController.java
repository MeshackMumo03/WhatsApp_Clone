/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Clone.ChatServer;

/**
 *
 * @author Meshack
 */
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final List<Message> messages = new ArrayList<>();

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody Message message) {
        messages.add(message);
        return ResponseEntity.ok("Message sent");
    }

    @GetMapping("/history")
    public ResponseEntity<List<Message>> getChatHistory() {
        return ResponseEntity.ok(messages);
    }
}

class Message {
    private String sender;
    private String recipient;
    private String content;

    // Getters and Setters
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}