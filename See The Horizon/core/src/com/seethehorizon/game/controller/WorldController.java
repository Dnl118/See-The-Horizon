package com.seethehorizon.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.seethehorizon.game.levels.Level;
import com.seethehorizon.game.model.Coin;
import com.seethehorizon.game.model.Cristal1;
import com.seethehorizon.game.model.Cristal2;
import com.seethehorizon.game.model.End;
import com.seethehorizon.game.model.Live;
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
    public int qtdCoins;
    public int qtdCristal1;
    public int qtdCristal2;
    public boolean gameFinished;

    //rects para detectar colisoes
    private Rectangle rect1 = new Rectangle();
    private Rectangle rect2 = new Rectangle();

    private float timeToRespawn;
    private float timetoRestart;

    private boolean accelerometerAvailable;

    public WorldController() {
        init();
    }

    private void init() {
        accelerometerAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        cameraHelper = new CameraHelper();
        lives = Constants.LIVES_START;
        timeToRespawn = 0;
        timetoRestart = Constants.TIME_TO_RESPAWN;
        gameFinished = false;
        initLevel();
    }

    private void initLevel() {
        score = 0;
        qtdCoins = 0;
        qtdCristal1 = 0;
        qtdCristal2 = 0;
        level = new Level(Constants.LEVEL_01);
        cameraHelper.setTarget(level.will);
    }

    public void update(float deltaTime) {
        if (gameFinished) {
            timetoRestart -= deltaTime;
            if (timetoRestart < 0) {
                init();
            }
        } else {
            if (isGameOver()) {
                timeToRespawn -= deltaTime;
                if (timeToRespawn < 0) {
                    init();
                }
            } else {
                handleInputGame(deltaTime);
            }
            level.update(deltaTime);
            testCollisions();
            cameraHelper.update(deltaTime);
            if (!isGameOver() && isWillInEsgoto()) {
                lives--;
                if (isGameOver()) {
                    timeToRespawn = Constants.TIME_TO_RESPAWN;
                } else {
                    initLevel();
                }
            }
        }
    }

    public boolean isGameOver() {
        return lives < 0;
    }

    public boolean isWillInEsgoto() {
        return level.will.position.y < -5;
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
        for (Coin c : level.coins) {
            if (c.collected) {
                //se ja foi coletado ja parte pro proximo
                continue;
            }
            rect2.set(c.position.x, c.position.y,
                    c.bounds.getWidth(), c.bounds.getHeight());
            if (!rect1.overlaps(rect2)) {
                //idem will com solo
                continue;
            }
            onCollisionWillWithCoin(c);
            break;
        }
        //testa colisao com coletavel1
        for (Cristal1 c1 : level.cristais1) {
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
            onCollisionWillWithCristal1(c1);
            break;
        }
        //testa colisao com coletavel2
        for (Cristal2 c2 : level.cristais2) {
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
            onCollisionWillWithCristal2(c2);
            break;
        }
        for (Live live : level.extraLives) {
            if (live.collected) {
                continue;
            }
            rect2.set(live.position.x, live.position.y,
                    live.bounds.getWidth(), live.bounds.getHeight());
            if (!rect1.overlaps(rect2)) {
                //idem will com solo
                continue;
            }
            onCollisionWillWithLive(live);
            break;
        }
        //verifica se chegou no final
        for (End end : level.ends) {
            rect2.set(end.position.x, end.position.y,
                    end.bounds.getWidth(), end.bounds.getHeight());
            if (!rect1.overlaps(rect2)) {
                continue;
            }
            onCollisionWithEnd(end);
            break;
        }
    }

    private void onCollisionWillWithSolo(Solo solo) {
        Will will = level.will;
        float heightDifference = Math.abs(will.position.y - (solo.position.y + solo.bounds.height));
        if (heightDifference > 0.25f) {
            boolean hitLeftEdge = will.position.x > solo.position.x + solo.bounds.width / 2.0f;
            if (hitLeftEdge) {
                will.position.x = solo.position.x + solo.bounds.width;
            } else {
                will.position.x = solo.position.x - will.bounds.width;
            }
            return;
        }
        switch (will.jumpState) {
            case GROUNDED:
                break;
            case FALLING:
            case JUMP_FALLING:
                will.position.y = solo.position.y + will.bounds.height + will.origin.y;
                will.jumpState = Will.JUMP_STATE.GROUNDED;
                break;
            case JUMP_RISING:
                will.position.y = solo.position.y + will.bounds.height + will.origin.y;
                break;
        }
    }

    private void onCollisionWillWithCoin(Coin c) {
        c.collected = true;
        qtdCoins++;
        score += c.getScore();
        Gdx.app.log(TAG, "c coletado");
    }

    private void onCollisionWillWithCristal1(Cristal1 c1) {
        c1.collected = true;
        qtdCristal1++;
        score += c1.getScore();
        Gdx.app.log(TAG, "c1 coletado");
    }

    private void onCollisionWillWithCristal2(Cristal2 c2) {
        c2.collected = true;
        qtdCristal2++;
        score += c2.getScore();
        Gdx.app.log(TAG, "c2 coletado");
    }

    private void onCollisionWillWithLive(Live live) {
        live.collected = true;
        score += live.getScore();
        if (lives < Constants.MAX_LIVES) {
            lives += 1;
        }
        Gdx.app.log(TAG, "extra live coletado");
    }

    private void onCollisionWithEnd(End end) {
        gameFinished = true;
        Gdx.app.log(TAG, "chegou no final");
    }

    private void handleInputGame(float deltaTime) {
        //level.will.velocity.x = level.will.maxVelocity.x; //teste de movimento lateral
        if (accelerometerAvailable) {
            float amount = Gdx.input.getAccelerometerY() / 10.0f;
            amount *= 90.0f;
            if (Math.abs(amount) < Constants.MIN_ANGLE_FOR_ACCELERATION) {
                amount = 0;
            } else {
                amount /= Constants.MAX_ANGLE_FOR_ACCELERATION;
            }
            level.will.velocity.x = level.will.maxVelocity.x * amount;
        } else {
            level.will.velocity.x = level.will.maxVelocity.x;
        }
        if (Gdx.input.isTouched()) {
            //Gdx.app.log(TAG, "Tela input");
            level.will.setJumping(true);
        } else {
            level.will.setJumping(false);
        }
    }

}
