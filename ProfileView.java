package com.mycompany.oop;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ProfileView {
    private Stage stage;

    public ProfileView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        // Main container with light grey/blue background
        VBox root = new VBox(20);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #f0f4f8;");

        // --- Header ---
        Label headerLabel = new Label("My Profile");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        headerLabel.setStyle("-fx-text-fill: #2c5f2d;");

        // --- The Profile Card (White box with shadow) ---
        VBox profileCard = new VBox(15);
        profileCard.setMaxWidth(500);
        profileCard.setPadding(new Insets(30));
        profileCard.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 15; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);"
        );

        // Define Input Fields
        Label bioLabel = new Label("Bio");
        bioLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        TextArea bioArea = new TextArea();
        bioArea.setPrefHeight(120);
        bioArea.setWrapText(true);
        bioArea.setPromptText("Tell us about your background and values...");

        Label interestLabel = new Label("Interests");
        interestLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        TextField interestField = new TextField();
        interestField.setPromptText("e.g., Islamic Studies, Volunteering, Cooking");

        Label ageLabel = new Label("Age");
        ageLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        TextField ageField = new TextField();
        
        // --- DATA LOADING ---
        String[] savedData = UserManager.loadProfile();
        if (savedData != null) {
            // Index 1=Bio, 2=Interests, 3=Age
            bioArea.setText(savedData[1]);
            interestField.setText(savedData[2]);
            ageField.setText(savedData[3]);
        }

        // --- Buttons ---
        Button saveButton = new Button("Save Profile");
        saveButton.setMaxWidth(Double.MAX_VALUE);
        saveButton.setStyle(
            "-fx-background-color: #2c5f2d; " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 12; " +
            "-fx-background-radius: 8;"
        );

        saveButton.setOnAction(e -> {
            String username = UserManager.getCurrentUser().getUsername();
            String bio = bioArea.getText();
            String interests = interestField.getText();
            String ageInput = ageField.getText();

            try {
                // Checklist #5: Age Validation
                int ageVal = Integer.parseInt(ageInput);
                if (ageVal < 18) {
                    showSimpleAlert(Alert.AlertType.ERROR, "Age Requirement", "Users must be 18 or older.");
                    return;
                }

                // Call the updated UserManager method
                UserManager.saveProfile(username, bio, interests, ageInput);
                showSimpleAlert(Alert.AlertType.INFORMATION, "Success", "Profile updated successfully!");

            } catch (NumberFormatException ex) {
                showSimpleAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a valid number for age.");
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #777;");
        backBtn.setOnAction(e -> new DashboardView(stage).show());

        // Assemble the card
        profileCard.getChildren().addAll(
            bioLabel, bioArea, 
            interestLabel, interestField, 
            ageLabel, ageField, 
            new Region() {{ setPrefHeight(10); }}, // Small spacer
            saveButton
        );

        root.getChildren().addAll(headerLabel, profileCard, backBtn);

        Scene scene = new Scene(root, 850, 700);
        stage.setScene(scene);
    }

    private void showSimpleAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}