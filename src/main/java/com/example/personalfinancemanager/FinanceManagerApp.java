package com.example.personalfinancemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FinanceManagerApp extends Application {
    private static Stage oneStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        oneStage = primaryStage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main_view.fxml")));
        primaryStage.setTitle("Personal Finance Manager");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void changeScene(String fxmlFile) throws IOException {
        Parent scene = FXMLLoader.load(Objects.requireNonNull(FinanceManagerApp.class.getResource(fxmlFile)));
        oneStage.getScene().setRoot(scene);
    }
}