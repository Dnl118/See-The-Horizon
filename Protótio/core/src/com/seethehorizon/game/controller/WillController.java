package com.seethehorizon.game.controller;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.seethehorizon.game.model.Block;
import com.seethehorizon.game.model.Will;
import com.seethehorizon.game.model.World;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Danilo on 20/02/2016.
 */
public class WillController {

    enum Keys {
        LEFT, RIGHT, JUMP, FIRE;
    }

    private static final long LONG_JUMP_PRESS = 150l;
    private static final float ACCELERATION = 20f;
    private static final float GRAVITY = -20f;
    private static final float MAX_JUMP_SPEED = 7f;
    private static final float DAMP = 0.95f;
    private static final float MAX_VEL = 4f;
    //temporario
    private static final float WIDTH = 10f;

    private World world;
    private Will will;
    private long jumpPressedTime;
    private boolean jumpingPressed;


    static Map<Keys, Boolean> keys = new HashMap<WillController.Keys, Boolean>();

    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.JUMP, false);
        keys.put(Keys.FIRE, false);
    }

    ;

    public WillController(World world) {
        this.world = world;
        this.will = world.getWill();
    }

    //controla se teclas pressionadas ou não (movimentos)
    public void leftPressed() {
        keys.get(keys.put(Keys.LEFT, true));
    }

    public void rightPressed() {
        keys.get(keys.put(Keys.RIGHT, true));
    }

    public void jumpPressed() {
        keys.get(keys.put(Keys.JUMP, true));
        jumpingPressed = true;
    }

    public void firePressed() {
        keys.get(keys.put(Keys.FIRE, false));
    }

    public void leftReleased() {
        keys.get(keys.put(Keys.LEFT, false));
    }

    public void rightReleased() {
        keys.get(keys.put(Keys.RIGHT, false));
    }

    public void jumpReleased() {
        keys.get(keys.put(Keys.JUMP, false));
        jumpingPressed = false;
    }

    public void fireReleased() {
        keys.get(keys.put(Keys.FIRE, false));
    }

    //método de atualização principal
    public void update(float delta) {
        processInput();
        colisionDetection(delta);

        if(!will.isGrounded()) {
            will.getAcceleration().y = GRAVITY;
        }
        //.slc substitui o .mul
        will.getAcceleration().scl(delta);
        will.getVelocity().add(will.getAcceleration().x, will.getAcceleration().y);
        if (will.getAcceleration().x == 0)
            will.getVelocity().x *= DAMP;
        if (will.getVelocity().x > MAX_VEL)
            will.getVelocity().x = MAX_VEL;
        if (will.getVelocity().x < -MAX_VEL)
            will.getVelocity().x = -MAX_VEL;

        will.update(delta);

        if (will.getPosition().y < 0) {
            will.getPosition().y = 0f;
            will.setPosition(will.getPosition());
            if (will.getState().equals(Will.State.JUMPING)) {
                will.setState(Will.State.IDLE);
            }
        }
        if (will.getPosition().x < 0) {
            will.getPosition().x = 0;
            will.setPosition(will.getPosition());
            if (!will.getState().equals(Will.State.JUMPING)) {
                will.setState(Will.State.IDLE);
            }
        }
        if (will.getPosition().x > WIDTH - will.getBounds().width) {
            will.getPosition().x = WIDTH - will.getBounds().width;
            will.setPosition(will.getPosition());
            if (!will.getState().equals(Will.State.JUMPING)) {
                will.setState(Will.State.IDLE);
            }
        }

    }

    private boolean processInput() {
        if (keys.get(Keys.JUMP)) {
            if (!will.getState().equals(Will.State.JUMPING)) {
                jumpingPressed = true;
                jumpPressedTime = System.currentTimeMillis();
                will.setState(Will.State.JUMPING);
                will.getVelocity().y = MAX_JUMP_SPEED;
            } else if (jumpingPressed && ((System.currentTimeMillis() - jumpPressedTime) >= LONG_JUMP_PRESS)) {
                jumpingPressed = false;
            } else if (jumpingPressed) {
                will.getVelocity().y = MAX_JUMP_SPEED;
            }
        }
        if (keys.get(Keys.LEFT)) {
            will.setFacingLeft(true);
            if (!will.getState().equals(Will.State.JUMPING)) {
                will.setState(Will.State.WALKING);
            }
            will.getAcceleration().x = -ACCELERATION;
        } else if (keys.get(Keys.RIGHT)) {
            will.setFacingLeft(false);
            if (!will.getState().equals(Will.State.JUMPING)) {
                will.setState(Will.State.WALKING);
            }
            will.getAcceleration().x = ACCELERATION;
        } else {
            if (!will.getState().equals(Will.State.JUMPING)) {
                will.setState(Will.State.IDLE);
            }
            will.getAcceleration().x = 0;
        }
        return false;
    }

    private void onCollisionWithBlocks(Block block) {
        Will will = world.getWill();
        float heightDifference = Math.abs(will.getPosition().y -
                (block.getPosition().y + block.getBounds().getHeight()));
        if (heightDifference >= 0.1f) {
            boolean hitRightEdge = //will.getPosition().dst(block.getPosition()) > block.getBounds().getWidth() / 2f;
                    will.getPosition().x >
                            (block.getPosition().x + block.getBounds().getWidth() / 2.0f);
            if (hitRightEdge) {
                will.getPosition().x = block.getPosition().x + block.getBounds().getWidth();
            } else {
                will.getPosition().x = block.getPosition().x - will.getBounds().getWidth();
            }
            boolean hitTopEdge = will.getPosition().y <
                    (will.getPosition().y + block.getBounds().getHeight() / 2.0f);
            if(hitTopEdge){
                will.getPosition().y = block.getPosition().y + block.getBounds().getHeight();
                will.setGrounded(true);
            } else {
                will.getPosition().y = block.getPosition().y - will.getBounds().getHeight();
            }
            return;
        }

    }

    public void colisionDetection(float delta) {
        Rectangle r1 = new Rectangle();
        Rectangle r2 = new Rectangle();

        r1.set(world.getWill().getPosition().x, world.getWill().getPosition().y,
                world.getWill().getBounds().getWidth(), world.getWill().getBounds().getHeight());

        for (Block block : world.getBlocks()) {
            r2.set(block.getPosition().x, block.getPosition().y,
                    block.getBounds().getWidth(), block.getBounds().getHeight());
            if (!r1.overlaps(r2)) continue;
            //if(!(will.getPosition().dst(block.getPosition()) < block.getBounds().getWidth() / 2)) continue;
            onCollisionWithBlocks(block);
        }
    }

}
