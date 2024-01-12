package com.example.stickheroapplication;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GamePlayController {

    @FXML
    private Pane gameScreen;

    @FXML
    private Label score;

    @FXML
    private ImageView stickHero;

    @FXML
    private Label cherryScore;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private boolean isGrowing = false;
    private Line stick;
    private double stickLength = 0.0;
    private final double fixedCharDist = 55.0;
    private final double minDist = 30.0;
    private final double maxDist = 150.0;
    private ArrayList<Rectangle> platforms = new ArrayList<>();
    private Rectangle prevPlatform;
    private Rectangle currPlatform;
    private double angle = 270.0;
    private int currScore = 0;
    private int highScore = 0;
    private double prevDist = 0.0;
    private ImageView prevCherry;
    private double prevCherryDist;
    private boolean isGameActive = true;
    private boolean isUpsideDown = false;

    private SoundManager soundManager = new SoundManager();

    private Timeline stickExtension;

    private Timeline timeline;

    private PlatformGenerator platformGenerator = new PlatformGenerator();

    Random random = new Random();

    @FXML
    public void initialize() {
        soundManager.playGameBGM();
        Rectangle initPlatform = platformGenerator.initializePlatform();
        gameScreen.getChildren().add(initPlatform);
        prevPlatform = initPlatform;
        currPlatform = initPlatform;
        platforms.add(initPlatform);
        cherryScore.setText("0");
        startGeneratingPlatforms();
        gameScreen.setOnMousePressed(event -> {
            if (isGameActive && !isGrowing) {
                isGrowing = true;
                handleMousePressed();
            }
        });
        gameScreen.setOnMouseReleased(event -> {
            if (isGameActive && isGrowing) {
                isGrowing = false;
                handleMouseReleased();
            }
        });
    }
    private void startGeneratingPlatforms() {
        javafx.animation.KeyFrame keyFrame = new javafx.animation.KeyFrame(
                javafx.util.Duration.seconds(0.1),
                event -> generatePlatform()
        );

        timeline = new javafx.animation.Timeline(keyFrame);
        timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
        timeline.play();
    }

    private void stopGeneratingPlatforms() {
        timeline.stop();
    }
    public void collectCherry(){
        String labelText = cherryScore.getText();

        int spaceIndex = labelText.indexOf(" ");
        String cherryCountStr = labelText.substring(spaceIndex+1);
        int currCherryScore = Integer.parseInt(cherryCountStr);
        currCherryScore++;
        FadeTransition ft = new FadeTransition(Duration.millis(500), cherryScore);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();

//        ft.setOnFinished(event -> {
//            cherryScore.setVisible(false);
//        });

        gameScreen.getChildren().remove(prevCherry);
        prevCherry = null;
        soundManager.playCherryCollectSound();
        cherryScore.setText("Cherries: " + currCherryScore);
    }
    private void generatePlatform() {
        Rectangle platform = platformGenerator.generatePlatform();
        double minLayoutX = prevPlatform.getLayoutX() + prevPlatform.getWidth() + minDist;
        double maxLayoutX = prevPlatform.getLayoutX() + prevPlatform.getWidth() + maxDist;
        double layoutX = random.nextDouble() * (maxLayoutX - minLayoutX) + minLayoutX;
        platform.setLayoutX(layoutX);
        gameScreen.getChildren().add(platform);
        platforms.add(platform);
        prevPlatform = platform;
    }

    public void handleMousePressed() {
        double pivotX = currPlatform.getLayoutX() + currPlatform.getWidth();
        double pivotY = currPlatform.getLayoutY();
        stick = new Line(pivotX,pivotY,pivotX,pivotY);
        stick.setStroke(Color.BLACK);
        stick.setStrokeWidth(4.0);
        gameScreen.getChildren().add(stick);
        soundManager.playStickGrowSound();

        //using timeline
        stickExtension = new Timeline(new javafx.animation.KeyFrame(Duration.millis(50), e -> {
            stickLength += 10;
            stick.setEndY(stick.getStartY() - stickLength);
        }));
        stickExtension.setCycleCount(Timeline.INDEFINITE);
        stickExtension.play();
    }

    public void handleMouseReleased() {
        //stop the animation timer
        stickExtension.stop();
        rotateStick();
    }


    private void rotateStick() {
        //rotate stick by 90 degrees by adding setX and setY using timeline
        double centerX = stick.getStartX();
        double centerY = stick.getStartY();

        Timeline timeline = new Timeline(new javafx.animation.KeyFrame(Duration.millis(40), e -> {
            angle += 5;

            double angleInRadians = Math.toRadians(angle);
            double endX = centerX + stickLength * Math.cos(angleInRadians);
            double endY = centerY + stickLength * Math.sin(angleInRadians);

            stick.setStartX(centerX);
            stick.setStartY(centerY);
            stick.setEndX(endX);
            stick.setEndY(endY);

        }));
        timeline.setCycleCount(18);
        timeline.play();
        //pause till stick animation completes
        timeline.setOnFinished(event -> {
            checkIfStickIsLongEnough();
        });
    }

    private void checkIfStickIsLongEnough() {
        //check if stick is long enough to reach the next platform
        //if it is, move the character to the next platform
        //if it is not, end the game
        double currPlatformEndX = currPlatform.getLayoutX() + currPlatform.getWidth();
        double nextPlatformStartX = platforms.get(platforms.indexOf(currPlatform) + 1).getLayoutX();
        double nextPlatformEndX = nextPlatformStartX + platforms.get(platforms.indexOf(currPlatform) + 1).getWidth();
        if (stickLength >= nextPlatformStartX - currPlatformEndX && stickLength < nextPlatformEndX - currPlatformEndX) {
            moveCharacterToNextPlatform();
            soundManager.playStickFallSound();
        } else {
            endGame();
        }
    }

    private void moveCharacterToNextPlatform() {
        Rectangle previousPlatform = currPlatform;
        int nextPlatformIndex = platforms.indexOf(currPlatform) + 1;
        currPlatform = platforms.get(nextPlatformIndex);
        //Animate the character to move to the next platform
        double characterX = stickHero.getLayoutX();
        double characterEndX = currPlatform.getLayoutX() + currPlatform.getWidth() - fixedCharDist;
        double distancetoTravel = currPlatform.getLayoutX() - characterX;
        double distance = distancetoTravel - prevDist;
        double speed = 100;
        double duration = distance / speed * 1000;
        prevDist = distancetoTravel;
        isGameActive = false;
        gameScreen.setOnMouseClicked(event -> {
            if (!isGameActive) {
                flipCharacter();
            }
        });
        TranslateTransition tt = new TranslateTransition(Duration.millis(duration), stickHero);
        tt.setToX(characterEndX - characterX);
        tt.play();
        tt.setOnFinished(event -> {
            if (isUpsideDown) {
                playerFalls();
            }
            else {
                if (score.getText().equals("00")) {
                    score.setText("01");
                    currScore = 1;
                }
                else if (Integer.parseInt(score.getText()) <= 9) {
                    currScore = Integer.parseInt(score.getText());
                    currScore++;
                    score.setText(String.format("%02d", currScore)); // 2 digits
                } else {
                    currScore = Integer.parseInt(score.getText());
                    currScore++;
                    score.setText(currScore + "");
                }
                gameScreen.getChildren().remove(stick);
                stickLength = 0.0;
                angle = 270.0;
                double shiftAmount = currPlatform.getLayoutX() - previousPlatform.getLayoutX();
                stickHero.setLayoutX(stickHero.getLayoutX() - shiftAmount);
                for (Rectangle platform : platforms) {
                    platform.setLayoutX(platform.getLayoutX() - shiftAmount);
                }
                Rectangle nextPlatform = platforms.get(nextPlatformIndex + 1);
                isGameActive = true;
                if (nextPlatformIndex > 0) {
                    if (prevCherry != null) {
                        gameScreen.getChildren().remove(prevCherry);
                    }
                    displayCherryBetweenPlatforms(currPlatform, nextPlatform);
                }
            }
        });
    }

    private void flipCharacter() {
        //rotate along x-axis by 180 degrees by flipping
        if(isUpsideDown) {
            stickHero.setLayoutY(208);
            RotateTransition rt = new RotateTransition(Duration.millis(100), stickHero);
            rt.setAxis(javafx.scene.transform.Rotate.X_AXIS);
            rt.setByAngle(180);
            rt.setCycleCount(1);
            rt.play();
            isUpsideDown = false;
            if (prevCherry != null) {
                checkCherryColection();
            }
        }
        else {
            stickHero.setLayoutY(241);
            RotateTransition rt = new RotateTransition(Duration.millis(100), stickHero);
            rt.setAxis(javafx.scene.transform.Rotate.X_AXIS);
            rt.setByAngle(180);
            rt.setCycleCount(1);
            rt.play();
            isUpsideDown = true;
        }
    }

    private void checkCherryColection() {
        double cherryX = prevCherry.getLayoutX();
        double playerX = stickHero.getLayoutX();
        System.out.println("cherryX: " + cherryX);
        System.out.println("playerX: " + playerX);

        if(Math.abs(cherryX - playerX) < 1000 || Math.abs(cherryX - playerX) > 1000) {
            collectCherry();
        }
    }

    private void displayCherryBetweenPlatforms(Rectangle previousPlatform, Rectangle currPlatform) {
        double minCherryX = previousPlatform.getLayoutX() + previousPlatform.getWidth();
        double maxCherryX = currPlatform.getLayoutX() - 23;
        //display only if dist > 100
        if (maxCherryX - minCherryX < 100) {
            return;
        }
        double cherryX = random.nextDouble() * (maxCherryX - minCherryX) + minCherryX;
        ImageView cherry = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/cherry.png"))));
        cherry.setLayoutX(cherryX);
        cherry.setLayoutY(302);
        cherry.setFitWidth(23);
        cherry.setFitHeight(23);
        gameScreen.getChildren().add(cherry);
        prevCherry = cherry;
        prevCherryDist = cherryX;
    }


    private void endGame() {
        stopGeneratingPlatforms();
        //move player till end of stick and then fall
        isGameActive = false;
        gameScreen.setOnMouseClicked(event -> {
            if (!isGameActive) {
                flipCharacter();
            }
        });
        double characterX = stickHero.getLayoutX();
        double distancetoTravel = stick.getEndX() - characterX - 30;
        double distance = distancetoTravel - prevDist;
        double speed = 100;
        double duration = distance / speed * 1000;
        prevDist = distancetoTravel;
        TranslateTransition tt = new TranslateTransition(Duration.millis(duration), stickHero);
        tt.setToX(stick.getEndX() - characterX - 30);
        tt.play();
        tt.setOnFinished(event -> {
            gameScreen.getChildren().remove(stick);
            stickLength = 0.0;
            angle = 270.0;
            playerFalls();
        });
    }

    private void playerFalls() {
        ScoreManager scoreManager = ScoreManager.getInstance();
        if (currScore > scoreManager.getHighscore()) {
            highScore = currScore;
        } else {
            highScore = scoreManager.getHighscore();
        }
        scoreManager.setScore(currScore);
        scoreManager.setHighscore(highScore);
        double characterY = stickHero.getLayoutY();
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), stickHero);
        tt.setToY(characterY + 250);
        tt.play();
        tt.setOnFinished(event -> {
            soundManager.playPlayerFallSound();
            //soundManager.pauseGameBGM();
            //show game over screen
            try {
                switchToGameOverScreen();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void switchToGameOverScreen() throws IOException {
        soundManager.pauseGameBGM();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gameover.fxml")));
        stage = (Stage)gameScreen.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

