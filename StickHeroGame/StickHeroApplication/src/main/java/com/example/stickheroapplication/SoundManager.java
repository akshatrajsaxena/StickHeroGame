package com.example.stickheroapplication;

import javafx.event.ActionEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class SoundManager {

    private AudioClip clickSound;
    private MediaPlayer gameBGM;
    private MediaPlayer homescreenBGM;
    private boolean isMuted = false;

    public void playHomeScreenBGM() {
        isMuted = false;
        String musicPath = Objects.requireNonNull(getClass().getResource("sounds/bgm.mp3")).toString();
        Media media = new Media(musicPath);
        homescreenBGM = new MediaPlayer(media);
        homescreenBGM.setAutoPlay(true);
        homescreenBGM.setVolume(0.2);
        homescreenBGM.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void pauseHomeScreenBGM() {
        homescreenBGM.pause();
    }

    public boolean isHomeScreenBGMPlaying() {
        return homescreenBGM.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public void playGameBGM() {
        if (isMuted) {
            return;
        }
        String musicPath = Objects.requireNonNull(getClass().getResource("sounds/gameMusic.mp3")).toString();
        Media media = new Media(musicPath);
        gameBGM = new MediaPlayer(media);
        gameBGM.setAutoPlay(true);
        gameBGM.setVolume(0.2);
        gameBGM.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void pauseGameBGM() {
        gameBGM.pause();
    }

    public boolean isGameBGMPlaying() {
        return gameBGM.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public boolean switchMusic(ActionEvent event) {
        if (isMuted) {
            isMuted = false;
            homescreenBGM.play();
            return true;
        } else {
            isMuted = true;
            homescreenBGM.pause();
            return false;
        }
    }

    public void playClickSound() {
        if (isMuted) {
            return;
        }
        String musicPath = Objects.requireNonNull(getClass().getResource("sounds/click.mp3")).toString();
        clickSound = new AudioClip(musicPath);
        clickSound.play();
    }

    public void playPlaySound() {
        if (isMuted) {
            return;
        }
        String musicPath = Objects.requireNonNull(getClass().getResource("sounds/play.mp3")).toString();
        clickSound = new AudioClip(musicPath);
        clickSound.play();
    }

    public void playGameOverSound() {
        if (isMuted) {
            return;
        }
        String musicPath = Objects.requireNonNull(getClass().getResource("sounds/gameOver.mp3")).toString();
        clickSound = new AudioClip(musicPath);
        clickSound.play();
    }

    public void playStickGrowSound() {
        if (isMuted) {
            return;
        }
        String musicPath = Objects.requireNonNull(getClass().getResource("sounds/stickgrow.mp3")).toString();
        clickSound = new AudioClip(musicPath);
        clickSound.play();
    }

    public void playStickFallSound() {
        if (isMuted) {
            return;
        }
        String musicPath = Objects.requireNonNull(getClass().getResource("sounds/stickfall.mp3")).toString();
        clickSound = new AudioClip(musicPath);
        clickSound.play();
    }

    public void playPlayerFallSound() {
        if (isMuted) {
            return;
        }
        String musicPath = Objects.requireNonNull(getClass().getResource("sounds/playerfall.mp3")).toString();
        clickSound = new AudioClip(musicPath);
        clickSound.play();
    }

    public void playCherryCollectSound() {
        if (isMuted) {
            return;
        }
        String musicPath = Objects.requireNonNull(getClass().getResource("sounds/pickup.mp3")).toString();
        clickSound = new AudioClip(musicPath);
        clickSound.play();
    }
}
