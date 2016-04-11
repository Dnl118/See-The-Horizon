package com.seethehorizon.game.controller;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.seethehorizon.game.assets.Assets;
import com.seethehorizon.game.levels.Level;
import com.seethehorizon.game.util.CameraHelper;
import com.seethehorizon.game.util.Constants;

/**
 * Created by Francisco on 03/04/2016.
 */
public class WorldController {

    private static final String TAG = WorldController.class.getName();

    //public Sprite[] testSprites;
    //public int selectedSprite;
    public CameraHelper cameraHelper;

    public Level level;
    public int lives;
    public int score;

    private void initLevel(){
        score = 0;
        level = new Level(Constants.LEVEL_01);
    }

    public WorldController() {
        init();
    }

    private void init() {
        cameraHelper = new CameraHelper();
        lives = Constants.LIVES_START;
        initLevel();
    }

    private Pixmap createProceduralPixmap(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        // Fill square with red color at 50% opacity
        pixmap.setColor(1, 0, 0, 0.5f);
        pixmap.fill();
        // Draw a yellow-colored X shape on square
        pixmap.setColor(1, 1, 0, 1);
        pixmap.drawLine(0, 0, width, height);
        pixmap.drawLine(width, 0, 0, height);
        // Draw a cyan-colored border around square
        pixmap.setColor(0, 1, 1, 1);
        pixmap.drawRectangle(0, 0, width, height);
        return pixmap;
    }

    public void update(float deltaTime) {
        cameraHelper.update(deltaTime);
    }

}
