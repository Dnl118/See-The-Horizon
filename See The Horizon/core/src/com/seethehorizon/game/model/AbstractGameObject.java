package com.seethehorizon.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Danilo on 10/04/2016.
 */
public abstract class AbstractGameObject {

    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;

    //para atores que se movimentam
    public Vector2 velocity;
    public Vector2 maxVelocity; //determina velocidade maxima do ator
    public Vector2 friction; //forca que frea o ator
    public Vector2 acceleration; //forca que acelera o ator

    //para delimitar "limite" do ator (tbm para detectar colisao futuramente)
    public Rectangle bounds;


    public AbstractGameObject() {
        position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;
        velocity = new Vector2();
        maxVelocity = new Vector2();
        friction = new Vector2();
        acceleration = new Vector2();
        bounds = new Rectangle();
    }

    public void update(float deltaTime) {
        updateMotionX(deltaTime);
        updateMotionY(deltaTime);
        //atualiza para nova posicao
        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;
    }

    public abstract void render(SpriteBatch batch);

    //calcula movimentacao no eixo x
    protected void updateMotionX(float deltaTime){
        //primeiro vamos verificar se
        //o objeto está parado no eixo x
        if(velocity.x != 0){
            //se nao, entao aplica friccao para freia-lo
            if(velocity.x > 0){
                velocity.x = Math.max(velocity.x - friction.x * deltaTime, 0);
            } else {
                velocity.x = Math.min(velocity.x + friction.x * deltaTime, 0);
            }
        }
        //aplica aceleracao
        velocity.x += acceleration.x * deltaTime;
        //para assegurar que a velocidade nao ultrapasse
        //o limite maximo (tanto positivo quanto negativo)
        velocity.x = MathUtils.clamp(velocity.x, -maxVelocity.x, maxVelocity.x);
    }

    //calcula movimentacao no eixo y
    protected void updateMotionY(float deltaTime){
        //primeiro vamos verificar se
        //o objeto está parado no eixo y
        if(velocity.y != 0){
            //se nao, entao aplica friccao para freia-lo
            if(velocity.y > 0){
                velocity.y = Math.max(velocity.y - friction.y * deltaTime, 0);
            } else {
                velocity.y = Math.min(velocity.y + friction.y * deltaTime, 0);
            }
        }
        //aplica aceleracao
        velocity.y += acceleration.y * deltaTime;
        //para assegurar que a velocidade nao ultrapasse
        //o limite maximo (tanto positivo quanto negativo)
        velocity.y = MathUtils.clamp(velocity.y, -maxVelocity.y, maxVelocity.y);
    }
}