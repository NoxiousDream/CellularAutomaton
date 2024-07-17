package com.noxious_dream.cellularautomata;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main_menu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main_menu.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("CellularAutomata");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
