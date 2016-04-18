package com.seethehorizon.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.seethehorizon.game.levels.Level;
import com.seethehorizon.game.model.Coletavel1;
import com.seethehorizon.game.model.Coletavel2;
import com.seethehorizon.game.model.Solo;
import com.seethehorizon.game.model.Will;
import com.seethehorizon.game.util.CameraHelper;
import com.seethehorizon.game.util.Constants;

/**
 * Created by Danilo on 03/04/2016.
 */
public class WorldController {

    private static final String TAG = WorldController.class.getName();

    public CameraHelper cameraHelper;

    public Level level;
    public int lives;
    public int score;

    //rects para detectar colisoes
    private Rectangle rect1 = new Rectangle();
    private Rectangle rect2 = new Rectangle();

    public WorldController() {
        init();
    }

    private void init() {
        cameraHelper = new CameraHelper();
        lives = Constants.LIVES_START;
        initLevel();
    }

    private void initLevel() {
        score = 0;
        level = new Level(Constants.LEVEL_01);
    }

    public void update(float deltaTime) {
        level.update(deltaTime);
        testCollisions();
        cameraHelper.update(deltaTime);
    }

    private void testCollisions() {
        //carrega rect1 com valores do will
        rect1.set(level.will.position.x, level.will.position.y,
                level.will.bounds.getWidth(), level.will.bounds.getHeight());
        //testa colisao entre will e o solo
        for (Solo solo : level.solos) {
            //carrrega rect2 com valores do objeto solo da iteracao
            rect2.set(solo.position.x, solo.position.y,
                    solo.bounds.getWidth(), solo.bounds.getHeight());
            if (!rect1.overlaps(rect2)) {
                //se nao estiver em overlaps ja parte pro proximo solo
                continue;
            }
            onCollisionWillWithSolo(solo);
        }
        //testa colisao com coletavel1
        for (Coletavel1 c1 : level.coletaveis1) {
            if (c1.collected) {
                //se ja foi coletado ja parte pro proximo
                continue;
            }
            rect2.set(c1.position.x, c1.position.y,
                    c1.bounds.getWidth(), c1.bounds.getHeight());
            if (!rect1.overlaps(rect2)) {
                //idem will com solo
                continue;
            }
            onCollisionWillWithColetavel1(c1);
            break;
        }
        //testa colisao com coletavel2
        for (Coletavel2 c2 : level.coletaveis2) {
            if (c2.collected) {
                //se ja foi coletado ja parte pro proximo
                continue;
            }
            rect2.set(c2.position.x, c2.position.y,
                    c2.bounds.getWidth(), c2.bounds.getHeight());
            if (!rect1.overlaps(rect2)) {
                //idem will com solo
                continue;
            }
            onCollisionWillWithColetavel2(c2);
            break;
        }
    }

    private void onCollisionWillWithSolo(Solo solo) {
        Will will = level.will;
        float heigthDifference = Math.abs(will.position.y - (will.position.y + solo.bounds.getHeight()));
        if (heigthDifference > 0.25f) {
            boolean hitHightEdge = will.position.x > (solo.position.x + solo.bounds.getWidth() / 2.0f);
            if (hitHightEdge) {
                will.position.x = solo.position.x + solo.bounds.getWidth();
            } else {
                will.position.x = solo.position.x - will.bounds.getWidth();
            }
            return;
        }
        switch (will.jumpState) {
            case GROUNDED:
                break;
            case FALLING:
            case JUMP_FALLING:
                will.position.y = will.position.y + will.bounds.getHeight() + will.origin.y;
                will.jumpState = Will.JUMP_STATE.GROUNDED;
                break;
            case JUMP_RISING:
                will.position.y = solo.position.y + solo.bounds.getHeight() + will.origin.y;
                break;
        }
    }

    private void onCollisionWillWithColetavel1(Coletavel1 c1) {
        c1.collected = true;
        score += c1.getScore();
        Gdx.app.log(TAG, "c1 coletado");
    }

    private void onCollisionWillWithColetavel2(Coletavel2 c2) {
        c2.collected = true;
        score += c2.getScore();
        Gdx.app.log(TAG, "c2 coletado");
    }

}
