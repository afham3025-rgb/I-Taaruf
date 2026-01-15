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

public class RegisterView {
    private Stage stage;
    
    public RegisterView(Stage stage) {
        this.stage = stage;
    }
    
    public void show() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f0f4f8;");
        
        Label titleLabel = new Label("Register Account");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);
        formBox.setMaxWidth(400);
        formBox.setPadding(new Insets(30));
        formBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("User", "Guardian");
        roleComboBox.setValue("User");
        roleComboBox.setPromptText("Select Role");
        roleComboBox.setMaxWidth(300);
        
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #2c5f2d; -fx-text-fill: white; -fx-padding: 10;");
        registerButton.setOnAction(e -> {
    String username = usernameField.getText();
    String password = passwordField.getText();
    String confirm = confirmPasswordField.getText();
    String role = roleComboBox.getValue();

    if (username.isEmpty() || password.isEmpty()) {
        showAlert("Error", "All fields are required");
        return;
    }

    if (!password.equals(confirm)) {
        showAlert("Error", "Passwords do not match");
        return;
    }

    boolean success = UserManager.register(username, password, role);

    if (success) {
        showAlert("Success", "Account created! You can now login.");
        new LoginView(stage).show();
    } else {
        showAlert("Error", "Username already exists");
    }
});
        
        Button backButton = new Button("Back to Login");
        backButton.setStyle("-fx-background-color: #666; -fx-text-fill: white; -fx-padding: 10;");
        backButton.setOnAction(e -> {
            LoginView loginView = new LoginView(stage);
            loginView.show();
        });
        
        formBox.getChildren().addAll(usernameField, passwordField, confirmPasswordField, 
                                     roleComboBox, registerButton, backButton);
        root.getChildren().addAll(titleLabel, formBox);
        
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
    }
    private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
}
}
