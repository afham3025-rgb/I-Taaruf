package com.mycompany.oop;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class LoginView {
    private Stage stage;
    
    public LoginView(Stage stage) {
        this.stage = stage;
    }
    
    public void show() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setStyle("-fx-background-color: #f0f4f8;");
        
        // Checklist #6: Logo Implementation
        VBox logoBox = new VBox(10);
        logoBox.setAlignment(Pos.CENTER);
        ImageView logoView = createPlaceholderLogo();
        logoBox.getChildren().add(logoView);
        
        Label titleLabel = new Label("I-Taaruf");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setStyle("-fx-text-fill: #2c5f2d;");
        
        Label subtitleLabel = new Label("Shariah-Compliant Matchmaking");
        subtitleLabel.setStyle("-fx-text-fill: #666;");
        
        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);
        formBox.setMaxWidth(350);
        formBox.setPadding(new Insets(30));
        formBox.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefHeight(40);
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefHeight(40);
        
        Button loginButton = new Button("Login");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setPrefHeight(40);
        loginButton.setStyle("-fx-background-color: #2c5f2d; -fx-text-fill: white; -fx-font-weight: bold;");
        
        // Checklist #1: Role-Based Dashboard Redirection
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            if (UserManager.login(username, password)) {
                // Check if user is a Guardian or a Ward
                if (UserManager.isGuardian()) {
                    // Send to Guardian Dashboard
                    new GuardianDashboardView(stage, username).show();
                } else {
                    // Send to Standard Dashboard
                    new DashboardView(stage).show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password. Please try again.");
                alert.showAndWait();
            }
        });
        
        Button registerButton = new Button("Don't have an account? Register here");
        registerButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #2c5f2d; -fx-underline: true;");
        registerButton.setOnAction(e -> new RegisterView(stage).show());
        
        formBox.getChildren().addAll(usernameField, passwordField, loginButton, registerButton);
        root.getChildren().addAll(logoBox, titleLabel, subtitleLabel, formBox);
        
        Scene scene = new Scene(root, 800, 650);
        stage.setScene(scene);
        stage.show();
    }
    
    // Checklist #6 Helper
    private ImageView createPlaceholderLogo() {
        StackPane logoStack = new StackPane();
        Circle outerCircle = new Circle(50, Color.web("#2c5f2d"));
        Label logoText = new Label("IT");
        logoText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        logoText.setTextFill(Color.WHITE);
        logoStack.getChildren().addAll(outerCircle, logoText);
        
        javafx.scene.image.WritableImage image = logoStack.snapshot(null, null);
        return new ImageView(image);
    }
}
