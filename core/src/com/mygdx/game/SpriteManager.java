package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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
    private final SpriteBatch batch = new SpriteBatch();

    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport viewPort = new FitViewport(960f, 640f, camera);

    private SpriteManager() {
        viewPort.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // camera.zoom = 0.3f;
    }





    public void loadSprite(String path, String name) {
        this.spriteMap.put(name, new Texture(path));
    }


    public void drawSprite(String spriteName, float x, float y) {
        this.drawSprite(
                spriteName,
                x,
                y,
                1f,
                1f,
                false,
                false,
                0f,
                1f,
                1f,
                1f,
                1f
        );
    }

    public void drawSprite(String spriteName, float x, float y, float r, float g, float b) {
        this.drawSprite(
                spriteName,
                x,
                y,
                1f,
                1f,
                false,
                false,
                0f,
                r,
                g,
                b,
                1f
        );
    }


    public void drawSprite(String spriteName, float x, float y, float width, float height, boolean flipHorizontally, boolean flipVertically, float rotationRad, float r, float g, float b, float a) {
        Texture sprite = spriteMap.getOrDefault(spriteName, null);

        if (sprite == null) {
            System.out.println("Requested sprite with name " + spriteName + " but it was not found");
            System.exit(-1); // c ah crash
        }

        float w = sprite.getWidth() / 2.0f;
        float h = sprite.getHeight() / 2.0f;

        batch.setColor(r, g, b, a);
        batch.draw(
                sprite,
                x - w,
                y - h,
                w,
                h,
                (float) sprite.getWidth(),
                (float) sprite.getHeight(),
                width,
                height,
                rotationRad,
                0,
                0,
                sprite.getWidth(),
                sprite.getHeight(),
                flipHorizontally,
                flipVertically
        );
    }

    public void renderBegin() {

        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1);

        viewPort.apply();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();


    }

    public void render() {
        // TODO : this


        batch.end();
    }

    public void dispose() {
        for (Map.Entry<String, Texture> entry : spriteMap.entrySet()) {
            entry.getValue().dispose();
        }
        spriteMap.clear();
    }

    public void resizedWindow(int width, int height) {
        viewPort.update(width, height);
    }

    public void setCameraPosition(float x, float y) {
        camera.position.x = x;
        camera.position.y = y;
        camera.update();
    }

    public Vector2 getMousePosition() {
        return new Vector2(
                camera.position.x + Gdx.input.getX() - (Gdx.graphics.getWidth() / 2f),
                camera.position.y - Gdx.input.getY() + (Gdx.graphics.getHeight() / 2f)
        );

    }

}
