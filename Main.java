package com.mycompany.oop;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("I-Taaruf - Shariah-Compliant Matchmaking System");
        
        LoginView loginView = new LoginView(primaryStage);
        loginView.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}