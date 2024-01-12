package com.example.stickheroapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class StickHeroApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(StickHeroApplication.class.getResource("home-screen.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("Stick Hero");
        stage.getIcons().add(new Image(Objects.requireNonNull(StickHeroApplication.class.getResourceAsStream("images/character.png"))));
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        stage.setOnCloseRequest(e -> {
            e.consume();
            exitGame(stage);
        });
    }

    public void exitGame(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Game");
        alert.setHeaderText("Are you sure you want to exit the game?");
        alert.setContentText("Press OK to exit the game, or press Cancel to return to the game.");
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Game is exiting...");
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
        Result result = JUnitCore.runClasses(testingClass.class);
        for (Failure failure : result.getFailures()){
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}