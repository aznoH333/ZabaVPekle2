package com.mygdx.game;

public class WorldManager {

    private static WorldManager instance = null;


    public static WorldManager getInstance() {
        if (instance == null) {
            instance = new WorldManager();
        }
        return instance;
    }

    private static SpriteManager spriteManager = SpriteManager.getInstance();

    public void draw() {
        for (int x = -30; x < 30; x++) {
            for (int y = -30; y < 30; y++) {
                if (Math.abs(x) < 20 && Math.abs(y) < 20) {
                    spriteManager.drawSprite("floor", x * 32f, y * 32f, 0.7f, 0.2f, 0.2f);
                }else {
                    spriteManager.drawSprite("wall", x * 32f, y * 32f, 0.7f, 0.2f, 0.2f);

                }
            }
        }
    }

    public boolean isSpaceEmpty(float x, float y, float width, float height) {
        // placehodler logic
        float widthValue = width / 2f;
        float heightValue = height / 2f;

        return x - widthValue > -20 * 32f &&
               x + widthValue < 20 * 32f &&
               y - heightValue > -20 * 32f &&
               y + heightValue < 20 * 32f;
    }
}
