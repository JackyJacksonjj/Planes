package com.gdx.game.ui.menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.List;

/**
 * Representation of menu list.
 * TODO: listeners, different type of buttons (only text, input, checkbox, combined, etc.), orientation, scroll, stylization (?)
 */
public class MenuOptions {

    private List<TextButton> menuOptions;

    /**
     * Should be called last among all render calls
     */
//    public void render(SpriteBatch batch) {
//        if(batch.isDrawing()) {
//            for(TextButton x : menuOptions) {
//                batch.draw(x, x.getX(), x.getY());
//            }
//        } else {
//            throw new RuntimeException("SpriteBatch is not drawing! Call SpriteBatch.begin() before drawing");
//        }
//    }
}
