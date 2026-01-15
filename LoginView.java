
package com.mycompany.project;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginView {
    private Stage stage;
    
    public LoginView(Stage stage) {
        this.stage = stage;
    }
    
    public void show() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f0f4f8;");
        
        // Title
        Label titleLabel = new Label("I-Taaruf");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setStyle("-fx-text-fill: #2c5f2d;");
        
        Label subtitleLabel = new Label("Shariah-Compliant Matchmaking System");
        subtitleLabel.setFont(Font.font("Arial", 14));
        subtitleLabel.setStyle("-fx-text-fill: #555;");
        
        // Login form box
        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);
        formBox.setMaxWidth(400);
        formBox.setPadding(new Insets(30));
        formBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        
        Button loginButton = new Button("Login");
        loginButton.setMaxWidth(300);
        loginButton.setStyle("-fx-background-color: #2c5f2d; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10;");
        
        Button registerButton = new Button("Register New Account");
        registerButton.setMaxWidth(300);
        registerButton.setStyle("-fx-background-color: #4a7c59; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10;");
        
        // Demo: Button clicks navigate to other views
        loginButton.setOnAction(e -> {
    String username = usernameField.getText();
    String password = passwordField.getText();

    boolean success = UserManager.login(username, password);

    if (success) {
        DashboardView dashboard = new DashboardView(stage);
        dashboard.show();
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setContentText("Invalid username or password");
        alert.showAndWait();
    }
});
        
        registerButton.setOnAction(e -> {
            RegisterView registerView = new RegisterView(stage);
            registerView.show();
        });
        
        formBox.getChildren().addAll(usernameField, passwordField, loginButton, registerButton);
        root.getChildren().addAll(titleLabel, subtitleLabel, formBox);
        
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}

