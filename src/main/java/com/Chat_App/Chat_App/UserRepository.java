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
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByOnlineTrue();
}