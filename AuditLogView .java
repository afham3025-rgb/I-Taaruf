package com.mycompany.oop;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AuditLogView {
    private Stage stage;

    public AuditLogView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f4f8;");

        // Top Bar
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #2c5f2d;");

        Label titleLabel = new Label("System Audit Log");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button backButton = new Button("Back to Dashboard");
        backButton.setStyle("-fx-background-color: white; -fx-text-fill: #2c5f2d;");
        backButton.setOnAction(e -> {
            new DashboardView(stage).show();
        });

        topBar.getChildren().addAll(titleLabel, spacer, backButton);

        // Audit Log Content
        VBox auditBox = new VBox(15);
        auditBox.setPadding(new Insets(20));

        Label auditLabel = new Label("Recent System Activities");
        auditLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        TextArea logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefRowCount(20);
        logArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
        logArea.setText(AuditLogger.readLogs());

        Button refreshButton = new Button("Refresh Logs");
        refreshButton.setStyle("-fx-background-color: #2c5f2d; -fx-text-fill: white; -fx-padding: 10;");
        refreshButton.setOnAction(e -> {
            logArea.setText(AuditLogger.readLogs());
        });

        auditBox.getChildren().addAll(auditLabel, refreshButton, logArea);

        root.setTop(topBar);
        root.setCenter(auditBox);

        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
    }
}
