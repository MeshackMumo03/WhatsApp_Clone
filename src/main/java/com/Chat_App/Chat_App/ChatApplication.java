package com.Chat_App.Chat_App;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ComponentScan(basePackages = "com.Chat_App.Chat_App")
public class ChatApplication extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = new SpringApplicationBuilder(ChatApplication.class)
            .headless(false)
            .run();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Get any beans you need from the context
            primaryStage.setTitle("Chat Application");
            // Initialize your UI components here
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        // This will call init() and start()
        launch(args);
    }
}