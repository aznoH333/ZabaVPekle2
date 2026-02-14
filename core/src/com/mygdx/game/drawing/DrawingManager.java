package com.mygdx.game.drawing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.drawing.shaders.LightingShaderHandler;
import com.mygdx.game.drawing.shaders.ScreenEdgeShaderHandler;
import com.mygdx.game.drawing.shaders.ScreenEffectShaderHandler;

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


    public static final float SCREEN_WIDTH = 640f;
    public static final float SCREEN_HEIGHT = 360f;

    private static DrawingManager instance;


    private final HashMap<String, Texture> spriteMap = new HashMap<>();
    /** Sprite batch. Renders to fbo. Uses camera */
    private final SpriteBatch batch = new SpriteBatch();
    /** Sprite batch. Renders to fbo. Doesn't use the camera*/
    private final SpriteBatch staticBatch = new SpriteBatch();
    private final SpriteBatch staticOutputBatch = new SpriteBatch();
    /** Output sprite batch. Renders fbo to screen */
    private final SpriteBatch outputBatch = new SpriteBatch();
    private final ArrayList<ArrayList<DrawingCommand>> drawingQueue = new ArrayList<>();
    private final ArrayList<ArrayList<DrawingCommand>> staticDrawingQueue = new ArrayList<>();
    private final ArrayList<TextDrawingCommand> fontDrawingQueue = new ArrayList<>();

    private final OrthographicCamera camera = new OrthographicCamera();
    private final OrthographicCamera staticCamera = new OrthographicCamera();

    private final Viewport viewPort = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
    private final Viewport staticViewPort = new ExtendViewport(SCREEN_WIDTH, SCREEN_HEIGHT, staticCamera);
    
    public final LightingShaderHandler lightingShaderHandler;
    public final ScreenEffectShaderHandler screenEffectShaderHandler;
    public final ScreenEdgeShaderHandler screenEdgeShaderHandler;

    BitmapFont font;

    ShaderProgram gameShader;
    ShaderProgram screenShader;
    private FrameBuffer gameFrameBuffer;
    private FrameBuffer staticFrameBuffer;

    /* shader values*/
    private float loopedTimeValue = 0f;
    private float aspectRatio = 0;

    private DrawingManager() {
        viewPort.update((int) SCREEN_WIDTH, (int) SCREEN_HEIGHT);
        camera.zoom = 1f;
        staticCamera.zoom = 1f;
        gameFrameBuffer = createFrameBuffer(SCREEN_WIDTH * 4, SCREEN_HEIGHT * 4);
        staticFrameBuffer = createFrameBuffer(SCREEN_WIDTH * 4, SCREEN_HEIGHT * 4);


        // Source - https://stackoverflow.com/a
        // Posted by moreofles
        // Retrieved 2026-01-17, License - CC BY-SA 3.0
        // Thanks libgdx very cool. spent 2 days trying to figure this shit out
        Matrix4 matrix = new Matrix4();
        matrix.setToOrtho2D(0, 0, SCREEN_WIDTH,SCREEN_HEIGHT);
        outputBatch.setProjectionMatrix(matrix);
        staticOutputBatch.setProjectionMatrix(matrix);



        gameShader = buildShader("shaders/game/vertex.glsl", "shaders/game/fragment.glsl");
        screenShader = buildShader("shaders/screen/vertex.glsl", "shaders/screen/fragment.glsl");
        outputBatch.setShader(gameShader);
        staticOutputBatch.setShader(screenShader);

        
        lightingShaderHandler = new LightingShaderHandler(gameShader, camera);
        screenEffectShaderHandler = new ScreenEffectShaderHandler(screenShader, gameShader);
        screenEdgeShaderHandler = new ScreenEdgeShaderHandler(gameShader, camera);
        
        font = loadFont("fonts/3270/3270NerdFont-Regular.ttf");
    }
    
    private BitmapFont loadFont(String fontPath) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        BitmapFont font = generator.generateFont(parameter);
        
        generator.dispose();
        
        return font;
    }

    private FrameBuffer createFrameBuffer(float width, float height) {
        return new FrameBuffer(
            Pixmap.Format.RGBA8888, (int) width, (int) height, false
        );
    }

    private ShaderProgram buildShader(String vertexPath, String fragmentPath) {
        String vertexShader = Gdx.files.internal(vertexPath).readString();
        String fragmentShader = Gdx.files.internal(fragmentPath).readString();
        ShaderProgram shader = new ShaderProgram(
            vertexShader,
            fragmentShader
        );

        if (!shader.isCompiled()) {
            throw new GdxRuntimeException(shader.getLog());
        }

        shader.bind();
        return shader;
    }


    public void loadSpritesInDirectory(String path) {

        FileHandle handle = Gdx.files.internal(path);
        if (!handle.exists()) {
            System.exit(1);
        }
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

    private void renderBatch(SpriteBatch batch, ArrayList<ArrayList<DrawingCommand>> queue) {

        for (ArrayList<DrawingCommand> layer : queue) {
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
    }

    public void mainRenderLoop() {



        lightingShaderHandler.applyLights(aspectRatio);
        screenEdgeShaderHandler.apply();
        screenEffectShaderHandler.apply();

        gameFrameBuffer.begin();
            ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1);
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            viewPort.apply();
            renderBatch(batch, drawingQueue);
            batch.end();
        gameFrameBuffer.end();
        
        staticFrameBuffer.begin();
            ScreenUtils.clear(0.0f, 0.0f, 0.0f, 0f);
        
            staticBatch.begin();
            staticBatch.setProjectionMatrix(staticCamera.combined);
            
            
            staticViewPort.apply();
            renderBatch(staticBatch, staticDrawingQueue);
    
            // font
            for (TextDrawingCommand textDrawingCommand : fontDrawingQueue) {
                renderText(textDrawingCommand);
            }
            fontDrawingQueue.clear();
            staticBatch.end();
        staticFrameBuffer.end();

        Texture staticFrameBufferTexture = staticFrameBuffer.getColorBufferTexture();

        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1);

        outputBatch.begin();
        Texture frameBufferTexture = gameFrameBuffer.getColorBufferTexture();
        outputBatch.draw(frameBufferTexture, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 0, 0, 1, 1);
        
        outputBatch.end();
        
        staticOutputBatch.begin();
        staticOutputBatch.draw(staticFrameBufferTexture, 0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT, 0, 0, 1, 1);
        
        staticOutputBatch.end();

    }

    public void dispose() {
        for (Map.Entry<String, Texture> entry : spriteMap.entrySet()) {
            entry.getValue().dispose();
        }

        font.dispose();

        spriteMap.clear();
        staticBatch.dispose();
        batch.dispose();
        gameShader.dispose();
        screenShader.dispose();
        gameFrameBuffer.dispose();
        staticFrameBuffer.dispose();
        outputBatch.dispose();
        staticOutputBatch.dispose();
    }

    public void resizedWindow(int width, int height) {
        if (width == 0 || height == 0) {
            return;
        }

        aspectRatio = SCREEN_WIDTH / SCREEN_HEIGHT;

        viewPort.update(width, height);
        staticViewPort.update(width, height);

        gameFrameBuffer.dispose();
        gameFrameBuffer = createFrameBuffer(width, height);
        
        staticFrameBuffer.dispose();
        staticFrameBuffer = createFrameBuffer(width, height);

        // shader.setUniformf("aspectRatio", aspectRatio);
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
