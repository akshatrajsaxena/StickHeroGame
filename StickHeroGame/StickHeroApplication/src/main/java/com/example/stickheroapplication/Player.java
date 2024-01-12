package com.example.stickheroapplication;

import java.util.ArrayList;
import java.util.List;
class GameListener {
    public void onPlayerCollides(Player player) {
    }
}
public class Player {

    private List<GameListener> listeners = new ArrayList<>();

    private int cherries;
    private int lives;

    private Stick stick;

    public Player() {
        stick = new Stick();
    }

    public void stretchStick(int length) {
        stick.setLength(length);
    }

    public void releaseStick() {
        stick.disconnectFromPlatform();
    }


    public void collide() {
        for(GameListener listener : listeners) {
            listener.onPlayerCollides(this);
        }

        // Lose life
        lives--;
    }

    public int getCherries() {
        return cherries;
    }

    public void addCherries(int amount) {
        this.cherries += amount;
    }
    public void addListener(GameListener listener) {
        listeners.add(listener);
    }
    public void removeListener(GameListener listener) {
        listeners.remove(listener);
    }

    public void startFalling() {
        System.out.println("Player is falling");
    }
}
class Stick {

    private int length;
    private Player player;
    private Object attachedPlatform;

    public void setLength(int length) {
        this.length = length;
    }

    public void disconnectFromPlatform() {
        this.attachedPlatform = null;
        player.startFalling();

    }
}