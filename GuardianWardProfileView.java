package com.mycompany.oop;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GuardianWardProfileView {
    private Stage stage;
    private String guardianUsername;
    
    public GuardianWardProfileView(Stage stage, String guardianUsername) {
        this.stage = stage;
        this.guardianUsername = guardianUsername;
    }
    
    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f4f8;");
        
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #1a4d2e;");
        
        Label titleLabel = new Label("Ward Profiles");
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
        
        // Ward list on the left
        VBox wardListBox = new VBox(10);
        wardListBox.setPadding(new Insets(15));
        wardListBox.setStyle("-fx-background-color: white;");
        
        Label wardListLabel = new Label("My Wards");
        wardListLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        ListView<String> wardList = new ListView<>();
        wardList.setPrefWidth(200);
        wardList.getItems().addAll(
            "Ward: Ali Hassan",
            "Ward: Fatima Khan",
            "Ward: Omar Abdullah"
        );
        
        Button addWardButton = new Button("Add New Ward");
        addWardButton.setStyle("-fx-background-color: #1a4d2e; -fx-text-fill: white; -fx-padding: 8;");
        addWardButton.setMaxWidth(Double.MAX_VALUE);
        addWardButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Ward");
            dialog.setHeaderText("Add New Ward");
            dialog.setContentText("Enter ward's username:");
            
            dialog.showAndWait().ifPresent(wardUsername -> {
                wardList.getItems().add("Ward: " + wardUsername);
                AuditLogger.log("Guardian " + guardianUsername + " added ward: " + wardUsername);
            });
        });
        
        wardListBox.getChildren().addAll(wardListLabel, wardList, addWardButton);
        
        // Ward details on the right
        VBox detailsBox = new VBox(15);
        detailsBox.setPadding(new Insets(20));
        
        Label detailsLabel = new Label("Ward Profile Details");
        detailsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        TextArea profileDetailsArea = new TextArea();
        profileDetailsArea.setEditable(false);
        profileDetailsArea.setPrefRowCount(15);
        profileDetailsArea.setWrapText(true);
        profileDetailsArea.setText("Select a ward to view their profile details...");
        
        Button viewFullProfileButton = new Button("View Full Profile");
        viewFullProfileButton.setStyle("-fx-background-color: #1a4d2e; -fx-text-fill: white; -fx-padding: 10;");
        viewFullProfileButton.setDisable(true);
        
        Button editProfileButton = new Button("Edit Profile");
        editProfileButton.setStyle("-fx-background-color: #2d5f3e; -fx-text-fill: white; -fx-padding: 10;");
        editProfileButton.setDisable(true);
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(viewFullProfileButton, editProfileButton);
        
        wardList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                String wardName = newVal.replace("Ward: ", "");
                profileDetailsArea.setText(
                    "Ward Name: " + wardName + "\n\n" +
                    "Age: 24 years\n" +
                    "Gender: Male\n" +
                    "Education: Bachelor's Degree\n" +
                    "Occupation: Software Engineer\n" +
                    "Guardian: " + guardianUsername + "\n\n" +
                    "Bio: Looking for a righteous partner who values faith and family.\n\n" +
                    "Status: Active\n" +
                    "Privacy: Guardian-Monitored"
                );
                viewFullProfileButton.setDisable(false);
                editProfileButton.setDisable(false);
            }
        });
        
        editProfileButton.setOnAction(e -> {
            String selected = wardList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit Profile");
                alert.setHeaderText("Edit Ward Profile");
                alert.setContentText("Profile editing interface would open here.");
                alert.showAndWait();
            }
        });
        
        detailsBox.getChildren().addAll(detailsLabel, profileDetailsArea, buttonBox);
        
        splitPane.getItems().addAll(wardListBox, detailsBox);
        splitPane.setDividerPositions(0.25);
        
        root.setTop(topBar);
        root.setCenter(splitPane);
        
        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
    }
}
