package com.mycompany.oop;

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
        // === CRITICAL DEBUG - Check what users are loaded ===
        System.out.println("\n=== USER LIST DEBUG ===");
        System.out.println("Current User: " + UserManager.getCurrentUser().getUsername());
        System.out.println("Total Users in System: " + UserManager.getAllUsers().size());
        
        for (User u : UserManager.getAllUsers()) {
            System.out.println("  - Username: '" + u.getUsername() + "'");
            System.out.println("    Role: " + u.getRole());
            if (u instanceof Ward) {
                System.out.println("    Guardian: " + ((Ward) u).getGuardianName());
            }
        }
        System.out.println("=== END DEBUG ===\n");
        
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f4f8;");

        // --- TOP BAR ---
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15, 25, 15, 25));
        topBar.setStyle("-fx-background-color: #2c5f2d;");
        topBar.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label("Matchmaking Discovery");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titleLabel.setStyle("-fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button backButton = new Button("Back to Dashboard");
        backButton.setStyle("-fx-background-color: white; -fx-text-fill: #2c5f2d;");
        backButton.setOnAction(e -> new DashboardView(stage).show());

        topBar.getChildren().addAll(titleLabel, spacer, backButton);
        root.setTop(topBar);

        // --- MAIN CONTENT ---
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(25));
        mainContent.setAlignment(Pos.TOP_CENTER);

        Label sectionTitle = new Label("Available Profiles");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // This container holds all the user cards
        VBox userListContainer = new VBox(15); 
        userListContainer.setPadding(new Insets(10));
        
        User current = UserManager.getCurrentUser();
        int cardCount = 0;
        
        // Loop through every user in the system
        if (UserManager.getAllUsers() != null) {
            for (User u : UserManager.getAllUsers()) {
                System.out.println("Checking user: " + u.getUsername() + " | Role: " + u.getRole());
                
                // Only show other Wards (don't show yourself or Guardians)
                if (!u.getUsername().equals(current.getUsername()) && "Ward".equalsIgnoreCase(u.getRole())) {
                    System.out.println("  -> ADDING CARD for: " + u.getUsername());
                    userListContainer.getChildren().add(createUserCard(u));
                    cardCount++;
                } else {
                    System.out.println("  -> SKIPPED (same user or not Ward)");
                }
            }
        }
        
        System.out.println("Total cards created: " + cardCount);
        
        // If no users found, show a message
        if (cardCount == 0) {
            Label noUsersLabel = new Label("No other users available at this time.");
            noUsersLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");
            userListContainer.getChildren().add(noUsersLabel);
        }

        // Wrap the list in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(userListContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(500);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        mainContent.getChildren().addAll(sectionTitle, scrollPane);
        root.setCenter(mainContent);

        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
    }

    private HBox createUserCard(User u) {
        System.out.println("Creating card for: " + u.getUsername());
        
        HBox card = new HBox(20);
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2);");

        VBox info = new VBox(5);
        
        // THE USERNAME LABEL - This should be the ward's name
        Label nameLabel = new Label(u.getUsername()); 
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        nameLabel.setStyle("-fx-text-fill: #000000;"); // Explicit black color
        
        // The guardian info
        String guardianName = "No Guardian assigned";
        if (u instanceof Ward) {
            guardianName = "Guardian: " + ((Ward) u).getGuardianName();
        }
        
        Label subLabel = new Label(guardianName);
        subLabel.setStyle("-fx-text-fill: #777; -fx-font-size: 13px;");
        
        info.getChildren().addAll(nameLabel, subLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button matchBtn = new Button("Send Request");
        matchBtn.setStyle("-fx-background-color: #2c5f2d; -fx-text-fill: white; -fx-padding: 10 20;");
        matchBtn.setOnAction(e -> {
            UserManager.sendMatchRequest(u.getUsername());
            matchBtn.setText("Request Sent");
            matchBtn.setDisable(true);
            AuditLogger.log("Match request from " + UserManager.getCurrentUser().getUsername() + " to " + u.getUsername());
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Match request sent to " + u.getUsername());
            alert.showAndWait();
        });

        card.getChildren().addAll(info, spacer, matchBtn);
        return card;
    }
}
