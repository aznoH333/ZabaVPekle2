package com.mygdx.game.gameStates.implementations;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.components.behaviour.PlayerSoul;
import com.mygdx.game.entities.components.gui.Button;
import com.mygdx.game.entities.components.gui.Hover;
import com.mygdx.game.entities.components.gui.Text;
import com.mygdx.game.gameStates.GameState;
import com.mygdx.game.gameStates.GameStateManager;

public class MainMenu extends GameState {
    private static final EntityManager entityManager = EntityManager.getInstance();
    private static final GameStateManager gameStateManager = GameStateManager.getInstance();


    public MainMenu() {
        super("main menu");
    }

    @Override
    public void initializeState() {



        // play game button
        Text text =  new Text("Play game"); // I fucking despise this. But it works
        entityManager.addEntity(
                new Entity()
                        .makeStatic()
                        .setY(25f)
                        .setHeight(16f)
                        .setWidth(128f)
                        .addComponent(
                               text
                        ).addComponent(new Button(()->{
                            gameStateManager.switchState("game");
                        })).addComponent(new Hover(
                                ()->{
                                    text.color.b = 0f;
                                },
                                ()->{
                                    text.color.b = 1f;
                                }
                        ))
        );


        // quit game button
        Text text2 =  new Text("Quit game"); // I fucking despise this. But it works

        entityManager.addEntity(
                new Entity()
                        .makeStatic()
                        .setY(-25f)
                        .setHeight(16f)
                        .setWidth(128f)
                        .addComponent(
                                text2
                        ).addComponent(new Button(()->{
                            Gdx.app.exit();
                        })).addComponent(new Hover(
                                ()->{
                                    text2.color.b = 0f;
                                },
                                ()->{
                                    text2.color.b = 1f;
                                }
                        ))
        );
    }

    @Override
    public void cleanUpState() {

    }
}
