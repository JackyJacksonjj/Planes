package com.gdx.game.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by jenea on 5/10/17.
 */
public class PurpleLabel extends Label {

    private float width, height;

    public PurpleLabel(CharSequence text, LabelStyle style) {
        super(text, style);
    }

    @Override
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public float getPrefWidth() {
        return width;
    }

    @Override
    public float getPrefHeight() {
        return height;
    }
}
