package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;

/**
 * Created by usuario on 27/05/17.
 */

public class MyPlayer extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNIG};
    private State currentState;
    private State previusstate;
    private World world;
    private Body body;
    private TextureRegion stand;
    private Animation runn;
    private Animation jump;
    private float timerstate;
    private boolean isright;

    public MyPlayer(World world, TextureAtlas atlas){
        this.world = world;
        currentState = State.STANDING;
        previusstate = State.STANDING;
        timerstate = 0;
        isright = true;
        stand = new TextureRegion(atlas.findRegion("standharry"));
        defineBody();
        setBounds(0, 0, 16 / MyGame.PPM, 16 / MyGame.PPM );
        TextureRegion runharry = new TextureRegion(atlas.findRegion("runharry"));
        TextureRegion jumpharry = new TextureRegion(atlas.findRegion("jumpharry"));

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 0; i < 5; i++){
            frames.add(new TextureRegion(runharry, i * 16, 0, 16, 21));
        }
        runn = new Animation(0.1f, frames);
        frames.clear();
        for (int i = 0; i < 2; i++){
            frames.add(new TextureRegion(jumpharry, i * 16, 0, 16, 21));
        }
        jump = new Animation(0.1f, frames);
        frames.clear();

        setRegion(stand);
    }

    public void update(float dt){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y -getHeight() / 2);
        setRegion(getFrame(dt));
    }

    private TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;

        switch (currentState){
            case JUMPING:
                region = (TextureRegion) jump.getKeyFrame(timerstate);
                break;
            case RUNNIG:
                region =(TextureRegion) runn.getKeyFrame(timerstate, true);
                break;
            case STANDING:
            case FALLING:
                default:
                region = stand;
                break;
        }
        if((body.getLinearVelocity().x < 0 || !isright) && !region.isFlipX()){
            region.flip(true, false);
            isright =false;
        }else if ((body.getLinearVelocity().x >0 || isright) && region.isFlipX()){
            region.flip(true,false);
            isright = true;
        }

        timerstate = currentState == previusstate ? timerstate +dt: 0;
        previusstate = currentState;
        return region;
    }

    private State getState(){
        if(body.getLinearVelocity().y > 0 || body.getLinearVelocity().y < 0 && previusstate == State.JUMPING){
            return State.JUMPING;
        }else if(body.getLinearVelocity().y < 0){
            return State.FALLING;
        }else if(body.getLinearVelocity().x != 0){
            return State.RUNNIG;
        }else {
            return  State.STANDING;
        }
    }

    private void defineBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / MyGame.PPM, 32 / MyGame.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        FixtureDef  fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyGame.PPM);
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);
    }

    public Body getBody() {
        return body;
    }

    public void  jump(){
        if(currentState != State.JUMPING){
            body.applyLinearImpulse(new Vector2(0, 4f), body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }
}
