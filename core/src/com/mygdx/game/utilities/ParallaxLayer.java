package com.mygdx.game.utilities;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by usuario on 4/06/17.
 */

public class ParallaxLayer {
    private Texture region;
    private float verticalposition;
    private float speed;

    public ParallaxLayer(Texture region, float speed, float verticalposition) {
        this.region = region;
        this.speed = speed;
        this.verticalposition = verticalposition;
    }

    public Texture getRegion() {
        return region;
    }

    public float getSpeed() {
        return speed;
    }

    public float getVerticalposition() {
        return verticalposition;
    }
}
