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
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.shaders.LightingShaderHandler;
import com.mygdx.game.drawing.shaders.ScreenEdgeShaderHandler;
import com.mygdx.game.drawing.shaders.ScreenEffectShaderHandler;
import com.mygdx.game.utils.types.FileUtils;

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

    HashMap<FontSize, BitmapFont> fonts = new HashMap<>();

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

        // loadSpritesInDirectory("assets/sprites");
        loadBakedSprites();

        // load fonts
        for (FontSize font : FontSize.values()) {
            fonts.put(font, loadFont("fonts/3270/3270NerdFont-Regular.ttf", font.pointSize));
        }
    }
    
    private BitmapFont loadFont(String fontPath, int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.classpath(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        
        generator.dispose();
        // BitmapFont font = new BitmapFont();

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



    private final static String[] bakedSprites = {
            "sprites/v1/floor_tile.png",
            "sprites/v1/brick_wall.png",
            "sprites/v1/entities/bullets/bullet.png",
            "sprites/v1/entities/bullets/fire_ball.png",
            "sprites/v1/entities/body_parts/hats/hats_11.png",
            "sprites/v1/entities/body_parts/hats/hats_6.png",
            "sprites/v1/entities/body_parts/hats/hats_7.png",
            "sprites/v1/entities/body_parts/hats/hats_10.png",
            "sprites/v1/entities/body_parts/hats/hats_5.png",
            "sprites/v1/entities/body_parts/hats/hats_4.png",
            "sprites/v1/entities/body_parts/hats/hats_1.png",
            "sprites/v1/entities/body_parts/hats/hats_3.png",
            "sprites/v1/entities/body_parts/hats/hats_2.png",
            "sprites/v1/entities/body_parts/hats/hats_9.png",
            "sprites/v1/entities/body_parts/hats/hats_8.png",
            "sprites/v1/entities/body_parts/legs/legs_9.png",
            "sprites/v1/entities/body_parts/legs/legs_8.png",
            "sprites/v1/entities/body_parts/legs/legs_5.png",
            "sprites/v1/entities/body_parts/legs/legs_4.png",
            "sprites/v1/entities/body_parts/legs/legs_6.png",
            "sprites/v1/entities/body_parts/legs/legs_7.png",
            "sprites/v1/entities/body_parts/legs/legs_3.png",
            "sprites/v1/entities/body_parts/legs/legs_2.png",
            "sprites/v1/entities/body_parts/legs/legs_1.png",
            "sprites/v1/entities/body_parts/hands/hands_0002.png",
            "sprites/v1/entities/enemy/enemy_8.png",
            "sprites/v1/entities/enemy/enemy_9.png",
            "sprites/v1/entities/enemy/enemy_2.png",
            "sprites/v1/entities/enemy/enemy_3.png",
            "sprites/v1/entities/enemy/enemy_1.png",
            "sprites/v1/entities/enemy/enemy_4.png",
            "sprites/v1/entities/enemy/enemy_5.png",
            "sprites/v1/entities/enemy/enemy_7.png",
            "sprites/v1/entities/enemy/enemy_6.png",
            "sprites/v1/visualEffects/blood/blood_3.png",
            "sprites/v1/visualEffects/blood/blood_big_3.png",
            "sprites/v1/visualEffects/blood/blood_big_2.png",
            "sprites/v1/visualEffects/blood/blood_2.png",
            "sprites/v1/visualEffects/blood/blood_big_1.png",
            "sprites/v1/visualEffects/blood/blood_1.png",
            "sprites/v1/visualEffects/blood/blood_5.png",
            "sprites/v1/visualEffects/blood/blood_4.png",
            "sprites/v1/visualEffects/blood/blood_6.png",
            "sprites/v1/visualEffects/blood/blood_7.png",
            "sprites/v1/visualEffects/blood/giblet_2.png",
            "sprites/v1/visualEffects/blood/giblet_3.png",
            "sprites/v1/visualEffects/blood/giblet_1.png",
            "sprites/v1/visualEffects/blood/blood_8.png",
            "sprites/v1/visualEffects/blood/giblet_4.png",
            "sprites/v1/visualEffects/blood/giblet_5.png",
            "sprites/v1/visualEffects/blood/giblet_6.png",
            "sprites/v1/visualEffects/spawner/enemy_spawner_0001.png",
            "sprites/v1/visualEffects/spawner/enemy_spawner_0002.png",
            "sprites/v1/visualEffects/fire/fire_particle_0004.png",
            "sprites/v1/visualEffects/fire/fire_particle_0001.png",
            "sprites/v1/visualEffects/fire/fire_particle_0002.png",
            "sprites/v1/visualEffects/fire/fire_particle_0003.png",
            "sprites/v2/world/bricks/world_0005.png",
            "sprites/v2/world/bricks/world_0011.png",
            "sprites/v2/world/bricks/world_0010.png",
            "sprites/v2/world/bricks/world_0004.png",
            "sprites/v2/world/bricks/world_0012.png",
            "sprites/v2/world/bricks/world_0006.png",
            "sprites/v2/world/bricks/world_0007.png",
            "sprites/v2/world/bricks/world_0013.png",
            "sprites/v2/world/bricks/world_0017.png",
            "sprites/v2/world/bricks/world_0003.png",
            "sprites/v2/world/bricks/world_0002.png",
            "sprites/v2/world/bricks/world_0016.png",
            "sprites/v2/world/bricks/world_0014.png",
            "sprites/v2/world/bricks/world_0015.png",
            "sprites/v2/world/bricks/world_0001.png",
            "sprites/v2/world/bricks/world_0018.png",
            "sprites/v2/world/bricks/world_0024.png",
            "sprites/v2/world/bricks/world_0025.png",
            "sprites/v2/world/bricks/world_0019.png",
            "sprites/v2/world/bricks/world_0026.png",
            "sprites/v2/world/bricks/world_0022.png",
            "sprites/v2/world/bricks/world_0023.png",
            "sprites/v2/world/bricks/world_0021.png",
            "sprites/v2/world/bricks/world_0009.png",
            "sprites/v2/world/bricks/world_0008.png",
            "sprites/v2/world/bricks/world_0020.png",
            "sprites/v2/map/map_tiles_0001.png",
            "sprites/v2/map/map_tiles_0002.png",
            "sprites/v2/gui/health/hud_health_0001.png",
            "sprites/v2/gui/health/hud_health_0002.png",
            "sprites/v2/gui/health/pixel.png",
            "sprites/v2/gui/map/hud_map_tiles_0001.png",
            "sprites/v2/gui/map/hud_map_tiles_0002.png",
            "sprites/v2/gui/map/hud_map_tiles_0003.png",
            "sprites/v2/gui/map/hud_map_tiles_0006.png",
            "sprites/v2/gui/map/hud_map_tiles_0004.png",
            "sprites/v2/gui/map/hud_map_tiles_0005.png",
            "sprites/v2/gui/map/hud_map_0002.png",
            "sprites/v2/gui/map/hud_map_0001.png",
            "sprites/v2/gui/inventory/inventory_0002.png",
            "sprites/v2/gui/inventory/inventory_0003.png",
            "sprites/v2/gui/inventory/inventory_0001.png",
            "sprites/v2/gui/inventory/inventory_0004.png",
            "sprites/v2/gui/inventory/inventory_0005.png",
            "sprites/v2/gui/inventory/inventory_items_0003.png",
            "sprites/v2/gui/inventory/inventory_slot_0004.png",
            "sprites/v2/gui/inventory/inventory_items_0002.png",
            "sprites/v2/gui/inventory/inventory_items_0001.png",
            "sprites/v2/gui/inventory/inventory_items_0005.png",
            "sprites/v2/gui/inventory/inventory_slot_0002.png",
            "sprites/v2/gui/inventory/inventory_slot_0003.png",
            "sprites/v2/gui/inventory/inventory_items_0004.png",
            "sprites/v2/gui/inventory/inventory_items_0006.png",
            "sprites/v2/gui/inventory/inventory_slot_0001.png",
            "sprites/v2/gui/generic/button.png",
            "sprites/v2/gui/healthbar/boss_healthbar_header.png",
            "sprites/v2/gui/healthbar/boss_healthbar_segment_0001.png",
            "sprites/v2/gui/healthbar/boss_healthbar_segment_0002.png",
            "sprites/v2/entities/enemy/body_medium/enemy_body_4.png",
            "sprites/v2/entities/enemy/body_medium/enemy_body_5.png",
            "sprites/v2/entities/enemy/body_medium/enemy_body_7.png",
            "sprites/v2/entities/enemy/body_medium/enemy_body_6.png",
            "sprites/v2/entities/enemy/body_medium/enemy_body_2.png",
            "sprites/v2/entities/enemy/body_medium/enemy_body_3.png",
            "sprites/v2/entities/enemy/body_medium/enemy_body_1.png",
            "sprites/v2/entities/enemy/heads_small/small_enemy_heads_8.png",
            "sprites/v2/entities/enemy/heads_small/small_enemy_heads_9.png",
            "sprites/v2/entities/enemy/heads_small/small_enemy_heads_4.png",
            "sprites/v2/entities/enemy/heads_small/small_enemy_heads_5.png",
            "sprites/v2/entities/enemy/heads_small/small_enemy_heads_7.png",
            "sprites/v2/entities/enemy/heads_small/small_enemy_heads_6.png",
            "sprites/v2/entities/enemy/heads_small/small_enemy_heads_2.png",
            "sprites/v2/entities/enemy/heads_small/small_enemy_heads_3.png",
            "sprites/v2/entities/enemy/heads_small/small_enemy_heads_1.png",
            "sprites/v2/entities/neutral/augments/augments_0001.png",
            "sprites/v2/entities/neutral/augments/augments_0015.png",
            "sprites/v2/entities/neutral/augments/augments_0014.png",
            "sprites/v2/entities/neutral/augments/augments_0016.png",
            "sprites/v2/entities/neutral/augments/augments_0002.png",
            "sprites/v2/entities/neutral/augments/augments_0003.png",
            "sprites/v2/entities/neutral/augments/augments_0017.png",
            "sprites/v2/entities/neutral/augments/augments_0013.png",
            "sprites/v2/entities/neutral/augments/augments_0007.png",
            "sprites/v2/entities/neutral/augments/augments_0006.png",
            "sprites/v2/entities/neutral/augments/augments_0012.png",
            "sprites/v2/entities/neutral/augments/augments_0004.png",
            "sprites/v2/entities/neutral/augments/augments_0010.png",
            "sprites/v2/entities/neutral/augments/augments_0011.png",
            "sprites/v2/entities/neutral/augments/augments_0005.png",
            "sprites/v2/entities/neutral/augments/augments_0008.png",
            "sprites/v2/entities/neutral/augments/augments_0020.png",
            "sprites/v2/entities/neutral/augments/augments_0009.png",
            "sprites/v2/entities/neutral/augments/augments_0019.png",
            "sprites/v2/entities/neutral/augments/augments_0018.png",
            "sprites/v2/entities/neutral/item_boxes/item_boxes_0004.png",
            "sprites/v2/entities/neutral/item_boxes/item_boxes_0002.png",
            "sprites/v2/entities/neutral/item_boxes/item_boxes_0003.png",
            "sprites/v2/entities/neutral/item_boxes/item_boxes_0001.png",
            "sprites/v2/entities/neutral/crafting_machines/machines_0004.png",
            "sprites/v2/entities/neutral/crafting_machines/machines_0001.png",
            "sprites/v2/entities/neutral/crafting_machines/machines_0002.png",
            "sprites/v2/entities/neutral/crafting_machines/machines_0003.png",
            "sprites/v2/entities/neutral/level_exit.png",
            "sprites/v2/entities/projectiles/bullets_0006.png",
            "sprites/v2/entities/projectiles/bullets_0007.png",
            "sprites/v2/entities/projectiles/bullets_0005.png",
            "sprites/v2/entities/projectiles/bullets_0004.png",
            "sprites/v2/entities/projectiles/bullets_0001.png",
            "sprites/v2/entities/projectiles/bullets_0003.png",
            "sprites/v2/entities/projectiles/bullets_0002.png",
            "sprites/v2/entities/projectiles/bullets_0008.png",
            "sprites/v2/entities/player/player_6.png",
            "sprites/v2/entities/player/faces_0001.png",
            "sprites/v2/entities/player/player_2_10.png",
            "sprites/v2/entities/player/player_2_6.png",
            "sprites/v2/entities/player/player_2_7.png",
            "sprites/v2/entities/player/player_7.png",
            "sprites/v2/entities/player/faces_0002.png",
            "sprites/v2/entities/player/player_5.png",
            "sprites/v2/entities/player/player_2_5.png",
            "sprites/v2/entities/player/player_2_4.png",
            "sprites/v2/entities/player/player_4.png",
            "sprites/v2/entities/player/faces_0003.png",
            "sprites/v2/entities/player/player_2_1.png",
            "sprites/v2/entities/player/player_1.png",
            "sprites/v2/entities/player/player_3.png",
            "sprites/v2/entities/player/faces_0004.png",
            "sprites/v2/entities/player/player_2_3.png",
            "sprites/v2/entities/player/player_2_2.png",
            "sprites/v2/entities/player/player_2.png",
            "sprites/v2/entities/player/guns_0006.png",
            "sprites/v2/entities/player/guns_0004.png",
            "sprites/v2/entities/player/guns_0005.png",
            "sprites/v2/entities/player/guns_0001.png",
            "sprites/v2/entities/player/guns_0002.png",
            "sprites/v2/entities/player/guns_0003.png",
            "sprites/v2/entities/player/player_2_9.png",
            "sprites/v2/entities/player/player_2_8.png"
    };

    public void loadBakedSprites() {
        for (String bakedSprite : bakedSprites) {
            FileHandle handle = Gdx.files.internal(bakedSprite);
            loadSprite(handle.path(), handle.nameWithoutExtension());
        }
    }

    public void loadSpritesInDirectory(String path) {
        /*
        // This bs is here because of libgdx file loading restrictions
        // can't dynamically load files from the jar for some reason
        System.out.println(FileUtils.bakeFilePaths(path));

        FileHandle handle = Gdx.files.internal(path);

        for (FileHandle f : handle.list()) {
            if (!f.isDirectory()) {

                loadSprite(f.path(), f.nameWithoutExtension());
            } else {
                loadSpritesInDirectory(f.path());
            }
        }*/
    }

    private void loadSprite(String path, String name) {
        if (name.isEmpty()) {
            return;
        }

        this.spriteMap.put(name, new Texture(path));
    }


    private void drawSprite(SpriteBatch batch, String spriteName, float x, float y, float width, float height, boolean flipHorizontally, boolean flipVertically, float rotationRad, float r, float g, float b, float a) {
        Texture sprite = spriteMap.getOrDefault(spriteName, null);

        if (sprite == null) {
            System.out.println("Requested sprite with name " + spriteName + " but it was not found");
            // System.exit(-1); // c ah crash
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

        BitmapFont font = fonts.get(command.fontSize);

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


        for (BitmapFont font : fonts.values()) {
            font.dispose();
        }

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



    


}
