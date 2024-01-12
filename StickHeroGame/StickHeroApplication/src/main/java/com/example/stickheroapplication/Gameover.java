package com.example.stickheroapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Gameover {

    @FXML
    private Button playAgainButton;
    @FXML
    private Button homeButton;
    @FXML
    private Button syncButton;
    @FXML
    private Label currScore;
    @FXML
    private Label highScore;

    private SoundManager soundManager = new SoundManager();

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    public void initialize() {
        ScoreManager scoreManager = ScoreManager.getInstance();
        if (scoreManager.getScore() < 10) {
            currScore.setText("0" + scoreManager.getScore());
        } else {
            currScore.setText(String.valueOf(scoreManager.getScore()));
        }
        //see if highscore is lees than 10
        //if so, add a 0 in front of it
        //if not, just display the highscore
        if (scoreManager.getHighscore() < 10) {
            highScore.setText("0" + scoreManager.getHighscore());
        } else {
            highScore.setText(String.valueOf(scoreManager.getHighscore()));
        }
    }

    public void switchtoHomeScreen(ActionEvent event) throws IOException {
        soundManager.playHomeScreenBGM();
        soundManager.playClickSound();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-screen.fxml")));
        stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchtoGameScreen(ActionEvent event) throws IOException {
        soundManager.playClickSound();
        soundManager.playGameBGM();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gameplay.fxml")));
        stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void syncScore(ActionEvent event) {
        soundManager.playClickSound();
        //add highscore to file
        ScoreManager scoreManager = ScoreManager.getInstance();
        scoreManager.syncScore();
    }
}
