/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Chat_App.Chat_App;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Meshack
 */
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByParticipant1OrParticipant2(User user1, User user2);
}
