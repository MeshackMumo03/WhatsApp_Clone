/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.Clone.ChatClient;

/**
 *
 * @author Meshack
 */
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class LoginController {
    @FXML private TextField usernameField;
    @FXML private Button loginButton;

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> login());
    }

    private void login() {
        String username = usernameField.getText().trim();
        if (!username.isEmpty()) {
            try {
                // Load the main chat interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(loader.load()));

                // Pass the username to ChatController
                ChatController controller = loader.getController();
                controller.setUsername(username);
                stage.setTitle("Chat - " + username);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
