/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MessageView {
    private Stage stage;
    
    public MessageView(Stage stage) {
        this.stage = stage;
    }
    
    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f4f8;");
        
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #2c5f2d;");
        
        Label titleLabel = new Label("Messages");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: white;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button backButton = new Button("Back to Dashboard");
        backButton.setStyle("-fx-background-color: white; -fx-text-fill: #2c5f2d;");
        backButton.setOnAction(e -> {
            DashboardView dashboard = new DashboardView(stage);
            dashboard.show();
        });
        
        topBar.getChildren().addAll(titleLabel, spacer, backButton);
        
        SplitPane splitPane = new SplitPane();
        
        VBox conversationsBox = new VBox(10);
        conversationsBox.setPadding(new Insets(15));
        conversationsBox.setStyle("-fx-background-color: white;");
        
        Label conversationsLabel = new Label("Conversations");
        conversationsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        ListView<String> conversationsList = new ListView<>();
        conversationsList.setPrefWidth(250);
        conversationsList.getItems().addAll(
            "User001",
            "User002",
            "User003"
        );
        
        conversationsBox.getChildren().addAll(conversationsLabel, conversationsList);
        
        VBox messageBox = new VBox(15);
        messageBox.setPadding(new Insets(15));
        
        Label chatLabel = new Label("Select a conversation");
        chatLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        ListView<String> messagesList = new ListView<>();
        messagesList.setPrefHeight(400);
        messagesList.setStyle("-fx-background-color: #f9f9f9;");
        messagesList.getItems().addAll(
            "You: Hello, Assalamualaikum [10:30]",
            "User001: Waalaikumsalam [10:31]",
            "You: How are you? [10:32]"
        );
        
        HBox sendBox = new HBox(10);
        sendBox.setAlignment(Pos.CENTER);
        
        TextField messageField = new TextField();
        messageField.setPromptText("Type your message (max 500 chars)");
        HBox.setHgrow(messageField, Priority.ALWAYS);
        
        Label charCountLabel = new Label("0/500");
        charCountLabel.setStyle("-fx-text-fill: #666;");
        
        Button sendButton = new Button("Send");
        sendButton.setStyle("-fx-background-color: #2c5f2d; -fx-text-fill: white; -fx-padding: 10;");
        sendButton.setOnAction(e -> {
            messagesList.getItems().add("You: " + messageField.getText() + " [Now]");
            messageField.clear();
        });
        
        sendBox.getChildren().addAll(messageField, charCountLabel, sendButton);
        
        messageBox.getChildren().addAll(chatLabel, messagesList, sendBox);
        
        splitPane.getItems().addAll(conversationsBox, messageBox);
        splitPane.setDividerPositions(0.3);
        
        root.setTop(topBar);
        root.setCenter(splitPane);
        
        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
    }
}
