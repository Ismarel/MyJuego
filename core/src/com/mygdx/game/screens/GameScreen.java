package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.sprites.MyPlayer;
import com.mygdx.game.utilities.*;

/**
 * Created by usuario on 27/05/17.
 */

public class GameScreen extends MainScreen {

    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    Viewport gameport;
    World world;

    TextureAtlas atlas;
    MyPlayer player;

    com.mygdx.game.utilities.BackManager backManager;



    public GameScreen(MyGame myGame) {
        super(myGame);
    }

    @Override
    public void show() {
        map = new TmxMapLoader().load("level.tmx");
        atlas = new TextureAtlas("playeratlas.atlas");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGame.PPM);
        camera = new OrthographicCamera();
        gameport = new StretchViewport(MyGame.WIDHTGAME / MyGame.PPM, MyGame.HEIGHTGAME / MyGame.PPM, camera);
        camera.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -9.81f),true);

        CreateLayers createLayers = new CreateLayers();
        createLayers.buildShapes(map, world, "objground", "objground");

        player = new MyPlayer(world, atlas);

        backManager = new com.mygdx.game.utilities.BackManager(camera);

    }

    private void handler(float dt){
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.jump();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.getBody().getLinearVelocity().x <= 2)
            player.getBody().applyLinearImpulse(new Vector2(0.1f, 0), player.getBody().getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.getBody().getLinearVelocity().x >= -2)
            player.getBody().applyLinearImpulse(new Vector2(-0.1f, 0), player.getBody().getWorldCenter(), true);
    }

    public void update(float dt){
        world.step(1/60f, 6, 2);
        handler(dt);
        player.update(dt);
        backManager.update(dt);
        camera.update();
        renderer.setView(camera);
        if(player.getBody().getPosition().x >= 2.0142984 && player.getBody().getPosition().x <= 17.20){
            camera.position.x = player.getBody().getPosition().x;
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f,0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        backManager.render(delta, myGame.batch);
        renderer.render();
        myGame.batch.setProjectionMatrix(camera.combined);
        myGame.batch.begin();
        player.draw(myGame.batch);
        myGame.batch.end();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
    }
}
