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

public class GuardianDashboardView {
    private Stage stage;
    private String guardianName;

    public GuardianDashboardView(Stage stage, String guardianName) {
        this.stage = stage;
        this.guardianName = guardianName;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f4f7f6;");

        // --- TOP NAVIGATION ---
        HBox topNav = new HBox();
        topNav.setPadding(new Insets(20));
        topNav.setStyle("-fx-background-color: #1a3c34;"); // Dark Shariah Green
        
        Label title = new Label("Guardian Control Panel: " + guardianName);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: white;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> new LoginView(stage).show());
        
        topNav.getChildren().addAll(title, spacer, logoutBtn);
        root.setTop(topNav);

        // --- MAIN CONTENT ---
        VBox mainContent = new VBox(25);
        mainContent.setPadding(new Insets(30));
        
        Label welcomeLabel = new Label("Monitoring Activity for Your Wards");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // --- ACTION CARDS SECTION ---
        HBox actionCards = new HBox(20);
        actionCards.getChildren().addAll(
            createActionCard("View Ward Profile", "Check and edit your ward's public info."),
            createActionCard("Message History", "Monitor conversations for Shariah compliance.")
        );

        mainContent.getChildren().addAll(welcomeLabel, actionCards);
        
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
    }

    private VBox createActionCard(String title, String desc) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setPrefWidth(250);
        card.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        
        Label t = new Label(title);
        t.setStyle("-fx-font-weight: bold;");
        Label d = new Label(desc);
        d.setWrapText(true);
        d.setStyle("-fx-font-size: 11px; -fx-text-fill: #666;");
        
        Button btn = new Button("Open");
        btn.setStyle("-fx-background-color: #1a3c34; -fx-text-fill: white;");
        
        // --- THE FIXED BUTTON LOGIC ---
        btn.setOnAction(e -> {
            switch (title) {
                case "View Ward Profile":
                    // Fix: Passing both stage and name so it knows WHICH ward to show
                    new GuardianWardProfileView(stage, guardianName).show();
                    break;
                case "Message History":
                    // Fix: Ensure class name matches your file "GuardianMessageMonitorView"
                    new GuardianMessageMonitorView(stage, guardianName).show();
                    break;
            }
        });
        
        card.getChildren().addAll(t, d, btn);
        return card;
    }
}