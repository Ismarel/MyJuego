package com.mygdx.game.utilities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGame;
import com.mygdx.game.sprites.MyPlayer;


/**
 * Created by usuario on 4/06/17.
 */

public class ParallaxBackground {

    private ParallaxLayer[] layers;
    private Camera camera;
    private Camera camscreen;
    private float currentX;

    public ParallaxBackground(ParallaxLayer[] layers, float widht, float height, Camera camscreen) {
        this.layers = layers;
        this.camera = new OrthographicCamera(widht, height);
        this.camscreen = camscreen;
    }

    public void update(float dt){

    }

    public void render(SpriteBatch batch){
        batch.setProjectionMatrix(camera.projection);
        batch.begin();

        for(int i = 0; i < layers.length; i++){
            layers[i].getRegion().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            currentX = -camscreen.position.x * layers[i].getSpeed();
            if (i == 0){
                batch.draw(layers[i].getRegion(), -MyGame.WIDHTGAME / 2,  -MyGame.HEIGHTGAME + layers[i].getVerticalposition(), (int)-currentX, 0, layers[i].getRegion().getWidth(), layers[i].getRegion().getHeight()+100);
            }else {

                batch.draw(layers[i].getRegion(), -MyGame.WIDHTGAME / 2,  -MyGame.HEIGHTGAME + layers[i].getVerticalposition() , (int)-currentX, 0, layers[i].getRegion().getWidth(), layers[i].getRegion().getHeight());
            }
        }
        batch.end();
    }
}
