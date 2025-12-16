package com.mygdx.game.world;

import com.badlogic.gdx.graphics.Color;

public class WorldProgress {



    public int levelsCompleted = 0;


    public Color floorColor = new Color(0.2f, 0.2f, 0.2f, 1f);
    public Color brickColor = new Color(0.95f, 0.25f, 0.1f, 1f);
    public Color worldTopColor = new Color(0.95f, 0.25f, 0.1f, 1f);
    public Color doorColor = new Color(0.8f, 0.8f, 0.8f, 1f);


    public int outerWorldSize = 25;
    public int innerWorldSize = 10;



    public void completedLevel() {
        levelsCompleted++;

        if (levelsCompleted > 3) {
            brickColor = new Color(0.1f, 0.2f, 1f, 1f);
            worldTopColor = new Color(0.1f, 0.2f, 0.7f, 1f);
        }

    }

    public int howManyEnemiesShouldSpawn() {
        return 2 + levelsCompleted;
    }

}
