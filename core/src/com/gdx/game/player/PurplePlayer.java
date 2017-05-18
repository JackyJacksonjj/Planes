package com.gdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.gdx.game.PurpleLampGame;
import com.gdx.game.player.weapon.Gun;
import com.gdx.game.utils.TimerTool;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.gdx.game.PurpleLampGame.PPM;

/**
 * PurplePlayer = just a class representing player
 */
public class PurplePlayer {

    private static final int STANDARD_VELOCITY = 30;
    private static final String TAG = PurplePlayer.class.getSimpleName();
    private static TextureAtlas playerAtlas;
    private PurpleLampGame game;
    private Gun gun;
    private Sprite sprite;
    private Body body;
    private boolean blink;
    private float xPosition;
    private long deadTime = -1;
    private int speedBoost = 1;

    public PurplePlayer(Sprite sprite, PurpleLampGame game) {
        this.sprite = sprite;
        init(game);
    }

    public static PurplePlayer createPlayer(PurpleLampGame game) {
        PurplePlayer player = new PurplePlayer(getAtlas().createSprite("purple_space_ship"), game);
        player.game = game;
        player.gun = Gun.createGun(game, Gun.BULLET_SIZE_NORMAL, 1, 1);
        return player;
    }

    public static TextureAtlas getAtlas() {
        if (playerAtlas == null) {
            playerAtlas = new TextureAtlas("ships.txt");
        }
        return playerAtlas;
    }

    private void init(PurpleLampGame game) {
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Vector2 pos = new Vector2((Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2) / PPM,  5);
        bodyDef.position.x = pos.x;
        bodyDef.position.y = pos.y;
        bodyDef.fixedRotation = true;
        body = game.getWorld().createBody(bodyDef);

        PolygonShape shipShape = new PolygonShape();
        shipShape.setAsBox(sprite.getWidth() / 2 / PPM, sprite.getHeight() / 2 / PPM);

//        FixtureDef fixtureDefinition = new FixtureDef();
//        fixtureDefinition.shape = shipShape;

        body.createFixture(shipShape, 4);
        setDefaultPosition();
    }

    private void setDefaultPosition() {
        if (sprite.getTexture() == null) {
            throw new RuntimeException("No ship texture (image) given");
        }

//        xPosition = Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2;
//        body.getPosition().x = xPosition = xPosition / PPM;
//        body.getPosition().y = 1.5f * PPM;
    }

    public void dead() {
        deadTime = System.currentTimeMillis();
        blink = true;
        setDefaultPosition();

        final ScheduledFuture future = TimerTool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                blink = !blink;
            }
        }, 0, 250, TimeUnit.MILLISECONDS);

        TimerTool.schedule(new Runnable() {
            @Override
            public void run() {
                future.cancel(true);
                blink = false;
                deadTime = -1;
            }
        }, 5, TimeUnit.SECONDS);
    }

    public void speedUp() {
        if (speedBoost == 25) {
            speedBoost = 1;
            return;
        }
        speedBoost += 1;
    }

    public void draw(Batch batch) {
        if (!batch.isDrawing()) {
            throw new RuntimeException("Batch is not drawing. Doesn't look like this method should to be called.");
        }

        int fingerX = Gdx.input.getX();

        if (deadTime == -1 && Gdx.input.isTouched(0)) {
//            Touch screen implementation

            if (Math.abs(body.getWorldCenter().x * PPM - fingerX) != 0) {
                Vector2 pos = body.getPosition();

                Vector2 direction = new Vector2((fingerX - sprite.getWidth() / 2f) / PPM, body.getPosition().y);
                direction.sub(body.getWorldCenter());

                float speed = STANDARD_VELOCITY * speedBoost / 5;
                body.setLinearVelocity(direction.scl(speed));

                if (fingerX - sprite.getWidth() <= 0 || fingerX + sprite.getWidth() >= Gdx.graphics.getWidth()) {
                    if (xPosition < 0 || pos.x < 0) {
                        xPosition = 0;
                        pos.x = 0;
                        body.setLinearVelocity(new Vector2(0, 0));
                    } else if (xPosition + sprite.getWidth() >= Gdx.graphics.getWidth() || pos.x >= Gdx.graphics.getWidth()) {
                        xPosition = Gdx.graphics.getWidth() - sprite.getWidth();
                        pos.x = xPosition / PPM;
                        body.setLinearVelocity(new Vector2(0, 0));
                    }
                }
            } else {
                body.setLinearVelocity(new Vector2(0, 0));
            }
        } else {
            body.setLinearVelocity(new Vector2(0, 0));
        }

        if (!blink) {
            batch.draw(sprite, body.getPosition().x * PPM - sprite.getWidth() / 2, body.getPosition().y * PPM - sprite.getWidth() / 2);
        }

        gun.shoot(this, batch);
    }

    public Vector2 getCenterBodyPosition() {
        return body.getWorldCenter();
    }
}
