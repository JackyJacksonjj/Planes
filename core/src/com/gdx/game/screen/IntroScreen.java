package com.gdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gdx.game.PurpleLampGame;

import static com.gdx.game.PurpleLampGame.DEBUG;
import static com.gdx.game.PurpleLampGame.PPM;

/**
 * Intro screen with title
 */
public class IntroScreen implements Screen {

    //    PurpleLabel label2;
    private BitmapFont bitmapFont;
    private Stage stage;
    private Table table;
    private PurpleLampGame game;

    public IntroScreen(PurpleLampGame game, String fontPath) {
        this.game = game;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = PPM * 4;
        parameter.color = Color.CHARTREUSE;
        bitmapFont = generator.generateFont(parameter); // font size PPM * 4 pixels
        generator.dispose();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setDebug(DEBUG);
        table.pad(PPM);

        setupTable();
    }

    private void setupTable() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        TextureRegion upRegion = atlas.findRegion("default-round");
        TextureRegion downRegion = atlas.findRegion("default-round-down");

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(upRegion);
        style.down = new TextureRegionDrawable(downRegion);
        style.font = bitmapFont;

        TextButton title = new TextButton("True Type Font (.ttf) - Gdx FreeType", style);
        table.top().add(title).expandX().top();


        TextButton button1 = new TextButton("start", style);
        button1.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.start();
            }
        });
        ;
        Table menuTable = new Table();
        table.row().right().bottom().expandY();
        table.add(menuTable);
        menuTable.setDebug(DEBUG);
        menuTable.right().bottom();
        menuTable.add(button1).pad(PPM);

        menuTable.row();
        TextButton button2 = new TextButton("settings", style);
        menuTable.add(button2).pad(PPM);

        menuTable.row();
        TextButton button3 = new TextButton("about", style);
        menuTable.add(button3).pad(PPM);

        menuTable.row();
        TextButton button4 = new TextButton("exit", style);
        menuTable.add(button4).pad(PPM);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
//        bitmapFont.draw(game.getBatch(),"True Type Font (.ttf) - Gdx FreeType",10,20 * PPM, 300, 80, true);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
