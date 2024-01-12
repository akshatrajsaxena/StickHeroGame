package com.example.stickheroapplication;

// import javafx.application.Application; // Commented out the unused import

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class SceneController {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView muteImageView;

    private SoundManager soundManager = new SoundManager();

    public void initialize() {
        soundManager.playHomeScreenBGM();
    }

    @FXML
    public void exitGame(ActionEvent event) {
        soundManager.playClickSound();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Game");
        alert.setHeaderText("Are you sure you want to exit the game?");
        alert.setContentText("Press OK to exit the game, or press Cancel to return to the game.");
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Game is exiting...");
            stage.close();
        }
    }

    @FXML
    public void playMusic(ActionEvent event) {
        if (soundManager.switchMusic(event)) {
            muteImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/SoundOn.png"))));
        } else {
            muteImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/SoundOff.png"))));
        }
    }

    @FXML
    public void switchtoGameScreen(ActionEvent event) throws IOException {
        soundManager.pauseHomeScreenBGM();
        soundManager.playPlaySound();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gameplay.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
