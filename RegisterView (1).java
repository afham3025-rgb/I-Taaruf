package com.mycompany.oop;
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
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #f0f4f8;");

        Label titleLabel = new Label("Create New Account");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #2c5f2d;");

        VBox formBox = new VBox(10);
        formBox.setMaxWidth(350);
        formBox.setPadding(new Insets(20));
        formBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        // Input Fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // Checklist #5: Age Input for Validation
        TextField ageField = new TextField();
        ageField.setPromptText("Age (Must be 18+)");

        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("Ward", "Guardian");
        roleBox.setPromptText("Select Role");
        roleBox.setMaxWidth(Double.MAX_VALUE);

        // Checklist #4: Guardian Link Field
        TextField guardianField = new TextField();
        guardianField.setPromptText("Guardian Name (Leave empty if Guardian)");
        guardianField.setVisible(false); // Hidden by default

        // Show/Hide Guardian field based on role selection
        roleBox.setOnAction(e -> {
            guardianField.setVisible("Ward".equals(roleBox.getValue()));
        });

        Button registerButton = new Button("Register");
        registerButton.setMaxWidth(Double.MAX_VALUE);
        registerButton.setStyle("-fx-background-color: #2c5f2d; -fx-text-fill: white; -fx-font-weight: bold;");

        registerButton.setOnAction(e -> {
            String user = usernameField.getText();
            String pass = passwordField.getText();
            String role = roleBox.getValue();
            String guard = guardianField.getText();
            String ageStr = ageField.getText();

            // Validation logic
            if (user.isEmpty() || pass.isEmpty() || role == null || ageStr.isEmpty()) {
                showAlert("Error", "Please fill in all required fields.");
                return;
            }
            
            if (UserManager.register(user, pass, role, guard, ageStr)) {
                showAlert("Success", "Account created for " + user);
                new LoginView(stage).show();
            } else {
                showAlert("Registration Failed", "Username might be taken or an error occurred.");
            }

            try {
                int age = Integer.parseInt(ageStr);
                // Checklist #5: Age Validation
                if (age < 18) {
                    showAlert("Access Denied", "You must be 18 or older to register.");
                    return;
                }

                // Call the updated UserManager.register with the Age parameter
                if (UserManager.register(user, pass, role, guard, ageStr)) {
                    showAlert("Success", "Account created successfully! Your profile has been initialized.");
                    new LoginView(stage).show();
                } else {
                    showAlert("Error", "Username already exists.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "Please enter a valid number for age.");
            }
        });

        Button backButton = new Button("Back to Login");
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #666;");
        backButton.setOnAction(e -> new LoginView(stage).show());

        formBox.getChildren().addAll(
            new Label("Username"), usernameField,
            new Label("Password"), passwordField,
            new Label("Age"), ageField,
            new Label("Role"), roleBox,
            guardianField, 
            registerButton
        );

        root.getChildren().addAll(titleLabel, formBox, backButton);

        Scene scene = new Scene(root, 800, 650);
        stage.setScene(scene);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}