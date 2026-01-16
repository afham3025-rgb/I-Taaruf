package com.mycompany.oop;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GuardianMatchRequestView {
    private Stage stage;
    private String guardianUsername;
    
    public GuardianMatchRequestView(Stage stage, String guardianUsername) {
        this.stage = stage;
        this.guardianUsername = guardianUsername;
    }
    
    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f4f8;");
        
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #1a4d2e;");
        
        Label titleLabel = new Label("Match Requests - Guardian Approval");
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
        
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));
        
        // Filter by ward
        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER_LEFT);
        
        Label filterLabel = new Label("Filter by Ward:");
        filterLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        ComboBox<String> wardFilter = new ComboBox<>();
        wardFilter.getItems().addAll("All Wards", "Ali Hassan", "Fatima Khan", "Omar Abdullah");
        wardFilter.setValue("All Wards");
        wardFilter.setPrefWidth(200);
        
        filterBox.getChildren().addAll(filterLabel, wardFilter);
        
        // Pending requests section
        VBox pendingBox = new VBox(15);
        pendingBox.setPadding(new Insets(20));
        pendingBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        Label pendingLabel = new Label("Pending Approval");
        pendingLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        pendingLabel.setStyle("-fx-text-fill: #c44536;");
        
        TableView<MatchRequest> pendingTable = new TableView<>();
        pendingTable.setPrefHeight(250);
        
        TableColumn<MatchRequest, String> wardCol1 = new TableColumn<>("Ward");
        wardCol1.setPrefWidth(120);
        wardCol1.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().ward));
        
        TableColumn<MatchRequest, String> fromCol = new TableColumn<>("From");
        fromCol.setPrefWidth(120);
        fromCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().from));
        
        TableColumn<MatchRequest, String> typeCol1 = new TableColumn<>("Type");
        typeCol1.setPrefWidth(100);
        typeCol1.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().type));
        
        TableColumn<MatchRequest, String> dateCol1 = new TableColumn<>("Date");
        dateCol1.setPrefWidth(150);
        dateCol1.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().date));
        
        TableColumn<MatchRequest, Void> actionCol1 = new TableColumn<>("Actions");
        actionCol1.setPrefWidth(200);
        actionCol1.setCellFactory(col -> new TableCell<>() {
            private final Button approveBtn = new Button("Approve");
            private final Button rejectBtn = new Button("Reject");
            private final Button viewBtn = new Button("View");
            private final HBox pane = new HBox(5, viewBtn, approveBtn, rejectBtn);
            
            {
                approveBtn.setStyle("-fx-background-color: #2d5f3e; -fx-text-fill: white; -fx-padding: 5;");
                rejectBtn.setStyle("-fx-background-color: #c44536; -fx-text-fill: white; -fx-padding: 5;");
                viewBtn.setStyle("-fx-background-color: #4a8056; -fx-text-fill: white; -fx-padding: 5;");
                
                approveBtn.setOnAction(e -> {
                    MatchRequest request = getTableView().getItems().get(getIndex());
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Approve Request");
                    confirm.setHeaderText("Approve Match Request");
                    confirm.setContentText("Approve match request from " + request.from + " for " + request.ward + "?");
                    
                    confirm.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            AuditLogger.log("Guardian " + guardianUsername + " approved match request for " + request.ward);
                            getTableView().getItems().remove(getIndex());
                            Alert success = new Alert(Alert.AlertType.INFORMATION);
                            success.setTitle("Success");
                            success.setContentText("Match request approved!");
                            success.showAndWait();
                        }
                    });
                });
                
                rejectBtn.setOnAction(e -> {
                    MatchRequest request = getTableView().getItems().get(getIndex());
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Reject Request");
                    confirm.setHeaderText("Reject Match Request");
                    confirm.setContentText("Reject match request from " + request.from + " for " + request.ward + "?");
                    
                    confirm.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            AuditLogger.log("Guardian " + guardianUsername + " rejected match request for " + request.ward);
                            getTableView().getItems().remove(getIndex());
                            Alert success = new Alert(Alert.AlertType.INFORMATION);
                            success.setTitle("Success");
                            success.setContentText("Match request rejected");
                            success.showAndWait();
                        }
                    });
                });
                
                viewBtn.setOnAction(e -> {
                    MatchRequest request = getTableView().getItems().get(getIndex());
                    showRequestDetails(request);
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
        
        pendingTable.getColumns().addAll(wardCol1, fromCol, typeCol1, dateCol1, actionCol1);
        
        // Sample data
        pendingTable.getItems().addAll(
            new MatchRequest("Ali Hassan", "Aisha Rahman", "Received", "2025-01-10 10:30 AM"),
            new MatchRequest("Fatima Khan", "Yusuf Ibrahim", "Received", "2025-01-12 02:15 PM"),
            new MatchRequest("Omar Abdullah", "Khadija Aziz", "Sent", "2025-01-14 09:00 AM")
        );
        
        pendingBox.getChildren().addAll(pendingLabel, pendingTable);
        
        // Approved/Processed requests section
        VBox processedBox = new VBox(15);
        processedBox.setPadding(new Insets(20));
        processedBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        Label processedLabel = new Label("Processed Requests");
        processedLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        processedLabel.setStyle("-fx-text-fill: #2d5f3e;");
        
        TableView<MatchRequest> processedTable = new TableView<>();
        processedTable.setPrefHeight(200);
        
        TableColumn<MatchRequest, String> wardCol2 = new TableColumn<>("Ward");
        wardCol2.setPrefWidth(120);
        wardCol2.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().ward));
        
        TableColumn<MatchRequest, String> matchCol = new TableColumn<>("Match");
        matchCol.setPrefWidth(120);
        matchCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().from));
        
        TableColumn<MatchRequest, String> statusCol = new TableColumn<>("Status");
        statusCol.setPrefWidth(120);
        statusCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().type));
        
        TableColumn<MatchRequest, String> dateCol2 = new TableColumn<>("Date Processed");
        dateCol2.setPrefWidth(150);
        dateCol2.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().date));
        
        processedTable.getColumns().addAll(wardCol2, matchCol, statusCol, dateCol2);
        
        // Sample processed data
        processedTable.getItems().addAll(
            new MatchRequest("Ali Hassan", "Maryam Noor", "Approved", "2025-01-05 11:20 AM"),
            new MatchRequest("Fatima Khan", "Hassan Farooq", "Rejected", "2025-01-08 03:45 PM")
        );
        
        processedBox.getChildren().addAll(processedLabel, processedTable);
        
        mainContent.getChildren().addAll(filterBox, pendingBox, processedBox);
        
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f0f4f8;");
        
        root.setTop(topBar);
        root.setCenter(scrollPane);
        
        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
    }
    
    private void showRequestDetails(MatchRequest request) {
        Alert details = new Alert(Alert.AlertType.INFORMATION);
        details.setTitle("Request Details");
        details.setHeaderText("Match Request Information");
        details.setContentText(
            "Ward: " + request.ward + "\n" +
            "From: " + request.from + "\n" +
            "Type: " + request.type + "\n" +
            "Date: " + request.date + "\n\n" +
            "Message: Assalamualaikum, I would like to express my interest...\n\n" +
            "Requester Profile:\n" +
            "Age: 28 years\n" +
            "Education: Bachelor's\n" +
            "Occupation: Engineer"
        );
        details.showAndWait();
    }
    
    // Inner class for match request data
    public static class MatchRequest {
        private String ward;
        private String from;
        private String type;
        private String date;
        
        public MatchRequest(String ward, String from, String type, String date) {
            this.ward = ward;
            this.from = from;
            this.type = type;
            this.date = date;
        }
    }
}
