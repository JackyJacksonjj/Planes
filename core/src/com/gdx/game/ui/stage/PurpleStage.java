package com.gdx.game.ui.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Overriding {@link Stage} draw() method to be able easily set background
 */
public class PurpleStage extends Stage {

    private TextureRegion background;

    public PurpleStage(Viewport viewport) {
        super(viewport);
    }

    public PurpleStage(String bckResourceName) {
        background = new TextureRegion(
                new Texture(Gdx.files.internal(bckResourceName)),
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
        );
    }

    public PurpleStage(Viewport viewport, String bckResourceName) {
        this(viewport, new TextureRegion(
                        new Texture(Gdx.files.internal(bckResourceName)),
                        0,
                        0,
                        Gdx.graphics.getWidth(),
                        Gdx.graphics.getHeight()
                )
        );
    }

    public PurpleStage(Viewport viewport, TextureRegion textureRegion) {
        super(viewport);
        this.background = textureRegion;
    }

    @Override
    public void draw() {
        draw(false);
    }

    /**
     * Copy of inner content of super.draw() except line that adds background
     */
    public Batch draw(boolean finishRenderingManually) {
        Camera camera = getViewport().getCamera();
        camera.update();

        if (!getRoot().isVisible()) return getBatch();

        Batch batch = getBatch();
        if (batch != null) {
            batch.setProjectionMatrix(camera.combined);
            //TODO: do not call batch.begin(). Have to be removed.
            if(!batch.isDrawing()) {
                batch.begin();
            }

            if (background != null) {
                batch.draw(background, 0, 0);
            }
            getRoot().draw(batch, 1);
            if (!finishRenderingManually) {
                batch.end();
            }
        }

        return batch;
        //if(debug) drawDebug();
    }
}
