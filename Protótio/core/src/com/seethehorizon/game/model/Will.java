package com.seethehorizon.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Danilo on 20/02/2016.
 * Classe que representa o personagem principal do jogo
 */
public class Will {

    //é importante definir um enum que englobe todos os possíveis estados do personagem
    public enum State {
        IDLE, WALKING, JUMPING, DYING;
    }

    //para definir atributos imutáveis do personagem
    private static final float SPEED = 4f;
    private static final float JUMP_VELOCITY = 1f;
    private static final float SIZE = 0.5f;

    private Vector2    position = new Vector2(); //posição do personagem no mundo do jogo
    private Vector2    acceleration = new Vector2(); //determina a aceleração
    private Vector2    velocity = new Vector2(); //será calculada e usada para Will se mover
    private Rectangle  bounds = new Rectangle(); //espaço (área) ocupado pelo Will
    private State      state = State.IDLE; //estado atual do Will
    private boolean    facingLeft = true; // determian se o rosto do Will está virado para a esquerda
    private boolean    grounded = false;

    public Will(Vector2 position) {
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
    }

    public void update(float delta){
        position.add(velocity.cpy().scl(delta));
    }

    public Rectangle getBounds(){
        return this.bounds;
    }

    public Vector2 getPosition(){
        return this.position;
    }

    public static float getSPEED() {
        return SPEED;
    }

    public static float getJumpVelocity() {
        return JUMP_VELOCITY;
    }

    public static float getSIZE() {
        return SIZE;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        if(state == State.JUMPING){
            grounded = false;
        }
        this.state = state;
    }

    public boolean isFacingLeft() {
        return facingLeft;
    }

    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }
}
