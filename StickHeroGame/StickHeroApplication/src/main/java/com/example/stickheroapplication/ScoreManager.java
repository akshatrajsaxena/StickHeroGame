package com.example.stickheroapplication;

//this is a singleton design pattern

import java.io.File;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;

public class ScoreManager {
    private static ScoreManager scoreManager = null;
    private int score;
    private int highscore;

    public static ScoreManager getInstance(){
        if (scoreManager == null) {
            scoreManager = new ScoreManager();
        }
        return scoreManager;
    }

    private ScoreManager() {
        this.score = 0;
        //get highscore from file
        try {
            File file = new File("src/main/java/com/example/stickheroapplication/highscore.txt");
            Scanner scanner = new Scanner(file);
            highscore = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Error reading highscore from file");
        }
    }

    public void syncScore() {
        //write highscore to file
        try {
            File file = new File("src/main/java/com/example/stickheroapplication/highscore.txt");
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(highscore);
            printWriter.close();
        } catch (Exception e) {
            System.out.println("Error writing highscore to file");
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }
}
