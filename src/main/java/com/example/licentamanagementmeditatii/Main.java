package com.example.licentamanagementmeditatii;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
//clasa Main, porneste aplicatia.
public class Main extends Application {
    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
       // FXMLLoader loader = new FXMLLoader(Main.class.getResource("Sessions.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("Landing.fxml"));
      //  Scene scene = new Scene(loader.load(), 900, 600);
        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}