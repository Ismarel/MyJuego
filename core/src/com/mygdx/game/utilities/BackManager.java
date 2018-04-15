package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGame;
import com.mygdx.game.sprites.MyPlayer;

/**
 * Created by usuario on 4/06/17.
 */

public class BackManager {
    private Texture montain, fog, blacktrees, trees, snowmountain;

    private Camera camscreen;
    private ParallaxLayer[] mlayer;
    private ParallaxBackground mbackground;

    public BackManager(Camera camscreen){

        this.camscreen = camscreen;
        this.montain = new Texture(Gdx.files.internal("Arrow-background.png"));
        this.fog = new Texture(Gdx.files.internal("fog.png"));
        this.blacktrees = new Texture(Gdx.files.internal("blacktrees.png"));
        this.trees = new Texture(Gdx.files.internal("trees.png"));
        this.snowmountain = new Texture(Gdx.files.internal("snowymountains.png"));

        mlayer = new ParallaxLayer[3];
        mlayer[0] = new ParallaxLayer(montain,1, 0);
        mlayer[1] = new ParallaxLayer(trees,20, 50);
        mlayer[2] = new ParallaxLayer(fog,50, 150);
        //mlayer[3] = new ParallaxLayer(trees,5);
        //mlayer[1] = new ParallaxLayer(snowmountain,100);

        mbackground = new ParallaxBackground(mlayer, MyGame.WIDHTGAME, MyGame.HEIGHTGAME + 150, camscreen);
    }

    public void render(float dt, SpriteBatch batch){
        mbackground.render(batch);
    }

    public void update(float dt){
        mbackground.update(dt);
    }

}
