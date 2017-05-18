package com.gdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.game.PurpleLampGame;
import com.gdx.game.enemy.Enemies;
import com.gdx.game.player.PurplePlayer;
import com.gdx.game.ui.stage.PurpleStage;

public class MainScreenBeta extends PurpleGameScreen {
    PurpleStage stage;
    private SpriteBatch batch;
    private TextureAtlas buttonsAtlas;
    private PurplePlayer purplePlayer;
    private Enemies enemies;

    public MainScreenBeta(PurpleLampGame game) {
        super(game);
        purplePlayer = PurplePlayer.createPlayer(game);
        enemies = Enemies.startSpawn(game);
    }

    private void setupStage() {
        batch = new SpriteBatch();
        stage = new PurpleStage("hubble_spiral_galaxy_m74.jpg");
        stage.clear();

        Gdx.input.setInputProcessor(stage);

        buttonsAtlas = new TextureAtlas("jet_button.txt");

        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas); // There is an ability to use custom fonts

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(); //** Button properties **//
        style.up = buttonSkin.getDrawable("jet_fighter");
        style.down = buttonSkin.getDrawable("jet_fighter_pressed");

        ImageButton button = new ImageButton(style);
        button.setX(Gdx.graphics.getWidth() - (style.up.getMinWidth() + 20));
        button.setX(Gdx.graphics.getHeight() - (style.up.getMinHeight() + 20));
        button.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    PurpleLampGame.DEBUG = !PurpleLampGame.DEBUG;
//                purplePlayer.dead();
            }
        });
        stage.addActor(button);
    }

    @Override
    public void show() {
        setupStage();
    }

    @Override
    public void render(float delta) {
        Batch batch = stage.draw(true);
        purplePlayer.draw(batch);
        enemies.spawn(batch);
        batch.end();

//        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
//                game.setScreen(new IntroScreen(game));
//        }
    }

    @Override
    public void hide() {
        Gdx.app.debug("Cubocy", "dispose main menu");
        stage.dispose();
    }

    @Override
    public void dispose() {
        //clean resources
    }
}