package loginsystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
    	
        Pane painelVazio = new Pane(); 
        
        Scene cena = new Scene(painelVazio, 400, 300); 
        
        primaryStage.setTitle("Login System - Horizon Earth");
        primaryStage.setScene(cena);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}