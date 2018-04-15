package com.mygdx.game.utilities;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGame;

import java.util.ArrayList;

/**
 * Created by usuario on 27/05/17.
 */

public class CreateLayers {

    public ArrayList<Body> buildShapes(Map map, World world, String name, String data){

        MapObjects objects = map.getLayers().get(name).getObjects();
        ArrayList<Body> bodies = new ArrayList<Body>();
        for(MapObject obj : objects){
            if (obj instanceof TextureMapObject){
                continue;
            }
            Shape shape;
            if (obj instanceof RectangleMapObject){
                shape = getRectangle((RectangleMapObject) obj);
            }else if (obj instanceof PolygonMapObject){
                shape = getPolygon((PolygonMapObject) obj);
            }else if (obj instanceof PolylineMapObject){
                shape = getPolyline((PolylineMapObject) obj);
            }else if(obj instanceof CircleMapObject){
                shape = getCircle((CircleMapObject)obj);
            }else {
                continue;
            }
            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bd);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.density= 1f;
            fixtureDef.shape =shape;
            body.createFixture(fixtureDef).setUserData(data);
            bodies.add(body);
            shape.dispose();
        }

        return bodies;
    }

    private PolygonShape getRectangle(RectangleMapObject rectangleMapObject){
        Rectangle rectangle = rectangleMapObject.getRectangle();
        PolygonShape shape = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / MyGame.PPM, (rectangle.y + rectangle.height * 0.5f)/MyGame.PPM);

        shape.setAsBox(rectangle.width * 0.5f / MyGame.PPM, rectangle.height * 0.5f / MyGame.PPM, size, 0.0f);

        return shape;
    }

    private CircleShape getCircle(CircleMapObject circleMapObject){
        Circle circle = circleMapObject.getCircle();
        CircleShape shape = new CircleShape();
        shape.setRadius(circle.radius / MyGame.PPM);
        circle.setPosition(new Vector2(circle.x / MyGame.PPM, circle.y / MyGame.PPM));
        return shape;
    }

    private PolygonShape getPolygon(PolygonMapObject polygonMapObject){
        PolygonShape shape = new PolygonShape();
        float [] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        float [] worldvertices = new float[vertices.length];
        for(int i = 0; i < vertices.length; i++){
            worldvertices[i] = vertices[i] / MyGame.PPM;
        }
        shape.set(worldvertices);
        return shape;
    }

    private ChainShape getPolyline (PolylineMapObject polylineMapObject){
        float[] vertices = polylineMapObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length/2];

        for(int i = 0; i < vertices.length / 2; i++){
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / MyGame.PPM;
            worldVertices[i].y = vertices[i * 2 + 1] / MyGame.PPM;
        }

        ChainShape shape = new ChainShape();
        shape.createChain(worldVertices);
        return shape;
    }

}
