package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class SpriteManager {

    public static SpriteManager getInstance() {
        if (instance == null) {
            instance = new SpriteManager();
        }
        return instance;
    }

    private static SpriteManager instance;


    private final HashMap<String, Texture> spriteMap = new HashMap<>();


    private SpriteManager() {
        loadSprites();
    }


    private void loadSprites() {

    }


    private void loadSprite(String path, String name) {
        this.spriteMap.put(name, new Texture(path));
    }

    public void drawSprite(String spriteName, float x, float y, boolean flipHorizontally, boolean flipVertically, float rotationRad, float r, float g, float b) {

    }

    public void dispose() {
        for (Map.Entry<String, Texture> entry : spriteMap.entrySet()) {
            entry.getValue().dispose();
        }
        spriteMap.clear();
    }

}
