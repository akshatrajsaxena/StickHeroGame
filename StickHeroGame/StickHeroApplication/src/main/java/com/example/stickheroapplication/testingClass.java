package com.example.stickheroapplication;

import javafx.scene.shape.Rectangle;
import org.junit.Test;
import static org.junit.Assert.*;


public class testingClass {

    @Test
    public void testScoreManager(){
        ScoreManager mgr1 = ScoreManager.getInstance();
        ScoreManager mgr2 = ScoreManager.getInstance();
        // Test single instance returned
        assertEquals(mgr1, mgr2);

        mgr1.setScore(10);
        assertEquals(10, mgr2.getScore());
    }

    @Test
    public void platformGeneratorTest(){
        PlatformGenerator generator = new PlatformGenerator();
        Rectangle platform = generator.initializePlatform();
        assertEquals(100.0, platform.getWidth(), 0.0);
        assertEquals(100.0, platform.getHeight(), 0.0);
    }
}
