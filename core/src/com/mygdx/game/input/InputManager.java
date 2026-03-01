package com.mygdx.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Managers;
import com.mygdx.game.utils.types.NumberUtils;

public class InputManager {
    private static InputManager instance;

    public static InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }

    Vector2 lastMousePos = new Vector2(0f, 0f);
    Vector2 bufferedAimDir = new Vector2(1f, 0f);
    boolean usingKeyboardAim = false;

    /**
     * Returns a normalized vector for player movement input.
     * WASD for pc, Left stick on controller
     */
    public Vector2 getMovementInput() {
        Vector2 output = new Vector2(0f, 0f);

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            output.x -= 1.0f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            output.x += 1.0f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            output.y += 1.0f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            output.y -= 1.0f;
        }


        return normalizeVector(output);
    }

    private Vector2 normalizeVector(Vector2 input) {
        float length = NumberUtils.distance(0f, 0f, input.x, input.y);

        return new Vector2(input.x / length, input.y / length);
    }

    public boolean pressedOpenInventory() {
        return Gdx.input.isKeyJustPressed(Input.Keys.E);
    }

    /**
     * Returns a vector with the shooting direction
     * originX and originY are used when calculating mouselook direction.
     */
    public Vector2 getShootingDirection(float originX, float originY) {
        Vector2 mousePos = Managers.drawingManager.getMousePosition();
        Vector2 output = new Vector2(0f, 0f);


        // I FUCKING HATE INTELLIJ. WHAT A PIECE OF SHIT
        // WHY CAN'T I SET THE INDENT TO BE CONSISTENT
        // WHY DOES IT DEFAULT TO THIS GARBAGE
        // WHO IS THIS FOR?


        if (!usingKeyboardAim) {
            output.x = mousePos.x - originX;
            output.y = mousePos.y - originY;
        } else {
            if (
                Gdx.input.isKeyPressed(Input.Keys.LEFT)
            ) {
                output.x -= 1f;
            }

            if (
                    Gdx.input.isKeyPressed(Input.Keys.RIGHT)
            ) {
                output.x += 1f;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                output.y += 1.0f;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                output.y -= 1.0f;
            }

            if (output.x == 0 && output.y == 0) {
                output = bufferedAimDir;
            }

        }

        lastMousePos = mousePos;
        bufferedAimDir = normalizeVector(output);

        return bufferedAimDir;
    }

    public boolean isFireButtonPressed() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
                Gdx.input.isKeyPressed(Input.Keys.UP) ||
                Gdx.input.isKeyPressed(Input.Keys.DOWN)
        ) {
            usingKeyboardAim = true;
            return true;
        }


        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            usingKeyboardAim = false;
            return true;
        }


        return false;

    }

    public boolean isDashButtonPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }

}
