package com.seethehorizon.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Danilo on 20/02/2016.
 * Representa um bloco dentro do jogo
 * Será utilizada para fazer paredes, obstáculos e etc
 */
public class Block {

    public static final float SIZE = 1f;

    Vector2    position = new Vector2();
    Rectangle  bounds = new Rectangle();

    public Block(Vector2 position){
        this.position = position;
        this.bounds.width = SIZE;
        this.bounds.height = SIZE;
    }

    public Vector2 getPosition(){
        return this.position;
    }

    public Rectangle getBounds(){
        return this.bounds;
    }

}
