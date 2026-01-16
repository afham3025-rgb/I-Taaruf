package com.mycompany.oop;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class DashboardView {
    private Stage stage;
    
    public DashboardView(Stage stage) {
        this.stage = stage;
    }
    
    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f4f8;");
        
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #2c5f2d;");
        topBar.setAlignment(Pos.CENTER_LEFT);
        
        Label titleLabel = new Label("I-Taaruf Dashboard");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: white;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label userLabel = new Label("Welcome, User");
        userLabel.setStyle("-fx-text-fill: white;");
        
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: white; -fx-text-fill: #2c5f2d;");
        logoutButton.setOnAction(e -> {
            LoginView loginView = new LoginView(stage);
            loginView.show();
        });
        
        topBar.getChildren().addAll(titleLabel, spacer, userLabel, logoutButton);
        
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(40));
        grid.setAlignment(Pos.CENTER);
        
        VBox profileCard = createFeatureCard("My Profile", 
            "View and edit your personal information", "#4a7c59");
        VBox matchCard = createFeatureCard("Match Requests", 
            "Send and manage match requests", "#2c5f2d");
        VBox messageCard = createFeatureCard("Messages", 
            "Communicate with approved matches", "#3a6b4d");
        VBox auditCard = createFeatureCard("System Logs", 
            "View audit logs and file management", "#5a8c69");
        
        profileCard.setOnMouseClicked(e -> {
            ProfileView profileView = new ProfileView(stage);
            profileView.show();
        });
        
        matchCard.setOnMouseClicked(e -> {
            MatchRequestView matchView = new MatchRequestView(stage);
            matchView.show();
        });
        
        messageCard.setOnMouseClicked(e -> {
            MessageView messageView = new MessageView(stage);
            messageView.show();
        });
        
        auditCard.setOnMouseClicked(e -> {
            AuditLogView auditView = new AuditLogView(stage);
            auditView.show();
        });
        
        grid.add(profileCard, 0, 0);
        grid.add(matchCard, 1, 0);
        grid.add(messageCard, 0, 1);
        grid.add(auditCard, 1, 1);
        
        root.setTop(topBar);
        root.setCenter(grid);
        
        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
    }
    
    private VBox createFeatureCard(String title, String description, String color) {
        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(30));
        card.setPrefSize(350, 200);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; " +
                     "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2); " +
                     "-fx-cursor: hand;");
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: " + color + ";");
        
        Label descLabel = new Label(description);
        descLabel.setWrapText(true);
        descLabel.setStyle("-fx-text-fill: #666; -fx-text-alignment: center;");
        descLabel.setMaxWidth(300);
        
        card.getChildren().addAll(titleLabel, descLabel);
        
        // Hover effect
        card.setOnMouseEntered(e -> 
            card.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 15; " +
                         "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2); " +
                         "-fx-cursor: hand;"));
        card.setOnMouseExited(e -> 
            card.setStyle("-fx-background-color: white; -fx-background-radius: 15; " +
                         "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2); " +
                         "-fx-cursor: hand;"));
        
        return card;
    }
}