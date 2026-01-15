/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project;
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
