package com.gdx.game.player.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gdx.game.PurpleLampGame;
import com.gdx.game.player.PurplePlayer;
import com.gdx.game.utils.TimerTool;

import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.gdx.game.PurpleLampGame.PPM;

/**
 * Created by jenea on 4/23/17.
 */
public class Gun {

    public static final String BULLET = "BULLET";

    public static final int BULLET_SIZE_NORMAL = 0;
    public static final int BULLET_SIZE_MID = 1;
    public static final int BULLET_SIZE_BIG = 2;
    private static Sprite shot;
    private boolean shootNow = false;

    // Shots per second. Diapason - (0,1]
    private float frequency;
    private int fireLines;
    private int size;
    private PurpleLampGame game;
    private Future schedule;
    private static ArrayList<Body> bullets = new ArrayList<>();

    public static Gun createGun(PurpleLampGame game, int bulletSize, int frequency, int rows) {
        Gun g = new Gun();
        if (shot == null) {
            shot = new Sprite(new Texture(Gdx.files.internal("shot_small.png")));
            shot.rotate90(false);
        }
        g.game = game;
        g.size = bulletSize;
        g.frequency = frequency;
        g.fireLines = rows;
        g.schedule();
        return g;
    }

    private static Body createBullet(World world, Vector2 bulletPosition, Vector2 velocity) {
        BodyDef bulletBodyDef;
        FixtureDef bulletFixtureDef;
        bulletBodyDef = new BodyDef();
        bulletBodyDef.type = BodyDef.BodyType.DynamicBody;
        PolygonShape bulletShape = new PolygonShape();
        bulletShape.setAsBox(1, 1);
        bulletFixtureDef = new FixtureDef();
        bulletFixtureDef.shape = bulletShape;
        bulletFixtureDef.density = 1;
        bulletFixtureDef.friction = 0.1f;
        bulletBodyDef.fixedRotation = true;
        bulletBodyDef.linearVelocity.add(velocity);
        bulletBodyDef.position.x = bulletPosition.x;
        bulletBodyDef.position.y = bulletPosition.y;
        Body bullet = world.createBody(bulletBodyDef);
        bullet.setUserData(BULLET);
        bullet.createFixture(bulletFixtureDef);
        return bullet;
    }

    private void schedule() {
        if (schedule == null) {
            schedule = TimerTool.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    shootNow = true;
                }
            }, 0, 250, TimeUnit.MILLISECONDS);
        }
    }

    private void refreshSchedule() {
        if (schedule == null) {
            schedule();
            return;
        }

        schedule.cancel(true);
        schedule();
    }

    public void shoot(PurplePlayer purplePlayer, Batch batch) {
        if (shootNow) {
            shootNow = false;
            createBullets(purplePlayer);
        }

        render(batch);
    }

    public static void removeBody(Body x) {
        bullets.remove(x);
    }

    private void render(Batch batch) {
        if (batch.isDrawing())
            for (Body body : bullets)
                batch.draw(shot, body.getPosition().x * PPM - shot.getWidth() / 2, body.getPosition().y * PPM - shot.getWidth() / 2);
    }

    private void createBullets(PurplePlayer purplePlayer) {
        Vector2 bulletPosition = new Vector2(purplePlayer.getCenterBodyPosition());
        bulletPosition.add(new Vector2(0, 80 / PPM));
        Vector2 velocity = new Vector2(0, 200 / PPM);
        for (int i = 0; i < fireLines; i++) {
            bullets.add(game.addBody(createBullet(game.getWorld(), bulletPosition, velocity)));
        }
    }
}
