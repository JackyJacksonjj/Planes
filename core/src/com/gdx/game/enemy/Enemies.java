package com.gdx.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gdx.game.PurpleLampGame;
import com.gdx.game.player.PurplePlayer;
import com.gdx.game.utils.TimerTool;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.gdx.game.PurpleLampGame.PPM;

/**
 * Created by jenea on 5/13/17.
 */
public class Enemies {

    public static final String ENEMY_SHIP = "eship";
    private static ArrayList<Body> enemies1 = new ArrayList<>();
    private static ArrayList<Body> enemies2 = new ArrayList<>();
    private static TextureAtlas textureAtlas;
    private Future schedule;
    private PurpleLampGame game;
    private Random random = new Random();
    private boolean spawnNow = true;
    private Sprite sprite1;
    private Sprite sprite2;

    private Enemies() {
    }

    public static Enemies startSpawn(PurpleLampGame game) {
        if (textureAtlas == null) {
            textureAtlas = new TextureAtlas(Gdx.files.internal("enemy.txt"));
        }
        Enemies e = new Enemies();
        e.game = game;
        e.sprite1 = textureAtlas.createSprite("enemy1");
        e.sprite2 = textureAtlas.createSprite("enemy2");
        e.schedule();
        return e;
    }

    public static void removeBody(Body x) {
        enemies1.remove(x);
        enemies2.remove(x);
    }

    private void schedule() {
        if (schedule == null) {
            schedule = TimerTool.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    spawnNow = true;
                }
            }, 0, 1000, TimeUnit.MILLISECONDS);
        }
    }

    public void spawn(Batch batch) {
        if (spawnNow) {
            spawnNow = false;
            createEnemy();
        }

        render(batch);
    }

    private void render(Batch batch) {
        if (batch.isDrawing()) {
            for (Body body : enemies1)
                batch.draw(sprite1, body.getPosition().x * PPM - sprite1.getWidth() / 2, body.getPosition().y * PPM - sprite1.getHeight() / 2);

            for (Body body : enemies2)
                batch.draw(sprite2, body.getPosition().x * PPM - sprite2.getWidth() / 2, body.getPosition().y * PPM - sprite2.getHeight() / 2);
        }
    }

    private Body createEnemy(World world, Vector2 position, Vector2 velocity, boolean isEnemy1) {
        Sprite sprite = isEnemy1 ? sprite1 : sprite2;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = position.x;
        bodyDef.position.y = position.y;
        bodyDef.fixedRotation = true;
        bodyDef.linearVelocity.add(velocity);
        Body body = world.createBody(bodyDef);
        body.setUserData(ENEMY_SHIP);
        PolygonShape shipShape = new PolygonShape();
        shipShape.setAsBox(sprite.getWidth() / 2 / PPM, sprite.getHeight() / 2 / PPM);
        body.createFixture(shipShape, 4);
        return body;
    }

    private void createEnemy() {
        Vector2 enemyPosition = new Vector2(random.nextInt(Gdx.graphics.getWidth() - 120)/PPM, Gdx.graphics.getHeight()/PPM + 100/PPM);
        Vector2 velocity = new Vector2(0, -100 / PPM);
        if (random.nextBoolean()) {
            enemies1.add(game.addBody(createEnemy(game.getWorld(), enemyPosition, velocity, true)));
        } else {
            enemies2.add(game.addBody(createEnemy(game.getWorld(), enemyPosition, velocity, false)));
        }
    }
}
