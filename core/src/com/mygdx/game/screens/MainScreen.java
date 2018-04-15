package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.mygdx.game.MyGame;

/**
 * Created by usuario on 27/05/17.
 */

public abstract class MainScreen implements Screen {
    protected MyGame myGame;

    public MainScreen(MyGame myGame){
        this.myGame = myGame;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
