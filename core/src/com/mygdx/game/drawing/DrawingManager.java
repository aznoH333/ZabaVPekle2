package com.mygdx.game.drawing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrawingManager {

    public static DrawingManager getInstance() {
        if (instance == null) {
            instance = new DrawingManager();
        }
        return instance;
    }

    private static DrawingManager instance;


    private final HashMap<String, Texture> spriteMap = new HashMap<>();
    private final SpriteBatch batch = new SpriteBatch();
    private final SpriteBatch staticBatch = new SpriteBatch();
    private final ArrayList<ArrayList<DrawingCommand>> drawingQueue = new ArrayList<>();
    private final ArrayList<ArrayList<DrawingCommand>> staticDrawingQueue = new ArrayList<>();
    private final ArrayList<TextDrawingCommand> fontDrawingQueue = new ArrayList<>();

    private final OrthographicCamera camera = new OrthographicCamera();
    private final OrthographicCamera staticCamera = new OrthographicCamera();

    private final Viewport viewPort = new FitViewport(568f, 320f, camera);
    private final Viewport staticViewPort = new ExtendViewport(960f, 640f, staticCamera);

    BitmapFont font = new BitmapFont();

    private DrawingManager() {
        viewPort.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 1f;
        staticCamera.zoom = 1f;
    }


    public void loadSpritesInDirectory(String path) {

        FileHandle handle = Gdx.files.internal(path);
        if (!handle.exists()) {
            System.exit(1);
        }
        System.out.println(handle.isDirectory());
        for (FileHandle f : handle.list()) {
            if (!f.isDirectory()) {
                loadSprite(f.path(), f.nameWithoutExtension());
            } else {
                loadSpritesInDirectory(f.path());
            }
        }
    }

    private void loadSprite(String path, String name) {
        System.out.println("loading sprite " + name + " with path " + path);
        this.spriteMap.put(name, new Texture(path));
    }


    private void drawSprite(SpriteBatch batch, String spriteName, float x, float y, float width, float height, boolean flipHorizontally, boolean flipVertically, float rotationRad, float r, float g, float b, float a) {
        Texture sprite = spriteMap.getOrDefault(spriteName, null);

        if (sprite == null) {
            System.out.println("Requested sprite with name " + spriteName + " but it was not found");
            System.exit(-1); // c ah crash
        }

        float w = sprite.getWidth() / 2.0f;
        float h = sprite.getHeight() / 2.0f;
        float rotation = (float) Math.toDegrees(rotationRad);

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
            rotation,
            0,
            0,
            sprite.getWidth(),
            sprite.getHeight(),
            flipHorizontally,
            flipVertically
        );
    }

    public void drawSprite(DrawingCommand command, DrawingLayer layer, boolean isStatic) {
        ArrayList<ArrayList<DrawingCommand>> queue;

        if (isStatic) {
            queue = staticDrawingQueue;
        } else {
            queue = drawingQueue;
        }

        if (queue.size() < layer.value + 1) {
            for (int i = queue.size(); i < layer.value + 1; i++) {
                queue.add(new ArrayList<>());
            }
        }

        queue.get(layer.value).add(command);
    }

    public void drawSprite(DrawingCommand command, DrawingLayer layer) {
        drawSprite(command, layer, false);
    }

    public void drawSpriteStatic(DrawingCommand command, DrawingLayer layer) {
        drawSprite(command, layer, true);
    }

    public void drawText(TextDrawingCommand command) {
        this.fontDrawingQueue.add(command);
    }

    private void renderText(TextDrawingCommand command) {

        font.setColor(command.r, command.b, command.g, command.a);


        GlyphLayout layout = new GlyphLayout(font, command.text);

        font.draw(staticBatch, command.text, command.x - (layout.width / 2f), command.y);
    }


    public void render() {
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1);

        viewPort.apply();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        // sprites
        for (ArrayList<DrawingCommand> layer : drawingQueue) {
            for (DrawingCommand command : layer) {
                drawSprite(
                    batch,
                    command.spriteName,
                    command.x,
                    command.y,
                    command.width,
                    command.height,
                    command.flipHorizontally,
                    command.flipVertically,
                    command.rotationRad,
                    command.r,
                    command.g,
                    command.b,
                    command.a
                );
            }
            layer.clear();
        }
        batch.end();


        staticBatch.setProjectionMatrix(staticCamera.combined);
        staticViewPort.apply();
        staticBatch.begin();

        for (ArrayList<DrawingCommand> layer : staticDrawingQueue) {
            for (DrawingCommand command : layer) {
                drawSprite(
                    staticBatch,
                    command.spriteName,
                    command.x,
                    command.y,
                    command.width,
                    command.height,
                    command.flipHorizontally,
                    command.flipVertically,
                    command.rotationRad,
                    command.r,
                    command.g,
                    command.b,
                    command.a
                );
            }
            layer.clear();
        }

        // font
        for (TextDrawingCommand textDrawingCommand : fontDrawingQueue) {
            renderText(textDrawingCommand);
        }
        fontDrawingQueue.clear();
        staticBatch.end();
    }

    public void dispose() {
        for (Map.Entry<String, Texture> entry : spriteMap.entrySet()) {
            entry.getValue().dispose();
        }

        font.dispose();

        spriteMap.clear();
        staticBatch.dispose();
        batch.dispose();
    }

    public void resizedWindow(int width, int height) {
        viewPort.update(width, height);
        staticViewPort.update(width, height);
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

    public Vector2 getScreenMousePosition() {

        return new Vector2(
            ((float) Gdx.input.getX() / Gdx.graphics.getWidth() - 0.5f) * (staticViewPort.getWorldWidth() * staticCamera.zoom),
            (-(float) Gdx.input.getY() / Gdx.graphics.getHeight() + 0.5f) * (staticViewPort.getWorldHeight() * staticCamera.zoom)
        );
    }

    public float getTextWidth(String text) {
        return new GlyphLayout(font, text).width;
    }

}
