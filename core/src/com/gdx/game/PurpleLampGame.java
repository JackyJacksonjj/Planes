package com.gdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.enemy.Enemies;
import com.gdx.game.player.weapon.Gun;
import com.gdx.game.screen.IntroScreen;
import com.gdx.game.screen.MainScreenBeta;

import java.util.ArrayList;

import static com.gdx.game.enemy.Enemies.ENEMY_SHIP;

public class PurpleLampGame extends Game implements ContactListener {

    public static final int PPM = 20;
    public static boolean DEBUG = false;
    public static final String WALL = "wall-ie";
    private ArrayList<Body> aliveBodies = new ArrayList<Body>(100);
    private ArrayList<Body> deadBodies = new ArrayList<Body>(100);
    private Viewport viewport;
    private Batch batch;
    private OrthographicCamera camera;
    private Box2DDebugRenderer box2DDebugRenderer;
    private World world;

    public World getWorld() {
        return world;
    }

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);

        world = new World(new Vector2(0, 0), true);

        world.setContactListener(this);
        batch = new SpriteBatch();

        box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.setDrawVelocities(true);
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        // Left
        addBorderWith(1,
                (Gdx.graphics.getHeight() + 300) / 2 / PPM,
                (Gdx.graphics.getHeight() / 2 / PPM) + 60 / PPM,
                1 / PPM);
        // Right
        addBorderWith((Gdx.graphics.getWidth() - 20) / PPM ,
                (Gdx.graphics.getHeight() + 300) / 2 / PPM,
                (Gdx.graphics.getHeight() / 2 / PPM) + 60 / PPM,
                1 / PPM);
        // Bottom
        addBorderWith((Gdx.graphics.getWidth()) / 2 / PPM,
                60 / PPM,
                1 / PPM,
                (Gdx.graphics.getWidth() / 2 / PPM) - 1);
        // Top
        addBorderWith((Gdx.graphics.getWidth()) / 2 / PPM,
                (Gdx.graphics.getHeight() + 150) / PPM,
                1 / PPM,
                (Gdx.graphics.getWidth() / 2 / PPM) - 1);
        setScreen(new IntroScreen(this, "font/3Dventure.ttf"));
    }

    public void update(float deltaTime) {
        world.step(1 / 60f, 6, 2);
        cameraUpdate(deltaTime);
        inputUpdate(deltaTime);
    }

    public Batch getBatch() {
        return batch;
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        update(Gdx.graphics.getDeltaTime());
        getScreen().render(Gdx.graphics.getDeltaTime());

        for (Body x : deadBodies) {
//            aliveBodies.remove(x);
            Gun.removeBody(x);
            Enemies.removeBody(x);
            world.destroyBody(x);
        }

        deadBodies.clear();

        if (DEBUG) {
            box2DDebugRenderer.render(world, camera.combined);
        }
        batch.end();
    }

    public void start() {
        setScreen(new MainScreenBeta(this));
    }

    public void cameraUpdate(float deltaTime) {
        //Currently it is working with Tiled map.
//        Vector3 position = camera.position;
//        float playersX = player.getPosition().x * PPM;
//        if(player != null) {
//            float lerp = 0.75f;
//
//            if(playersX - Gdx.graphics.getWidth()/2 <= 0 && playersX <= Gdx.graphics.getWidth()/2){
//                lerp = 0;
//            } else if(playersX > ((GameLevelOne) getScreen()).getMapWidth() - Gdx.graphics.getWidth()/2
//                    && playersX >= ((GameLevelOne) getScreen()).getMapWidth() - Gdx.graphics.getWidth()/2 - 1)
//                lerp = 0;
//
//            position.x += (playersX - position.x) * lerp * deltaTime;
//            if(Math.round(position.x) - position.x < 0.5)
//                position.x += Math.round(position.x) - position.x - 0.5;
//        }
//
//        camera.position.set(position);
//
//        camera.update();
//
//        if(getScreen() instanceof GameLevelOne)
//            ((GameLevelOne) getScreen()).getMapRenderer().setView(camera);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void inputUpdate(float deltaTime) {


//        if (player == null)
//            return;
//
//        Vector2 pos = player.getPosition();
//        pos.x = pos.x * PPM;
//
////        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
////            player.applyForceToCenter(0, 1800, false);
////        }
//
//        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
//            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) pos.x -= 250 * Gdx.graphics.getDeltaTime();
//            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) pos.x += 250 * Gdx.graphics.getDeltaTime();
//        }
//
//        if (pos.x < 64) pos.x = 64;
//        if (pos.x > ((GameLevelOne) getScreen()).getMapWidth() - 64)
//            pos.x = ((GameLevelOne) getScreen()).getMapWidth() - 64;
//
//        player.setTransform(pos.x / PPM, pos.y, 0);

//		if(jump) {
//			jump = false;
//			if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//				player.setLinearVelocity(vel.x, 0);
//				System.out.println("jump before: " + player.getLinearVelocity());
//				player.setTransform(pos.x, pos.y + 0.01f, 0);
//				player.applyLinearImpulse(0, 30, pos.x, pos.y,false);
//				System.out.println("jump, " + player.getLinearVelocity());
//			}
//		}
    }

    private void addBorderWith(int x, int y, int heightInPpm, int widthInPpm) {
        BodyDef box = new BodyDef();
        box.type = BodyDef.BodyType.StaticBody;
        Vector2 pos = new Vector2(x, y);
        box.position.x = pos.x;
        box.position.y = pos.y;

        Body body = world.createBody(box);
        body.setActive(true);
        body.setUserData(WALL);

        PolygonShape line = new PolygonShape();
        line.setAsBox(widthInPpm, heightInPpm);
        FixtureDef wall = new FixtureDef();
        wall.shape = line;
        wall.density = 1;
        body.createFixture(wall);
    }

    @Override
    public void beginContact(Contact contact) {
        didBulletHitSomething(contact.getFixtureA().getBody(), contact.getFixtureB().getBody());
    }

    private void didBulletHitSomething(Body a, Body b) {
        if (a.getUserData() == null)
            return;
        if (b.getUserData() == null)
            return;

        if (a.getUserData().equals(Gun.BULLET)) {
            if (b.getUserData().equals(WALL) || b.getUserData().equals(ENEMY_SHIP)) {
                System.out.println("b.getUserData().equals(WALL) || b.getUserData().equals(ENEMY_SHIP)");
                deadBodies.add(a);
            }
        } else if (b.getUserData().equals(Gun.BULLET)) {
            if (a.getUserData().equals(WALL) || a.getUserData().equals(ENEMY_SHIP)) {
                System.out.println("a.getUserData().equals(WALL) || a.getUserData().equals(ENEMY_SHIP)");
                deadBodies.add(b);
            }
        }
    }

    public Body addBody(Body body) {
//        aliveBodies.add(body);
        return body;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
