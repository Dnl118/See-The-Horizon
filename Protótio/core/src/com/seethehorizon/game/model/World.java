package com.seethehorizon.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Danilo on 20/02/2016.
 * Classe que representa o mundo onde o jogo acontece
 * por enquanto será apenas uma sala
 */
public class World {

    private Array blocks = new Array(); //armazenará os blocos do mundo
    private Will will; //personagem principal

    public World(){
        createDemoWorld();
    }

    private void createDemoWorld(){
        will = new Will(new Vector2(0, 0));

        for(int i = 0; i < 10; i++){
            blocks.add(new Block(new Vector2(i, 0)));
            blocks.add(new Block(new Vector2(i, 6)));
            if(i > 2){
                blocks.add(new Block(new Vector2(i, 1)));
            }
        }
        blocks.add(new Block(new Vector2(9, 2)));
        blocks.add(new Block(new Vector2(9, 3)));
        blocks.add(new Block(new Vector2(9, 4)));
        blocks.add(new Block(new Vector2(9, 5)));
        blocks.add(new Block(new Vector2(6, 3)));
        blocks.add(new Block(new Vector2(6, 4)));
        blocks.add(new Block(new Vector2(6, 5)));
    }

    public Array<Block> getBlocks(){
        return this.blocks;
    }

    public Will getWill(){
        return this.will;
    }

}
