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

public class ProfileView {
    private Stage stage;
    
    public ProfileView(Stage stage) {
        this.stage = stage;
    }
    
    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f4f8;");
        
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #2c5f2d;");
        
        Label titleLabel = new Label("My Profile");
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
        
        VBox formBox = new VBox(20);
        formBox.setPadding(new Insets(30));
        formBox.setAlignment(Pos.TOP_CENTER);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setMaxWidth(600);
        grid.setPadding(new Insets(30));
        grid.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Enter full name");
        
        TextField ageField = new TextField();
        ageField.setPromptText("Must be 18 or above");
        
        ComboBox<String> genderCombo = new ComboBox<>();
        genderCombo.getItems().addAll("Male", "Female");
        genderCombo.setPromptText("Select gender");
        
        TextField educationField = new TextField();
        educationField.setPromptText("Your education level");
        
        TextField occupationField = new TextField();
        occupationField.setPromptText("Your occupation");
        
        TextField guardianField = new TextField();
        guardianField.setPromptText("Guardian contact (required)");
        
        TextArea biodataArea = new TextArea();
        biodataArea.setPromptText("Tell us about yourself...");
        biodataArea.setPrefRowCount(4);
        
        int row = 0;
        grid.add(new Label("Full Name:"), 0, row);
        grid.add(fullNameField, 1, row++);
        
        grid.add(new Label("Age:"), 0, row);
        grid.add(ageField, 1, row++);
        
        grid.add(new Label("Gender:"), 0, row);
        grid.add(genderCombo, 1, row++);
        
        grid.add(new Label("Education:"), 0, row);
        grid.add(educationField, 1, row++);
        
        grid.add(new Label("Occupation:"), 0, row);
        grid.add(occupationField, 1, row++);
        
        grid.add(new Label("Guardian Contact:"), 0, row);
        grid.add(guardianField, 1, row++);
        
        grid.add(new Label("Biodata:"), 0, row);
        grid.add(biodataArea, 1, row++);
        
        Button saveButton = new Button("Save Profile");
        saveButton.setStyle("-fx-background-color: #2c5f2d; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10;");
        saveButton.setOnAction(e -> {

    String fullName = fullNameField.getText();
    String age = ageField.getText();
    String gender = genderCombo.getValue();
    String education = educationField.getText();
    String occupation = occupationField.getText();
    String guardian = guardianField.getText();
    String bio = biodataArea.getText();

    if (fullName.isEmpty() || age.isEmpty() || gender == null || guardian.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Please fill in all required fields.");
        alert.showAndWait();
        return;
    }

    FileHandler fileHandler = new FileHandler("profiles.txt");

    String profileData = String.join(",",
            fullName,
            age,
            gender,
            education,
            occupation,
            guardian,
            bio.replace(",", " ") // prevent CSV break
    );

    fileHandler.writeLine(profileData);

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setContentText("Profile saved successfully!");
    alert.showAndWait();
});
        
        grid.add(saveButton, 1, row);
        
        formBox.getChildren().add(grid);
        root.setTop(topBar);
        root.setCenter(formBox);
        
        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
    }
}