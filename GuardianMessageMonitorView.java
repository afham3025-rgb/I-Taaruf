package com.mycompany.oop;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GuardianMessageMonitorView {
    private Stage stage;
    private String guardianUsername;
    
    public GuardianMessageMonitorView(Stage stage, String guardianUsername) {
        this.stage = stage;
        this.guardianUsername = guardianUsername;
    }
    
    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f4f8;");
        
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #1a4d2e;");
        
        Label titleLabel = new Label("Message Monitor");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: white;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button backButton = new Button("Back to Dashboard");
        backButton.setStyle("-fx-background-color: white; -fx-text-fill: #1a4d2e;");
        backButton.setOnAction(e -> {
            new GuardianDashboardView(stage, guardianUsername).show();
        });
        
        topBar.getChildren().addAll(titleLabel, spacer, backButton);
        
        SplitPane splitPane = new SplitPane();
        
        // Left side - Ward conversations
        VBox conversationsBox = new VBox(10);
        conversationsBox.setPadding(new Insets(15));
        conversationsBox.setStyle("-fx-background-color: white;");
        
        Label wardLabel = new Label("Ward Conversations");
        wardLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        ComboBox<String> wardSelector = new ComboBox<>();
        wardSelector.getItems().addAll("Ali Hassan", "Fatima Khan", "Omar Abdullah");
        wardSelector.setPromptText("Select Ward");
        wardSelector.setMaxWidth(Double.MAX_VALUE);
        
        ListView<String> conversationsList = new ListView<>();
        conversationsList.setPrefWidth(250);
        conversationsList.getItems().addAll(
            "Ali Hassan ↔ Aisha Rahman",
            "Ali Hassan ↔ Maryam Noor",
            "Fatima Khan ↔ Yusuf Ibrahim"
        );
        
        wardSelector.setOnAction(e -> {
            String selected = wardSelector.getValue();
            if (selected != null) {
                conversationsList.getItems().clear();
                if (selected.equals("Ali Hassan")) {
                    conversationsList.getItems().addAll(
                        "Ali Hassan ↔ Aisha Rahman",
                        "Ali Hassan ↔ Maryam Noor"
                    );
                } else if (selected.equals("Fatima Khan")) {
                    conversationsList.getItems().addAll(
                        "Fatima Khan ↔ Yusuf Ibrahim",
                        "Fatima Khan ↔ Hassan Farooq"
                    );
                } else if (selected.equals("Omar Abdullah")) {
                    conversationsList.getItems().addAll(
                        "Omar Abdullah ↔ Khadija Aziz"
                    );
                }
            }
        });
        
        conversationsBox.getChildren().addAll(wardLabel, wardSelector, conversationsList);
        
        // Right side - Message view
        VBox messageBox = new VBox(15);
        messageBox.setPadding(new Insets(15));
        
        HBox headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        
        Label chatLabel = new Label("Select a conversation to monitor");
        chatLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Label statusLabel = new Label("🟢 Monitoring Active");
        statusLabel.setStyle("-fx-text-fill: #2d5f3e; -fx-font-weight: bold;");
        
        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        
        headerBox.getChildren().addAll(chatLabel, headerSpacer, statusLabel);
        
        // Message display area
        ListView<VBox> messagesList = new ListView<>();
        messagesList.setStyle("-fx-background-color: #f9f9f9;");
        messagesList.setPrefHeight(400);
        
        // Sample messages
        messagesList.getItems().addAll(
            createMessageBubble("Ali Hassan", "Assalamualaikum, how are you?", "10:30 AM", false),
            createMessageBubble("Aisha Rahman", "Waalaikumsalam, Alhamdulillah I'm well", "10:32 AM", true),
            createMessageBubble("Ali Hassan", "Would you like to know more about my family?", "10:35 AM", false),
            createMessageBubble("Aisha Rahman", "Yes, please. My guardian would also like to know", "10:37 AM", true)
        );
        
        conversationsList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                chatLabel.setText("Monitoring: " + newVal);
                AuditLogger.log("Guardian " + guardianUsername + " monitoring conversation: " + newVal);
            }
        });
        
        // Control panel
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setStyle("-fx-background-color: #fff9e6; -fx-border-color: #f0ad4e; -fx-border-width: 1;");
        
        Label controlLabel = new Label("Guardian Controls");
        controlLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        
        HBox controlButtons = new HBox(10);
        controlButtons.setAlignment(Pos.CENTER);
        
        Button flagButton = new Button("Flag Message");
        flagButton.setStyle("-fx-background-color: #f0ad4e; -fx-text-fill: white; -fx-padding: 8;");
        flagButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Flag Message");
            alert.setHeaderText("Flag inappropriate content?");
            alert.setContentText("This will mark the message for review and notify both parties.");
            
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    AuditLogger.log("Guardian " + guardianUsername + " flagged a message");
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setTitle("Success");
                    success.setContentText("Message flagged successfully");
                    success.showAndWait();
                }
            });
        });
        
        Button suspendButton = new Button("Suspend Chat");
        suspendButton.setStyle("-fx-background-color: #c44536; -fx-text-fill: white; -fx-padding: 8;");
        suspendButton.setOnAction(e -> {
            String selected = conversationsList.getSelectionModel().getSelectedItem();
            if (selected == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setContentText("Please select a conversation first");
                alert.showAndWait();
                return;
            }
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suspend Chat");
            alert.setHeaderText("Suspend this conversation?");
            alert.setContentText("This will temporarily block messaging between these users.");
            
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    AuditLogger.log("Guardian " + guardianUsername + " suspended chat: " + selected);
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setTitle("Success");
                    success.setContentText("Conversation suspended");
                    success.showAndWait();
                }
            });
        });
        
        Button exportButton = new Button("Export Chat");
        exportButton.setStyle("-fx-background-color: #4a8056; -fx-text-fill: white; -fx-padding: 8;");
        exportButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export Chat");
            alert.setContentText("Chat history would be exported to a file");
            alert.showAndWait();
        });
        
        controlButtons.getChildren().addAll(flagButton, suspendButton, exportButton);
        
        Label infoLabel = new Label("All conversations are monitored for safety and compliance with Shariah guidelines");
        infoLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #666;");
        infoLabel.setWrapText(true);
        
        controlPanel.getChildren().addAll(controlLabel, controlButtons, infoLabel);
        
        messageBox.getChildren().addAll(headerBox, messagesList, controlPanel);
        
        splitPane.getItems().addAll(conversationsBox, messageBox);
        splitPane.setDividerPositions(0.3);
        
        root.setTop(topBar);
        root.setCenter(splitPane);
        
        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
    }
    
    private VBox createMessageBubble(String sender, String message, String time, boolean isOther) {
        VBox bubble = new VBox(5);
        bubble.setPadding(new Insets(10));
        bubble.setMaxWidth(400);
        
        if (isOther) {
            bubble.setAlignment(Pos.CENTER_RIGHT);
            bubble.setStyle("-fx-background-color: #e8f5e9; -fx-background-radius: 10; -fx-border-color: #2d5f3e; -fx-border-radius: 10;");
        } else {
            bubble.setAlignment(Pos.CENTER_LEFT);
            bubble.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10; -fx-border-color: #999; -fx-border-radius: 10;");
        }
        
        Label senderLabel = new Label(sender);
        senderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        senderLabel.setStyle("-fx-text-fill: #333;");
        
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setStyle("-fx-font-size: 12px;");
        
        Label timeLabel = new Label(time);
        timeLabel.setStyle("-fx-font-size: 9px; -fx-text-fill: #666;");
        
        bubble.getChildren().addAll(senderLabel, messageLabel, timeLabel);
        
        return bubble;
    }
}

