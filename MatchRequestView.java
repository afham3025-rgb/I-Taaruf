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

public class MatchRequestView {
    private Stage stage;
    
    public MatchRequestView(Stage stage) {
        this.stage = stage;
    }
    
    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f4f8;");
        
        // Top bar
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #2c5f2d;");
        
        Label titleLabel = new Label("Match Requests");
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
        
        // Main content
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setAlignment(Pos.TOP_CENTER);
        
        // Send new request section
        VBox sendRequestBox = new VBox(15);
        sendRequestBox.setMaxWidth(600);
        sendRequestBox.setPadding(new Insets(20));
        sendRequestBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        Label sendLabel = new Label("Send New Match Request");
        sendLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        TextField receiverIdField = new TextField();
        receiverIdField.setPromptText("Enter Name");
        
        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Enter your message (optional)");
        messageArea.setPrefRowCount(3);
        
        Button sendButton = new Button("Send Request");
        sendButton.setStyle("-fx-background-color: #2c5f2d; -fx-text-fill: white; -fx-padding: 10;");
        sendButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Request Sent");
            alert.setContentText("Match request sent successfully!");
            alert.showAndWait();
        });
        
        sendRequestBox.getChildren().addAll(sendLabel, receiverIdField, messageArea, sendButton);
        
        // View requests section
        VBox viewRequestsBox = new VBox(15);
        viewRequestsBox.setMaxWidth(600);
        viewRequestsBox.setPadding(new Insets(20));
        viewRequestsBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        Label viewLabel = new Label("Received Requests");
        viewLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        ListView<String> requestsList = new ListView<>();
        requestsList.setPrefHeight(200);
        requestsList.getItems().addAll(
            "From: User001 | Status: Pending",
            "From: User002 | Status: Pending",
            "From: User003 | Status: Accepted"
        );
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button acceptButton = new Button("Accept Selected");
        acceptButton.setStyle("-fx-background-color: #4a7c59; -fx-text-fill: white; -fx-padding: 10;");
        
        Button rejectButton = new Button("Reject Selected");
        rejectButton.setStyle("-fx-background-color: #c44536; -fx-text-fill: white; -fx-padding: 10;");
        
        buttonBox.getChildren().addAll(acceptButton, rejectButton);
        
        viewRequestsBox.getChildren().addAll(viewLabel, requestsList, buttonBox);
        
        content.getChildren().addAll(sendRequestBox, viewRequestsBox);
        
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        
        root.setTop(topBar);
        root.setCenter(scrollPane);
        
        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
    }
}