package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.GameScreen;

/**
 * Created by usuario on 27/05/17.
 */

public class MyGame extends Game{

    public static final float PPM =100;
    public static final int WIDHTGAME =400;
    public static final int HEIGHTGAME =208;


    public SpriteBatch batch;


    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new GameScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }

    @Override
    public void render() {
        super.render();
    }
}
