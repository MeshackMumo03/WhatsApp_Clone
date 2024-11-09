/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Chat_App.Chat_App;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.messaging.Message;


/**
 *
 * @author Meshack
 */
@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository, MessageRepository messageRepository, ContactRepository contactRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    public User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setStatus("Offline");
        user.setOnline(false);
        return userRepository.save(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getOnlineUsers() {
        return userRepository.findByOnlineTrue();
    }

    public void updateUserStatus(User user, String status, boolean online) {
        user.setStatus(status);
        user.setOnline(online);
        userRepository.save(user);
    }

    public Contact createContact(String name, String status) {
        Contact contact = new Contact();
        contact.setName(name);
        contact.setStatus(status);
        return contactRepository.save(contact);
    }

    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }

    public Chat createChat(User participant1, User participant2) {
        Chat chat = new Chat();
        chat.setParticipant1(participant1);
        chat.setParticipant2(participant2);
        return chatRepository.save(chat);
    }

    public List<Message> getChatHistory(Chat chat) {
        return chat.getMessages();
    }

    public void sendMessage(Message message) {
        messageRepository.save(message);
    }
}