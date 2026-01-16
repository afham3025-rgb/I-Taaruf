package com.mycompany.oop;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;

public class UserListView {
    private Stage stage;

    public UserListView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f4f8;");

        // --- TOP HEADER ---
        HBox header = new HBox();
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #2c5f2d;");
        Label title = new Label("Find Your Match");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        title.setStyle("-fx-text-fill: white;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button backBtn = new Button("Dashboard");
        backBtn.setOnAction(e -> new DashboardView(stage).show());
        
        header.getChildren().addAll(title, spacer, backBtn);
        root.setTop(header);

        // --- SCROLLABLE LIST CONTENT ---
        VBox listContainer = new VBox(15);
        listContainer.setPadding(new Insets(20));
        listContainer.setAlignment(Pos.TOP_CENTER);

        // Checklist #2: Populate from real data
        ArrayList<User> allUsers = UserManager.getAllUsers(); // We need to add this method to UserManager
        User current = UserManager.getCurrentUser();

        for (User u : allUsers) {
            // Filter: Don't show yourself or other Guardians (Wards match with Wards)
            if (!u.getUsername().equals(current.getUsername()) && u.getRole().equals("Ward")) {
                listContainer.getChildren().add(createUserCard(u));
            }
        }

        ScrollPane scrollPane = new ScrollPane(listContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f0f4f8; -fx-border-color: transparent;");
        
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 800, 650);
        stage.setScene(scene);
    }

    // Helper to create a "Card" for each user
    private HBox createUserCard(User u) {
        HBox card = new HBox(20);
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 1);");

        VBox info = new VBox(5);
        Label nameLabel = new Label(u.getUsername());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Label roleLabel = new Label("Role: " + u.getRole());
        roleLabel.setStyle("-fx-text-fill: #666;");
        
        info.getChildren().addAll(nameLabel, roleLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button requestBtn = new Button("Send Match Request");
        requestBtn.setStyle("-fx-background-color: #2c5f2d; -fx-text-fill: white;");
        requestBtn.setOnAction(e -> {
            // Checklist #3 logic will go here
            System.out.println("Request sent to: " + u.getUsername());
            requestBtn.setText("Request Sent!");
            requestBtn.setDisable(true);
        });

        card.getChildren().addAll(info, spacer, requestBtn);
        return card;
    }
}